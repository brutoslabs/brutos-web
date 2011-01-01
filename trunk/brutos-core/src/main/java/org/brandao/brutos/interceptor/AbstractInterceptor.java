/*
 * Brutos Web MVC http://brutos.sourceforge.net/
 * Copyright (C) 2009 Afonso Brandao. (afonso.rbn@gmail.com)
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

package org.brandao.brutos.interceptor;

import org.brandao.brutos.ResourceMethod;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.brandao.brutos.ResourceAction;

/**
 *
 * @author Afonso Brandao
 */
public abstract class AbstractInterceptor implements Interceptor{
    
    protected Map<String,Object> props;
    private List<String> excludeMethods;
    
    public AbstractInterceptor() {
    }


    public boolean accept(InterceptorHandler handler) {
        ResourceAction rm = handler.getResourceAction();
        if( rm != null && excludeMethods != null )
            return !excludeMethods.contains( rm.getMethod().getName() );
        else
            return true;
    }
    
    public void setProperties( Map<String,Object> props ){
        this.props = props;
        
        if( props != null ){
            if( props.containsKey( "excludeMethods" ) ){
                String em = (String)props.get( "excludeMethods" );
                String[] ems = em.split( "," );
                this.excludeMethods = Arrays.asList( ems );
            }
        }
    }

    public boolean isConfigured(){
        return props != null;
    }

}
