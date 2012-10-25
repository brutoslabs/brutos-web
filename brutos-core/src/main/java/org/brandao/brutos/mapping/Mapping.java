/*
 * Brutos Web MVC http://www.brutosframework.com.br/
 * Copyright (C) 2009-2012 Afonso Brandao. (afonso.rbn@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.brandao.brutos.mapping;

import org.brandao.brutos.BrutosException;
import org.brandao.brutos.Configuration;
import org.brandao.brutos.old.programatic.*;
import org.brandao.brutos.InterceptorManager;
/**
 * @deprecated 
 * @author Afonso Brandao
 */
public abstract class Mapping {
    
    private IOCManager iocManager;
    
    private WebFrameManager webFrameManager;
    
    private InterceptorManager interceptorManager;
    
    public Mapping() {
        throw new UnsupportedOperationException( "deprecated: use ApplicationContext" );
    }

    /*
    public void configure( Configuration config, ServletContextEvent sce ){
    }
    */
    public abstract void destroy();
    /*
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
    */
    
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
