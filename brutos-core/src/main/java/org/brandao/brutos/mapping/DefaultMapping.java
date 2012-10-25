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

import org.brandao.brutos.old.programatic.IOCManager;
import org.brandao.brutos.InterceptorManager;
import org.brandao.brutos.old.programatic.WebFrameManager;

/**
 *
 * @author Afonso Brandao
 */
public class DefaultMapping extends Mapping{

    public DefaultMapping(){
        throw new UnsupportedOperationException( "deprecated: use DefaultContext" );
    }

    public void destroy() {
    }

    public void loadIOCManager(IOCManager iocManager){
        /*
        iocManager.addBean( "servletContextFactory",
                ContextFactory.class, ScopeType.APPLICATION, false, null );
                
        
        iocManager.addBean( "servletContext",
                ServletContext.class, ScopeType.APPLICATION, false,
                "servletContextFactory" ).setFactoryMethod( "createInstance" );

        iocManager.addBean( "iocManagerFactory", 
                IOCProviderFactory.class, ScopeType.APPLICATION, false, null );

        iocManager.addBean( "iocManager",
                IOCManager.class, ScopeType.APPLICATION, false,
                "iocManagerFactory" ).setFactoryMethod( "createInstance" );

        iocManager.addBean( "requestFactory", 
                RequestFactory.class, ScopeType.REQUEST, false, null );
        
        iocManager.addBean( "request",
                ServletRequest.class, ScopeType.REQUEST, false,
                "requestFactory" ).setFactoryMethod( "createInstance" );
         */
    }

    public void loadWebFrameManager(WebFrameManager webFrameManager){
    }

    public void loadInterceptorManager(InterceptorManager interceptorManager){
    }

}
