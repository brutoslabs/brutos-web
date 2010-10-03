/*
 * Brutos Web MVC http://brutos.sourceforge.net/
 * Copyright (C) 2009 Afonso Brand�o. (afonso.rbn@gmail.com)
 *
 * This library is free software. You can redistribute it 
 * and/or modify it under the terms of the GNU General Public
 * License (GPL) version 3.0 or (at your option) any later 
 * version.
 * You may obtain a copy of the License at
 * 
 * http://www.gnu.org/licenses/gpl.html 
 * 
 * Distributed WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied.
 *
 */

package org.brandao.brutos.xml;

import java.util.List;
import java.util.Map;
import org.brandao.brutos.programatic.InterceptorManager;
import org.brandao.brutos.programatic.InterceptorStackBuilder;

/**
 *
 * @author Afonso Brand�o
 */
public class InterceptorStackXML {
    
    private InterceptorManager interceptorManager;
    
    public InterceptorStackXML( InterceptorManager interceptorManager ) {
        this.interceptorManager = interceptorManager;
    }
    
    public void add( Map<String,Object> interceptor ){
        InterceptorStackBuilder ib = interceptorManager
                .addInterceptorStack( 
                    (String)interceptor.get( "name" ), 
                    "true".equals( interceptor.get( "default" ) )
                );
        
        List<Map<String,Object>> interceptors = (List)interceptor.get( "interceptors" );
        if( interceptors != null ){
            for( Map<String,Object> in: interceptors ){
                String ref = (String)in.get( "ref" );
                ib.addInterceptor( ref );
                addParam( ib, (List<Map<String,Object>>)in.get( "params" ) );
            }
        }
    }
    
    private void addParam( InterceptorStackBuilder ib, List<Map<String,Object>> params ){
        for( Map<String,Object> in: params )
            ib.addParameter( (String)in.get( "name" ), (String)in.get( "value" ) );
        
    }
    
}
