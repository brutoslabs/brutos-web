/*
 * Brutos Web MVC http://www.brutosframework.com.br/
 * Copyright (C) 2009 Afonso Brandao. (afonso.rbn@gmail.com)
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
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.mapping.StringUtil;
import org.brandao.brutos.web.URIMapping;

/**
 *
 * @author Brandao
 */
public class WebUtil {
    
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
            
            if(!value.startsWith("/"))
                throw new MalformedURLException("expected starts with \"/\": " + value);

            URIMapping map = new URIMapping( value );
            
            String uri = map.getURI(new Object[]{});
            URI prefix = new URI("http://serverName");
            prefix.resolve(uri);
        }
        catch(Exception e){
            throw new BrutosException(e);
        }
    }
    
}
