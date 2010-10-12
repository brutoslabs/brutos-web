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

package org.brandao.brutos.ioc;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.ContextLoaderListener;

/**
 *
 * @author Afonso Brandao
 */
public class ContextFactory implements FactoryBean<ServletContext>{
    
    private ServletContext context;
    
    public ContextFactory() {
        ServletRequest request = ContextLoaderListener.currentRequest.get();
        if( !(request instanceof HttpServletRequest) )
            throw new BrutosException( "the current request is not instance HttpServletRequest!" );
        
        this.context = ((HttpServletRequest)request).getSession().getServletContext();
    }

    public ServletContext createInstance() {
        return context;
    }

    public Class<ServletContext> getClassType() {
        return ServletContext.class;
    }
}
