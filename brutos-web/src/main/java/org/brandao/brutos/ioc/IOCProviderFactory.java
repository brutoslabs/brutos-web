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
import org.brandao.brutos.BrutosConstants;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.web.ContextLoaderListener;
import org.brandao.brutos.old.programatic.IOCManager;

/**
 *
 * @author Afonso Brandao
 */
public class IOCProviderFactory implements FactoryBean<IOCManager>{
    
    private IOCManager provider;
    
    public IOCProviderFactory() {
        ServletRequest request = ContextLoaderListener.currentRequest.get();
        if( !(request instanceof HttpServletRequest) )
            throw new BrutosException( "the current request is not instance HttpServletRequest!" );
        
        ServletContext sc = ((HttpServletRequest)request).getSession().getServletContext();
        this.provider = (IOCManager)sc.getAttribute( BrutosConstants.IOC_MANAGER );
    }

    public IOCManager createInstance() {
        return provider;
    }

    public Class<IOCManager> getClassType() {
        return IOCManager.class;
    }
}
