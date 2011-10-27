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
import org.brandao.brutos.ioc.IOCProvider;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.proxy.ProxyFactory;

/**
 * Altera o fluxo de execução de uma ação.
 * 
 * @author Brandao
 */
public class FlowController {

    public FlowController(){
    }
    
    /**
     * Direciona o fluxo para uma determinada view.
     * @param dispatcher Tipo do direcionamento do fluxo.
     * @return Fluxo.
     */
    public static DispatcherView dispatcher( DispatcherType dispatcher ){
        return new DispatcherView(dispatcher);
    }

    /**
     * Direciona o fluxo para uma determinada view.
     * @return Fluxo.
     */
    public static DispatcherView dispatcher(){
        return dispatcher(DispatcherType.FORWARD);
    }
    
    /**
     * Obtém a instância do controlador.
     * @param clazz Classe do controlador.
     * @return Instância do controlador.
     */
    public static Object getController( Class clazz ){
        return getControllerInstance(clazz);
    }

    /**
     * Executa uma ação.
     * @param clazz Classe do controlador.
     * @param actionName Identificação da ação.
     * @return Resultado da execução da ação.
     */
    public static Object execute( Class clazz, String actionName ){
        return Invoker.getInstance().invoke(clazz, actionName);
    }

    private static Object getControllerInstance( Class controllerClass ){
        ConfigurableApplicationContext context = 
                (ConfigurableApplicationContext) Invoker.getApplicationContext();
        
        ControllerResolver controllerResolver = context.getControllerResolver();
        IOCProvider iocProvider = context.getIocProvider();
        CodeGeneratorProvider codeGeneratorProvider =
                context.getCodeGeneratorProvider();

        Controller controller =
            controllerResolver
                .getController(
                    context.getControllerManager(),
                    controllerClass);

        if( controller == null )
            throw new BrutosException(
                String.format(
                    "controller not configured: %s",
                    new Object[]{controllerClass.getName()} ));

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
                        context,
                        context.getInvoker());

        return proxy;
    }

}
