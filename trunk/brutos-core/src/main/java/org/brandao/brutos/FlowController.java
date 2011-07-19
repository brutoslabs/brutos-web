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
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.mapping.MethodForm;

/**
 * Altera o fluxo de execução de uma ação.
 * 
 * @author Brandao
 */
public class FlowController {

    private ConfigurableApplicationContext context;
    private DispatcherType dispatcher;

    public FlowController(){
        this(null, null);
    }
    
    public FlowController(DispatcherType dispatcher,
            ConfigurableApplicationContext context){
        this.dispatcher = dispatcher;
        this.context = context;
    }

    /**
     * Define como o fluxo será direcionado.
     * @param dispatcher Tipo do direcionamento do fluxo.
     * @return Fluxo.
     */
    public static FlowController dispatcher( DispatcherType dispatcher ){
        ConfigurableApplicationContext app =
                Invoker.getCurrentApplicationContext();
        //Invoker invoker = app.getInvoker();
        //StackRequestElement element =
        //        invoker.createStackRequestElement();

        //element.setDispatcherType(dispatcher);
        return new FlowController(dispatcher, app);
    }

    private static void to(Class clazz, String actionName, String view,
            DispatcherType dispatcher, ConfigurableApplicationContext context){
        if( view != null ){
            throw new RedirectException(view,dispatcher);
            /*ConfigurableApplicationContext app =
                    Invoker.getCurrentApplicationContext();
            
            StackRequestElement sre =
                    app.getInvoker().getStackRequestElement();
            sre.setDispatcherType(this.stackRequestElement.getDispatcherType());
            sre.setView(view);
            */
        }
        else{
            ControllerManager cm = context.getControllerManager();
            Controller co = cm.getController(clazz);
            MethodForm action = co == null? null : co.getMethodByName(actionName);
            String id =
                    context.getControllerResolver().getControllerId(co, action);
            throw new RedirectException(id,dispatcher);

            /*
            ImpInterceptorHandler ih = new ImpInterceptorHandler();
            ih.setRequestId(null);
            ih.setContext(context);
            ih.setResource( co == null? null : co.getInstance(context.getIocProvider()) );
            ih.setResourceAction( action == null? null : new DefaultResourceAction(action) );
            stackRequestElement.setAction(ih.getResourceAction());
            stackRequestElement.setController(co);
            stackRequestElement.setHandler(ih);
            stackRequestElement.setView(view);
            stackRequestElement.setResource(ih.getResource());
            context.getInvoker().invoke(stackRequestElement);
            */
        }

    }
    
    /**
     * O fluxo é alterado para o controlador. A ação default é executada.
     * @param clazz Clsse do controlador.
     */
    public void to( Class clazz ){
        to(clazz, null, null, dispatcher, context);
    }

    /**
     * O fluxo é alterado para o controlador ou ação com a identificação
     * informada.
     * @param value Identificação.
     */
    public void to( String value ){
        /*ConfigurableApplicationContext app =
                Invoker.getCurrentApplicationContext();
        app.getInvoker().invoke(value);
        */
        to(null,null,value,dispatcher,context);
    }

    /**
     * O fluxo é alterado para uma determinada visão.
     * @param value
     */
    public void toView( String value ){
        to(null, null, value, dispatcher, context);
    }

    /**
     * O fluxo é alterado para uma ação associada a um método.
     * @param clazz Classe do controlador.
     * @return Instância do controlador.
     */
    public Object toController( Class clazz ){
        ConfigurableApplicationContext app =
                Invoker.getCurrentApplicationContext();
        StackRequestElement e = app.getInvoker().getStackRequestElement();
        e.setDispatcherType(dispatcher);
        return context.getController(clazz);
    }

    /**
     * O fluxo é alterado para uma ação.
     * @param clazz Classe do controlador.
     * @param actionName Identificação da ação
     */
    public void toAction( Class clazz, String actionName ){
        to(clazz, actionName, null, dispatcher, context);
    }

    /*
     * Obtém o fluxo atual.
     * @return Fluxo.
     */
    /*
     public FlowController getCurrentFlow(){
        ConfigurableApplicationContext app =
                Invoker.getCurrentApplicationContext();
        return new FlowController(
            app.getInvoker().getStackRequestElement(),
            app);
    }
    */
}
