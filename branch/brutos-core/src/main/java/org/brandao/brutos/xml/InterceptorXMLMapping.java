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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.brandao.brutos.programatic.InterceptorManager;

/**
 *
 * @author Afonso Brand�o
 */
public class InterceptorXMLMapping {
    
    private InterceptorManager interceptorManager;
    private InterceptorXML interceptorXML;
    private InterceptorStackXML interceptorStackXML;
    
    public InterceptorXMLMapping( InterceptorManager interceptorManager ){
        this.interceptorManager  = interceptorManager;
        this.interceptorXML      = new InterceptorXML( interceptorManager );
        this.interceptorStackXML = new InterceptorStackXML( interceptorManager );
    }

    public void processData( List<Map<String,Object>> interceptors ) throws Exception{
        if( interceptors == null )
            return;
        
        List<Map<String,Object>> stacks = new ArrayList();
        
        for( Map<String,Object> interceptor: interceptors ){
            if( "stack".equals( interceptor.get( "type" ) ) )
                stacks.add( interceptor );
            else
                interceptorXML.add( interceptor );
        }
        
        for( Map<String,Object> interceptor: stacks )
            interceptorStackXML.add( interceptor );
        
    }
    
}
