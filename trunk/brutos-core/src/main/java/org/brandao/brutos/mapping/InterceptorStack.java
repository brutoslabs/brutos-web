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
package org.brandao.brutos.mapping;

import java.util.ArrayList;
import java.util.List;
import org.brandao.brutos.BrutosException;

/**
 *
 * @author Afonso Brandao
 */
public class InterceptorStack extends Interceptor{
    
    private List interceptors;

    public InterceptorStack( InterceptorStack parent ) {
        super(parent);
        this.interceptors = new ArrayList( parent.interceptors );
    }
    
    public InterceptorStack() {
        this.interceptors = new ArrayList();
    }

    public List getInterceptors() {
        return interceptors;
    }

    public void setInterceptors(List interceptors) {
        this.interceptors = interceptors;
    }
    
    public void addInterceptor( Interceptor interceptor ){
        if( interceptors.contains( interceptor ) )
            throw new BrutosException( "interceptor already added: " + interceptor.getName() );
        else
            interceptors.add( interceptor );
    }
    
    public boolean removeInterceptor( Interceptor interceptor ){
        return interceptors.remove( interceptor );
    }
}
