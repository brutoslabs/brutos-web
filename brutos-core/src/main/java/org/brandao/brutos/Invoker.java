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
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.scope.Scope;
import org.brandao.brutos.scope.ThreadScope;
import org.brandao.brutos.view.ViewProvider;

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
    protected AbstractApplicationContext applicationContext;
    protected ViewProvider viewProvider;
    public Invoker() {
    }

    public Invoker( ControllerResolver controllerResolver, IOCProvider iocProvider, 
            ControllerManager controllerManager, ActionResolver actionResolver, 
            AbstractApplicationContext applicationContext, ViewProvider viewProvider ){
        
        this.controllerResolver = controllerResolver;
        this.iocProvider        = iocProvider;
        this.controllerManager  = controllerManager;
        this.actionResolver     = actionResolver;
        this.applicationContext = applicationContext;
        this.viewProvider       = viewProvider;
    }

    /**
     * Executa uma ação.
     *
     * @param requestId Identificação da ação.
     * @return Verdadeiro se foi executada a ação, coso contrário é falso.
     */
    public boolean invoke( String requestId ){

        Scopes scopes = applicationContext.getScopes();
        //Scope requestScope = scopes.get(ScopeType.REQUEST);
        ImpInterceptorHandler ih = new ImpInterceptorHandler();
        ih.setRequestId(requestId);
        ih.setContext(applicationContext);
        
        Controller form = controllerResolver.getController(controllerManager, ih);


        if( form == null )
            return false;

        ih.setResource( form.getInstance(iocProvider)/*iocProvider.getBean(form.getId())*/ );
        ih.setResourceAction( actionResolver.getResourceAction(form, scopes, ih) );


        StackRequestElement element = createStackRequestElement();

        element.setAction(ih.getResourceAction());
        element.setController(form);
        element.setHandler(ih);
        /*
        long time = System.currentTimeMillis();
        try{
            currentApp.set( this.applicationContext );
            requestScope.put( BrutosConstants.IOC_PROVIDER , this.iocProvider );
            requestScope.put( BrutosConstants.ROOT_APPLICATION_CONTEXT_ATTRIBUTE,
                    this.applicationContext );
            
            if( logger.isDebugEnabled() )
                logger.debug( "Received a new request: " + requestId );


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
        */

        return invoke(element);
    }

    public boolean invoke( StackRequestElement element ){

        Scopes scopes = applicationContext.getScopes();
        Scope requestScope = scopes.get(ScopeType.REQUEST);
        
        RequestInstrument requestInstrument =
                getRequestInstrument(requestScope);

        StackRequest stackRequest = (StackRequest)requestInstrument;
        
        long time = System.currentTimeMillis();
        boolean createdThreadScope = ThreadScope.create();
        try{
            currentApp.set( this.applicationContext );

            stackRequest.push(element);
            //requestScope.put( BrutosConstants.IOC_PROVIDER , this.iocProvider );
            //requestScope.put( BrutosConstants.ROOT_APPLICATION_CONTEXT_ATTRIBUTE,
            //        this.applicationContext );
            element.getController()
                    .proccessBrutosAction( element.getHandler() );
            return true;
        }
        finally{
            if(createdThreadScope)
                ThreadScope.destroy();
            stackRequest.pop();
            currentApp.remove();
            //requestScope.remove( BrutosConstants.IOC_PROVIDER );
            if( logger.isDebugEnabled() )
                logger.debug(
                        String.format( "Request processed in %d ms",
                            (System.currentTimeMillis()-time) ) );
        }

        
    }

    private RequestInstrument getRequestInstrument(Scope scope){
        RequestInstrument requestInstrument = 
                (RequestInstrument)
                    scope.get(BrutosConstants.REQUEST_INSTRUMENT);

        if( requestInstrument == null ){
            requestInstrument =
                    new RequestInstrumentImp(
                        this.applicationContext,
                        this.iocProvider,
                        this.viewProvider);

            scope.put(BrutosConstants.REQUEST_INSTRUMENT, requestInstrument);
        }

        return requestInstrument;
    }

    protected StackRequestElement createStackRequestElement(){
        return new StackRequestElementImp();
    }

    
    public static AbstractApplicationContext getCurrentApplicationContext(){
        return (AbstractApplicationContext) currentApp.get();
    }
    
}
