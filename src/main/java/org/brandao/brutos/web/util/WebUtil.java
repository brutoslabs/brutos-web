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

package org.brandao.brutos.web.util;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.brandao.brutos.BrutosException;
import org.brandao.brutos.mapping.StringUtil;
import org.brandao.brutos.web.StringPattern;

/**
 * 
 * @author Brandao
 */
public class WebUtil {

	public static final char REQUEST_SEPARATOR = '/';

	public static final String INDEX_REQUEST = "";
	
    public static String fixURI(String uri){
        
        if(StringUtil.isEmpty(uri))
            return null;
        
        uri = uri.replace("\\+", "\\");
        uri = uri.replaceAll("/+", "/");
        uri = uri.startsWith("/")? uri : "/" + uri;
        uri = uri.replace("/$", "");
        return uri;
    }
    
    public static void checkURI(String value, boolean required){
        try{  
            if(StringUtil.isEmpty(value)){
                if(required)
                    throw new MalformedURLException("is null or empty: " + value);
                else
                    return;
            }
            
            //if(!value.startsWith("/"))
            //    throw new MalformedURLException("expected starts with \"/\": " + value);

            StringPattern map = new StringPattern( value );
            
            String uri = map.getPattern();//map.toString(new Object[]{});
            URI prefix = new URI("http://serverName");
            prefix.resolve(uri);
        }
        catch(Exception e){
            throw new BrutosException(e);
        }
    }

    public static Enumeration<String> toEnumeration(String value){
    	return new EnumarationString(value);
    }
    
    public static List<String> parserURI(String value, boolean hasVars){
    	
    	List<String> result = new ArrayList<String>();
    	char[] chars = value.toCharArray();

    	for(int i=0;i<chars.length;i++){
    		char c = chars[i];
    				
    		if(c == '/'){
    			int start = i + 1;
    			int end   = -1;
    			int region = 0;
    			
    			if(hasVars){
	    			for(i = i+1;i<value.length();i++){
	    				c = chars[i];
	    				if(c == '{'){region++;}
	    				if(c == '}'){region--;}
	    	    		if(c == '/' && region == 0){end = i;i -= 1;break;}
	    			}
    			}
    			else{
    				while(++i < value.length() && chars[i] != '/');
    				end = i;
    				
    				if(i < value.length() && chars[i] == REQUEST_SEPARATOR){
    					i--;
    				}
    				
    			}
    			
    			if(end == -1){
    				end = value.length();
    			}
    			
    			if(end > start){
    				result.add(value.substring(start, end));
    			}
    		}
    		
    	}
    	
    	if(chars.length > 0 && chars[chars.length - 1] == REQUEST_SEPARATOR){
    		result.add(INDEX_REQUEST);
    	}
    	
    	return result;
    	
    }
    
	private static class EnumarationString implements Enumeration<String>{

		private String value;
		
		private int nextComma;
		
		private int currentComma;
		
		public EnumarationString(String value){
			this.currentComma = 0;
			this.value        = value;
			this.nextComma    = value.indexOf(",");
			
			if(nextComma == -1){
				this.nextComma = value.length();
			}
		}
		
		public boolean hasMoreElements() {
			return this.currentComma < value.length();
		}

		public String nextElement() {
			String v          = value.substring(currentComma, nextComma);
			this.currentComma = this.nextComma + 1;
			this.nextComma    = value.indexOf(",", this.currentComma);
			
			if(this.nextComma == -1){
				this.nextComma = value.length();
			}
			return v.trim();
		}
		
	}
    
}
