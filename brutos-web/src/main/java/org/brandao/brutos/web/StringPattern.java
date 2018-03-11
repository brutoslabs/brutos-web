/*
 * Brutos Web MVC http://www.brutosframework.com.br/
 * Copyright (C) 2009-2017 Afonso Brandao. (afonso.rbn@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.brandao.brutos.web;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.brandao.brutos.mapping.StringUtil;

/**
 * 
 * @author Brandao
 */
public class StringPattern {

    private String original;
    
    private Pattern pattern;

    private Pattern prePattern;
    
    private String stringPattern;
    
    private List<StringPatternVar> vars;

    public StringPattern( String value ) throws MalformedURLException{
        this.parse(value);
        this.original   = value;
        this.pattern    = 
    		Pattern.compile(this.createRegex(), Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
        this.prePattern =
			Pattern.compile(this.createPreRegex(), Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
        this.stringPattern = 
    		this.createPattern();
    }

    /* -- private methods -- */
    
    private void parse(String uri) throws MalformedURLException{
    	
        List<String> frags  = new ArrayList<String>();
        List<String> ids    = new ArrayList<String>();
        List<String> regexs = new ArrayList<String>();
        int startFrag       = 0;
        char[] chars        = uri.toCharArray();
        int openKeysCount   = 0;
        int closeKeysCount  = 0;
        int firstOpenKeys   = -1;
        int lastCloseKeys   = -1;

        for(int i=0;i<chars.length;i++){
        	
        	if(chars[i] == '{'){
        		
        		if(firstOpenKeys == -1){
        			frags.add(uri.substring(startFrag, i));
        			firstOpenKeys = i;
        		}
        		
        		openKeysCount++;
        	}
        	
        	if(chars[i] == '}'){
        		lastCloseKeys = i;
        		closeKeysCount++;
        	}
        	
        	if(openKeysCount > 0 && openKeysCount == closeKeysCount){
        		
        		
        		String var = uri.substring(firstOpenKeys + 1, lastCloseKeys);

        		int separatorIndex = var.indexOf(":");
        		
        		String id = 
    				var.indexOf(":") == -1?
    					var :
    					var.substring(0, separatorIndex);
    					
		        if(StringUtil.isEmpty(id))
		            throw new MalformedURLException("invalid parameter id " + var);
    					
    			String regex = 
					separatorIndex != -1?
						var.substring(separatorIndex + 1, var.length()) :
							null;
						
				regex = StringUtil.isEmpty(regex)? "\\w{1,}" : regex;
				
				ids.add(id);
				regexs.add(regex);
				
				startFrag      = i + 1;
				firstOpenKeys  = -1;
				lastCloseKeys  = -1;
		        openKeysCount  = 0;
		        closeKeysCount = 0;
        	}
        }
        
        if(openKeysCount > 0 && openKeysCount != closeKeysCount){
            throw new MalformedURLException("expected: }");
        }
        
        if(startFrag >= 0 && startFrag <= uri.length()){
        	if(startFrag == uri.length())
                frags.add(null);
        	else
        		frags.add(uri.substring(startFrag, uri.length()));
        }
        

        vars = new ArrayList<StringPatternVar>();

        for( int i=0;i<ids.size();i++ ){
        	
            StringBuilder regexPrefix = new StringBuilder("^");
            StringBuilder regexSuffix = new StringBuilder("");
            
            for(int k=0;k<=i;k++){
            	String value = frags.get(k);
                regexPrefix.append( value == null || value.isEmpty()? "" : Pattern.quote(value) );
                
                if(i>0 && k<i){
                   regexPrefix.append( regexs.get(k) );
                }
            }
        	
            for(int k=i;k<ids.size();k++){
            	
                if(k>i){
                    regexSuffix.append( regexs.get(k) );
                }
                
                String value = frags.get(k+1);
                regexSuffix.append(value == null || value.isEmpty()? "" : Pattern.quote(value) );
            }
            
            regexSuffix.append("$");
            
            vars.add(
                    new StringPatternVar(
                        i,
                        ids.get(i),
                        Pattern.compile(regexs.get(i), Pattern.DOTALL | Pattern.CASE_INSENSITIVE),
                        frags.get(i),
                        frags.get(i+1),
                        Pattern.compile(regexPrefix.toString(), Pattern.DOTALL | Pattern.CASE_INSENSITIVE),
                        Pattern.compile(regexSuffix.toString(), Pattern.DOTALL | Pattern.CASE_INSENSITIVE)) );
        }
        
    }

    private String createPreRegex(){
        StringBuilder result = new StringBuilder();
        
        if(vars.isEmpty())
            return this.original;
        
        
        for( int i=0;i<vars.size();i++ ){
        	StringPatternVar p = (StringPatternVar)vars.get(i);

            if(i == 0){
            	
                if(p.getStart() != null && p.getStart().length() > 0){
                    result
                    	.append( (p.getStart() == null? "" : Pattern.quote(p.getStart())) );
                }
                
                result
                	.append("(")
                	.append(".*")
                	.append(")");
                
                if(p.getEnd() != null && p.getEnd().length() > 0){
                    result
                    	.append( (p.getEnd() == null? "" : Pattern.quote(p.getEnd())) );
                }
            }
            else{
            	
                result
                	.append("(")
                	.append(".*")
                	.append(")");
                
                if(p.getEnd() != null && p.getEnd().length() > 0){
                    result
                    	.append( (p.getEnd() == null? "" : Pattern.quote(p.getEnd())) );
                }
            }
            
        }
        
        return result.toString();
        
    }
    
    private String createPattern(){
        StringBuilder value = new StringBuilder();
        
        if(vars.isEmpty())
            return this.original;
        
        for(int i=0;i<vars.size();i++ ){
            StringPatternVar p = vars.get(i);
            
            if(i == 0 && !p.isEmptyStart()){
                value.append(p.getStart());
            }

            value.append("(").append(p.getId()).append(")");
            
            if(!p.isEmptyEnd()){
                value.append(p.getEnd());
            }
            
        }
        
        return value.toString();
        
    }
    
    private String createRegex(){
        StringBuilder value = new StringBuilder();
        
        if(vars.isEmpty())
            return this.original;
        
        for(int i=0;i<vars.size();i++ ){
            StringPatternVar p = vars.get(i);
            
            if(i == 0 && !p.isEmptyStart()){
            	value.append(Pattern.quote(p.getStart()));
            }
            
            value.append(p.getRegex());
            
            if(!p.isEmptyEnd()){
                value.append(Pattern.quote(p.getEnd()));
            }
            
        }
        
        return value.toString();
        
    }
    
    /* -- public methods -- */
    
    public String getPattern(){
    	return this.stringPattern;
    }
    
    public String toString(Object[] params){
        String value = null;
        
        if(vars.isEmpty())
            return this.original;
        
        for(int i=0;i<vars.size();i++ ){
            StringPatternVar p = vars.get(i);
            
            if(i == 0 && p.getStart() != null){
                value = p.getStart();
            }

            value += String.valueOf(params[p.getIndex()]);
            
            if(p.getEnd() != null){
                value += p.getEnd();
            }
            
        }
        
        return value;
    }
    
    public Map<String,List<String>> getParameters( String value ){
        Map<String,List<String>> params = new HashMap<String,List<String>>();

        for( int i=0;i<vars.size();i++ ){
        	StringPatternVar p = vars.get(i);
            String tmp     = value;
            tmp            = p.getRegexPrefix().matcher(tmp).replaceAll("");
            tmp            = p.getRegexSuffix().matcher(tmp).replaceAll("");
            
            List<String> values = params.get(p.getId());
            
            if(values == null){
                values = new ArrayList<String>();
                params.put(p.getId(), values);
            }
            
            values.add(tmp);
        }

        return params;
    }
    
    public boolean matches(String value){
    	if(this.vars.isEmpty()){
    		return this.original.equalsIgnoreCase(value);
    	}
    	else{
    		return 
				this.prePattern.matcher(value).matches() && 
				this.pattern.matcher(value).matches();
    	}
    }

	public List<StringPatternVar> getVars() {
		return vars;
	}
    
}
