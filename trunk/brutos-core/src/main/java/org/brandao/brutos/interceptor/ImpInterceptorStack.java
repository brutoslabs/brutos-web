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

import java.util.List;

/**
 *
 * @author Afonso Brandao
 */
public class ImpInterceptorStack implements InterceptorStack{
    
    private List stack;
    private int stackPos;
    
    public ImpInterceptorStack() {
    }
    
    public void exec( List interceptors, InterceptorHandler handler ){
        this.stack = interceptors;
        next( handler );
    }
    
    public void next( InterceptorHandler handler ){
        if( stackPos < this.stack.size() ){
            Interceptor interceptor = (Interceptor) this.stack.get( stackPos++ );
            if( interceptor.accept( handler ) )
                interceptor.intercepted( this, handler );
        }
    }
}