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

package org.brandao.brutos;

import org.brandao.brutos.interceptor.ImpInterceptorHandler;
import org.brandao.brutos.ioc.IOCProvider;
import org.brandao.brutos.logger.Logger;
import org.brandao.brutos.logger.LoggerProvider;
import org.brandao.brutos.mapping.Form;
import org.brandao.brutos.scope.Scope;
import org.brandao.brutos.Scopes;

/**
 * Classe usada para invocar a aplicação.
 * 
 * @author Afonso Brandao
 */
public class Invoker {

    private static final ThreadLocal currentApp;

    static{
        currentApp = new ThreadLocal();
    }
    
    protected Logger logger = 
            LoggerProvider.getCurrentLoggerProvider()
            .getLogger(Invoker.class);
    
    protected ControllerResolver controllerResolver;
    protected IOCProvider iocProvider;
    protected ControllerManager controllerManager;
    protected ActionResolver actionResolver;
    protected ApplicationContext applicationContext;
    
    public Invoker() {
    }

    public Invoker( ControllerResolver controllerResolver, IOCProvider iocProvider, 
            ControllerManager controllerManager, ActionResolver actionResolver, 
            ApplicationContext applicationContext ){
        
        this.controllerResolver = controllerResolver;
        this.iocProvider        = iocProvider;
        this.controllerManager  = controllerManager;
        this.actionResolver     = actionResolver;
        this.applicationContext = applicationContext;
    }

    /**
     * Executa uma ação.
     *
     * @param requestId Identificação da ação.
     * @return Verdadeiro se foi executada a ação, coso contrário é falso.
     */
    public boolean invoke( String requestId ){

        Scopes scopes = applicationContext.getScopes();
        Scope requestScope = scopes.get(ScopeType.REQUEST);
        ImpInterceptorHandler ih = new ImpInterceptorHandler();
        ih.setRequestId(requestId);
        ih.setContext(applicationContext);
        
        Form form = controllerResolver.getController(controllerManager, ih);


        if( form == null )
            return false;

        long time = System.currentTimeMillis();
        try{
            currentApp.set( this.applicationContext );
            requestScope.put( BrutosConstants.IOC_PROVIDER , this.iocProvider );
            requestScope.put( BrutosConstants.ROOT_APPLICATION_CONTEXT_ATTRIBUTE,
                    this.applicationContext );
            
            if( logger.isDebugEnabled() )
                logger.debug( "Received a new request: " + requestId );

            ih.setResource( iocProvider.getBean(form.getId()) );
            
            ih.setResourceAction( actionResolver.getResourceAction(form, scopes, ih) );

            if( logger.isDebugEnabled() ){
                logger.debug(
                    String.format(
                        "Controller: %s Method: %s",
                        form.getClass().getName() ,
                        ih.getResourceAction() == null?  "" : ih.getResourceAction().getMethod().getName() )
                );


            }
            form.proccessBrutosAction( ih );
        }
        finally{
            currentApp.remove();
            requestScope.remove( BrutosConstants.IOC_PROVIDER );
            if( logger.isDebugEnabled() )
                logger.debug(
                        String.format( "Request processed in %d ms",
                            (System.currentTimeMillis()-time) ) );
        }

        return true;
    }

    public static ApplicationContext getCurrentApplicationContext(){
        return (ApplicationContext) currentApp.get();
    }
}
