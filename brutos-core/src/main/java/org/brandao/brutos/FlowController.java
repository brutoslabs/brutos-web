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

import org.brandao.brutos.codegenerator.CodeGeneratorProvider;
import org.brandao.brutos.interceptor.ImpInterceptorHandler;
import org.brandao.brutos.ioc.IOCProvider;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.mapping.MethodForm;
import org.brandao.brutos.proxy.ProxyFactory;

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
        ConfigurableApplicationContext context =
                Invoker.getCurrentApplicationContext();
        //Invoker invoker = app.getInvoker();
        //StackRequestElement element =
        //        invoker.createStackRequestElement();

        //element.setDispatcherType(dispatcher);
        return new FlowController(dispatcher, context);
    }

    public static FlowController dispatcher(){
        return dispatcher(DispatcherType.FORWARD);
    }
    private static Object to(Class clazz, String actionName, String view,
            DispatcherType dispatcher, ConfigurableApplicationContext context){
        if( view != null )
            throw new RedirectException(view,dispatcher);
        else{
            ControllerManager cm = context.getControllerManager();
            Controller controller = cm.getController(clazz);

            if( controller == null )
                throw new BrutosException("can not find controller: " + clazz.getName());

            MethodForm action =
                controller == null?
                    null :
                    controller.getMethodByName(actionName);

            if( dispatcher == DispatcherType.REDIRECT){
                String id =
                    context.getControllerResolver()
                    .getControllerId(controller, action);
                throw new RedirectException( id, dispatcher );
            }

            DefaultResourceAction resourceAction =
                    new DefaultResourceAction(action);

            ImpInterceptorHandler handler = new ImpInterceptorHandler();

            handler.setContext(context);
            handler.setResourceAction(resourceAction);
            handler.setResource( 
                controller == null?
                    null :
                    controller.getInstance(context.getIocProvider()));

            StackRequestElementImp stackRequestElementImp =
                    new StackRequestElementImp();

            stackRequestElementImp.setAction(resourceAction);
            stackRequestElementImp.setController(controller);
            stackRequestElementImp.setHandler(handler);
            stackRequestElementImp.setResource(handler.getResource());

            context.getInvoker().invoke(stackRequestElementImp);
            return stackRequestElementImp.getResultAction();

            /*
            ControllerManager cm = context.getControllerManager();
            Controller co = cm.getController(clazz);
            MethodForm action = co == null? null : co.getMethodByName(actionName);
            String id =
                    context.getControllerResolver().getControllerId(co, action);
            throw new RedirectException(id,dispatcher);
            */

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
     * O fluxo é alterado para uma ação associada a um método.
     * @param clazz Classe do controlador.
     * @return Instância do controlador.
     */
    public Object to( Class clazz ){
        ConfigurableApplicationContext app =
                Invoker.getCurrentApplicationContext();
        StackRequestElement e = app.getInvoker().getStackRequestElement();
        e.setDispatcherType(dispatcher);
        return getController(clazz);
    }

    /**
     * O fluxo é alterado para uma ação.
     * @param clazz Classe do controlador.
     * @param actionName Identificação da ação
     */
    public Object to( Class clazz, String actionName ){
        return to(clazz, actionName, null, dispatcher, context);
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

    private Object getController( Class controllerClass ){
        ControllerManager controllerManager = context.getControllerManager();
        IOCProvider iocProvider = context.getIocProvider();
        CodeGeneratorProvider codeGeneratorProvider =
                context.getCodeGeneratorProvider();

        Controller controller =
                controllerManager.getController(controllerClass);

        if( controller == null )
            throw new BrutosException(
                String.format(
                    "controller not configured: %s",
                    controllerClass.getName() ));

        Object resource = controller.getInstance(iocProvider);
                //iocProvider.getBean(controller.getId());

        ProxyFactory proxyFactory =
                codeGeneratorProvider
                    .getProxyFactory(controllerClass);

        Object proxy =
                proxyFactory
                    .getNewProxy(
                        resource,
                        controller,
                        dispatcher,
                        context,
                        context.getInvoker());

        return proxy;
    }

}
