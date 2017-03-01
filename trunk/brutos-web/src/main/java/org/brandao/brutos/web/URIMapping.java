package org.brandao.brutos.web;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.brandao.brutos.mapping.StringUtil;

public class URIMapping {

    private String originalURI;
    
    private Pattern uriPattern;

    private Pattern uriPrePattern;
    
    private List<URIParameter> parameters;

    public URIMapping( String uri ) throws MalformedURLException{
        this.parse(uri);
        this.originalURI   = uri;
        this.uriPattern    = 
    		Pattern.compile(this.getURIPattern(), Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
        this.uriPrePattern =
			Pattern.compile(this.getURIPrePattern(), Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
    }

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
        

        parameters = new ArrayList<URIParameter>();

        for( int i=0;i<ids.size();i++ ){
        	
            StringBuilder regexPrefix = new StringBuilder("^");
            StringBuilder regexSuffix = new StringBuilder("");
            
            for(int k=0;k<=i;k++){
                regexPrefix.append( frags.get(k) == null? "" : Pattern.quote(frags.get(k)) );
                
                if(i>0 && k<i){
                   regexPrefix.append( regexs.get(k) );
                }
            }
        	
            for(int k=i;k<ids.size();k++){
            	
                if(k>i){
                    regexSuffix.append( regexs.get(k) );
                }
                
                regexSuffix.append(frags.get(k+1) == null? "" : Pattern.quote(frags.get(k+1)) );
            }
            
            regexSuffix.append("$");
            
            parameters.add(
                    new URIParameter(
                        i,
                        ids.get(i),
                        Pattern.compile(regexs.get(i), Pattern.DOTALL | Pattern.CASE_INSENSITIVE),
                        frags.get(i),
                        frags.get(i+1),
                        Pattern.compile(regexPrefix.toString(), Pattern.DOTALL | Pattern.CASE_INSENSITIVE),
                        Pattern.compile(regexSuffix.toString(), Pattern.DOTALL | Pattern.CASE_INSENSITIVE)) );
        }
        
    }

    private String getURIPrePattern(){
        StringBuilder result = new StringBuilder();
        
        if(parameters.isEmpty())
            return result.toString();
        
        
        for( int i=0;i<parameters.size();i++ ){
        	URIParameter p = (URIParameter)parameters.get(i);

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
    
    private String toURI(Object[] params){
        String value = null;
        
        if(parameters.isEmpty())
            return this.originalURI;
        
        for(int i=0;i<parameters.size();i++ ){
            URIParameter p = parameters.get(i);
            
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
    
    private String getURIWithVars(){
        String value = null;
        
        if(parameters.isEmpty())
            return this.originalURI;
        
        for(int i=0;i<parameters.size();i++ ){
            URIParameter p = parameters.get(i);
            
            if(i == 0 && p.getStart() != null){
                value = p.getStart();
            }

            value += "(" + p.getId() + ")";
            
            if(p.getEnd() != null){
                value += p.getEnd();
            }
            
        }
        
        return value;
        
    }
    
    private String getURIPattern(){
        String value = null;
        
        if(parameters.isEmpty())
            return this.originalURI;
        
        for(int i=0;i<parameters.size();i++ ){
            URIParameter p = parameters.get(i);
            
            if(i == 0 && p.getStart() != null){
            	value = Pattern.quote(p.getStart());
            }
                value += p.getRegex();
            
            if(p.getEnd() != null){
                value += Pattern.quote(p.getEnd());
            }
            
        }
        
        return value;
        
    }
    
    public Map<String,List<String>> getParameters( String value ){
        Map<String,List<String>> params = new HashMap<String,List<String>>();

        for( int i=0;i<parameters.size();i++ ){
        	URIParameter p = parameters.get(i);
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
    
    public String getURI(Object[] params){
        return this.toURI(params);
    }
    
    public boolean matches(String uri){
    	return this.uriPrePattern.matcher(uri).matches() && this.uriPattern.matcher(uri).matches();
    }
    
}
