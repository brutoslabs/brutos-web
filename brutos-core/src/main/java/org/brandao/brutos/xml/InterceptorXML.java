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
import org.brandao.brutos.InterceptorBuilder;
import org.brandao.brutos.InterceptorManager;

/**
 *
 * @author Afonso Brand�o
 */
public class InterceptorXML {
    
    private InterceptorManager interceptorManager;
    
    public InterceptorXML( InterceptorManager interceptorManager ) {
        this.interceptorManager = interceptorManager;
    }

    public void add( Map<String,Object> interceptor ) throws Exception{
        InterceptorBuilder ib = interceptorManager
                .addInterceptor( 
                    (String)interceptor.get( "name" ),
                    (Class)Class.forName( 
                        (String)interceptor.get( "class" ), 
                        true, 
                        Thread.currentThread().getContextClassLoader() 
                    ),
                    "true".equals( interceptor.get( "default" ) )
                );
        
        /*
        List<Map<String,Object>> interceptors = (List)interceptor.get( "interceptors" );
        if( interceptors != null ){
            for( Map<String,Object> in: interceptors ){
                String ref = (String)in.get( "ref" );
                //ib.addInterceptor( ref );
                addParam( ib, (List<Map<String,Object>>)in.get( "params" ) );
            }
        }
        */
        addParam( ib, (List<Map<String,Object>>)interceptor.get( "params" ) );
        
    }
 
    private void addParam( InterceptorBuilder ib, List<Map<String,Object>> params ){
        for( Map<String,Object> in: params )
            ib.addParameter( (String)in.get( "name" ), (String)in.get( "value" ) );
    }
}
