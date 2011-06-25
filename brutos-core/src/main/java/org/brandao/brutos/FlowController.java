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

    private StackRequestElement stackRequestElement;
    private ConfigurableApplicationContext context;

    public FlowController(){
        this(null, null);
    }
    
    public FlowController(StackRequestElement stackRequestElement,
            ConfigurableApplicationContext context){
        this.stackRequestElement = stackRequestElement;
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
        Invoker invoker = app.getInvoker();
        StackRequestElement element =
                invoker.createStackRequestElement();

        element.setDispatcherType(dispatcher);
        return new FlowController(element, app);
    }

    private void to(Class clazz, String actionName, String view){
        if( view != null ){
            ConfigurableApplicationContext app =
                    Invoker.getCurrentApplicationContext();
            
            StackRequestElement sre =
                    app.getInvoker().getStackRequestElement();
            sre.setDispatcherType(this.stackRequestElement.getDispatcherType());
            sre.setView(view);
        }
        else{
            ControllerManager cm = context.getControllerManager();
            Controller co = cm.getController(clazz);
            MethodForm action = co == null? null : co.getMethodByName(actionName);
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
        }

    }
    
    /**
     * O fluxo é alterado para o controlador.
     * @param clazz Clsse do controlador.
     */
    public void to( Class clazz ){
        this.to(clazz, null, null);
    }

    /**
     * O fluxo é alterado para o controlador ou ação com a identificação
     * informada.
     * @param value Identificação.
     */
    public static void to( String value ){
        ConfigurableApplicationContext app =
                Invoker.getCurrentApplicationContext();
        app.getInvoker().invoke(value);
    }

    /**
     * O fluxo é alterado para uma determinada visão.
     * @param value
     */
    public void toView( String value ){
        this.to(null, null, value);
    }

    /**
     * O fluxo é alterado para uma ação associada a um método.
     * @param clazz Classe do controlador.
     * @return Instância do controlador.
     */
    public Object toController( Class clazz ){
        return context.getController(clazz);
    }

    /**
     * O fluxo é alterado para uma ação.
     * @param clazz Classe do controlador.
     * @param actionName Identificação da ação
     */
    public void toAction( Class clazz, String actionName ){
        this.to(clazz, actionName, null);
    }

    /**
     * Obtém o fluxo atual.
     * @return Fluxo.
     */
    public FlowController getCurrentFlow(){
        ConfigurableApplicationContext app =
                Invoker.getCurrentApplicationContext();
        return new FlowController(
            app.getInvoker().getStackRequestElement(),
            app);
    }
}
