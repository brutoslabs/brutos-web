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
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.mapping.ThrowableSafeData;

/**
 * Provê informações da requisição.
 * 
 * @author Brandao
 */
public interface StackRequestElement {

    /**
     * Obtém o objeto que representa a exceção lançada na requisição.
     * @return Objeto que representa a exceçao.
     */
    Throwable getObjectThrow();

    /**
     * Obtém o mapeamento da exceção.
     * @return Mapeamento da exceçao
     */
    ThrowableSafeData getThrowableSafeData();

    /**
     * Obtém os parâmetros da ação.
     * @return Parâmetros da ação.
     */
    Object[] getParameters();

    /**
     * Obtém o mapeamento do controlador.
     * @return Mapeamento do controlador.
     */
    Controller getController();

    /**
     * Obtém o objeto que representa a ação que será executada.
     * @return Ação que será executada.
     */
    ResourceAction getAction();

    /**
     * Obtém o resultado da ação.
     * @return Resultado da ação.
     */
    Object getResultAction();

    /**
     * Obtém o objeto que representa o controlador.
     * @return Objeto que representa o controlador.
     */
    Object getResource();
    
    /**
     * Obtém o manipulador da requisição.
     * @return Manipulador da requisição.
     */
    ConfigurableInterceptorHandler getHandler();

    /**
     * Obtém a vista da requisição.
     * @return Vista da requisição.
     */
    String getView();

    /**
     * Obtém o tipo de redirecionamento do fluxo para a vista.
     * @return Tipo de redirecionamento do fluxo para a vista.
     */
    DispatcherType getDispatcherType();

    /**
     * Define o objeto que representa a exceção lançada na requisição.
     * @param objectThrow Objeto que representa a exceção.
     */
    void setObjectThrow(Throwable objectThrow);

    /**
     * Define o mapeamento da exceção.
     * @param throwableSafeData Mapeamento da exceção.
     */
    void setThrowableSafeData(ThrowableSafeData throwableSafeData);

    /**
     * Define os parâmetros da ação.
     * @param parameters Parâmetros da ação.
     */
    void setParameters(Object[] parameters);

    /**
     * Define o mapeamento do controlador.
     * @param controller Mapeamento do controlador.
     */
    void setController(Controller controller);

    /**
     * Define o objeto que representa a ação que será executada.
     * @param action Ação que será executada.
     */
    void setAction(ResourceAction action);

    /**
     * Define o resultado da ação.
     * @param resultAction Resultado da ação.
     */
    void setResultAction(Object resultAction);

    /**
     * Define o manipulador da requisição.
     * @param handler Manipulador da requisição.
     */
    void setHandler(ConfigurableInterceptorHandler handler);

    /**
     * Define o objeto que representa o controlador.
     * @param resource Objeto que representa o controlador.
     */
    void setResource( Object resource );

    /**
     * Define a vista da requisição.
     * @param view Vista da requisição.
     */
    void setView( String view );

    /**
     * Define o tipo de redirecionamento do fluxo para a vista.
     * @param dispatcherType Tipo de redirecionamento do fluxo para a vista.
     */
    void setDispatcherType( DispatcherType dispatcherType );

}
