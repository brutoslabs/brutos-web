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

import javax.servlet.ServletContextEvent;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.Configuration;
import org.brandao.brutos.old.programatic.*;
import org.brandao.brutos.InterceptorManager;
/**
 *
 * @author Afonso Brandao
 */
public abstract class Mapping {
    
    private IOCManager iocManager;
    
    private WebFrameManager webFrameManager;
    
    private InterceptorManager interceptorManager;
    
    public Mapping() {
        throw new UnsupportedOperationException( "deprecated: use ApplicationContext" );
    }
    
    public void configure( Configuration config, ServletContextEvent sce ){
    }
    
    public abstract void destroy();

    public static Mapping getMapping( Configuration configuration, ServletContextEvent sce ){
        String amName = 
            configuration
                .getProperty(
                    "org.brandao.brutos.mapping", 
                    "org.brandao.brutos.programatic.AnnotationMapping"
                );
        
        Mapping mapping;
        

        try{
            Class<?> appManager = Class.forName( amName, true, Thread.currentThread().getContextClassLoader() );
            mapping = (Mapping)appManager.newInstance();
            return mapping;
        }
        catch( ClassNotFoundException e ){
            throw new BrutosException( e );
        }
        catch( InstantiationException e ){
            throw new BrutosException( e );
        }
        catch( IllegalAccessException e ){
            throw new BrutosException( e );
        }
    }

    public IOCManager getIocManager() {
        return iocManager;
    }

    public void setIocManager(IOCManager iocManager) {
        this.iocManager = iocManager;
    }

    public WebFrameManager getWebFrameManager() {
        return webFrameManager;
    }

    public void setWebFrameManager(WebFrameManager webFrameManager) {
        this.webFrameManager = webFrameManager;
    }

    public InterceptorManager getInterceptorManager() {
        return interceptorManager;
    }

    public void setInterceptorManager(InterceptorManager interceptorManager) {
        this.interceptorManager = interceptorManager;
    }
    
    public abstract void loadIOCManager( IOCManager iocManager );
    
    public abstract void loadWebFrameManager( WebFrameManager webFrameManager );
    
    public abstract void loadInterceptorManager( InterceptorManager interceptorManager );
    
}
