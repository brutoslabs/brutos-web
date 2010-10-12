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
package org.brandao.brutos.programatic;

import org.brandao.brutos.mapping.Interceptor;

/**
 *
 * @author Afonso Brandao
 */
public class InterceptorBuilder {
    
    private Interceptor interceptor;
    private InterceptorManager manager;
    
    public InterceptorBuilder( Interceptor interceptor, InterceptorManager manager ) {
        this.interceptor = interceptor;
        this.manager = manager;
    }
    
    public InterceptorBuilder addParameter( String name, String value ){
        interceptor.setProperty( name, value );
        return this;
    }
}
