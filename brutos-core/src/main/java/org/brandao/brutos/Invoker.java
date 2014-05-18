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

package org.brandao.brutos;

import org.brandao.brutos.interceptor.ConfigurableInterceptorHandler;
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
    protected ConfigurableApplicationContext applicationContext;
    protected ViewProvider viewProvider;
    protected RequestProvider requestProvider;
    protected ResponseProvider responseProvider;
    
    public Invoker() {
    }

    public Invoker( ControllerResolver controllerResolver, IOCProvider iocProvider, 
            ControllerManager controllerManager, ActionResolver actionResolver, 
            ConfigurableApplicationContext applicationContext, ViewProvider viewProvider ){
        
        this.controllerResolver = controllerResolver;
        this.iocProvider        = iocProvider;
        this.controllerManager  = controllerManager;
        this.actionResolver     = actionResolver;
        this.applicationContext = applicationContext;
        this.viewProvider       = viewProvider;
        this.requestProvider    = new RequestProvider();
        this.responseProvider   = new ResponseProvider();
    }

    /**
     * Executa uma ação.
     *
     * @param requestId Identificação da ação.
     * @return Verdadeiro se foi executada a ação, coso contrário é falso.
     */
    public boolean invoke( String requestId ){
        return invoke(requestId, null);
    }
    
    /**
     * Executa uma ação.
     *
     * @param requestId Identificação da ação.
     * @param externalThrow Exceção externa. Por exemplo FileUploadException.
     * @return Verdadeiro se foi executada a ação, coso contrário é falso.
     */
    public boolean invoke( String requestId, Throwable externalThrow ){

        ImpInterceptorHandler ih = new ImpInterceptorHandler();
        ih.setRequestId(requestId);
        ih.setContext(applicationContext);
        
        Controller form = controllerResolver.getController(controllerManager, ih);


        if( form == null )
            return false;

        ih.setResource( form.getInstance(iocProvider) );
        
        if(ih.getResourceAction() == null)
            ih.setResourceAction( actionResolver.getResourceAction(form, ih) );


        StackRequestElement element = createStackRequestElement();

        element.setAction(ih.getResourceAction());
        element.setController(form);
        element.setHandler(ih);
        element.setResource(ih.getResource());
        element.setObjectThrow(externalThrow);
        return invoke(element);
    }

    public Object invoke( Controller controller, ResourceAction action,
            Object[] parameters ){
        return invoke(controller, action, null, parameters);
    }

    public Object invoke( Controller controller, ResourceAction action, Object resource,
            Object[] parameters ){

        if( controller == null )
            throw new IllegalArgumentException("controller not found");

        if( action == null )
            throw new IllegalArgumentException("action not found");

        // create factory or other solution
        ImpInterceptorHandler handler = new ImpInterceptorHandler();

        handler.setContext(applicationContext);
        handler.setResourceAction(action);
        handler.setResource(
            resource == null?
                controller.getInstance(applicationContext.getIocProvider()) :
                resource);

        StackRequestElement stackRequestElement = createStackRequestElement();

        stackRequestElement.setAction(action);
        stackRequestElement.setController(controller);
        stackRequestElement.setHandler(handler);
        stackRequestElement.setParameters(parameters);
        stackRequestElement.setResource(handler.getResource());

        invoke(stackRequestElement);
        return stackRequestElement.getResultAction();
    }


    public Object invoke( Class controllerClass, String actionId ){
        Controller controller =
            applicationContext
                .getControllerResolver()
                    .getController(controllerManager, controllerClass);


        // create factory or other solution
        ImpInterceptorHandler ih = new ImpInterceptorHandler();
        ih.setRequestId(controller.getId());
        ih.setContext(applicationContext);

        ResourceAction action =
                applicationContext
                .getActionResolver()
                .getResourceAction(controller, actionId, ih);

        return this.invoke(controller, action, null);
    }

    public RequestInstrument getRequestInstrument(){
        Scopes scopes = applicationContext.getScopes();
        Scope requestScope = scopes.get(ScopeType.REQUEST);

        RequestInstrument requestInstrument =
                getRequestInstrument(requestScope);

        return requestInstrument;
    }
    
    public StackRequest getStackRequest(){
        RequestInstrument requestInstrument = getRequestInstrument();
        return getStackRequest(requestInstrument);
    }

    public StackRequest getStackRequest(RequestInstrument value){
        return (StackRequest)value;
    }
    
    public StackRequestElement getStackRequestElement(){
        return getStackRequest().getCurrent();
    }

    public boolean invoke( StackRequestElement element ){

        long time = -1;
        boolean createdThreadScope = false;
        StackRequest stackRequest  = null;
        boolean isFirstCall        = false;
        RequestInstrument requestInstrument;
        ConfigurableInterceptorHandler configurableInterceptorHandler;
        MvcRequest oldRequest      = null;
        MvcResponse oldresponse    = null;
        
        try{
            oldRequest         = this.requestProvider.start();
            oldresponse        = this.responseProvider.start();
            time               = System.currentTimeMillis();
            createdThreadScope = ThreadScope.create();
            requestInstrument  = getRequestInstrument();
            stackRequest       = getStackRequest(requestInstrument);
            isFirstCall        = stackRequest.isEmpty();
            configurableInterceptorHandler = element.getHandler();
            
            configurableInterceptorHandler.setRequestInstrument(requestInstrument);
            configurableInterceptorHandler.setStackRequestElement(element);
            
            if( isFirstCall )
                currentApp.set( this.applicationContext );

            stackRequest.push(element);
            element.getController()
                    .proccessBrutosAction( element.getHandler() );
            return true;
        }
        finally{
            this.requestProvider.destroy(oldRequest);
            this.responseProvider.destroy(oldresponse);
            
            if(createdThreadScope)
                ThreadScope.destroy();
            
            stackRequest.pop();

            if( isFirstCall )
                currentApp.remove();

            if( logger.isDebugEnabled() )
                logger.debug(
                        String.format( "Request processed in %d ms",
                            new Object[]{
                                new Long((System.currentTimeMillis()-time))} ) );
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

    StackRequestElement createStackRequestElement(){
        return new StackRequestElementImp();
    }

    
    public static ApplicationContext getCurrentApplicationContext(){
        return (ApplicationContext) currentApp.get();
    }

    public static Invoker getInstance(){
        ConfigurableApplicationContext context =
                (ConfigurableApplicationContext)getCurrentApplicationContext();

        if( context == null )
            throw new BrutosException("can not get invoker");

        return context.getInvoker();
    }
    
    public void flush(){
        this.requestProvider
                .setFactory(this.applicationContext.getRequestFactory());
        this.responseProvider
                .setFactory(this.applicationContext.getResponseFactory());
    }
}
