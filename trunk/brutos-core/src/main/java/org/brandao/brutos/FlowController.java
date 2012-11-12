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
        ApplicationContext context = Invoker.getCurrentApplicationContext();
        return context.getController(controllerClass);
    }

}
