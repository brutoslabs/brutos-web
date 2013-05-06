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

/**
 * Responsável por identificar um controlador. Um controlador pode ser identificado 
 * a partir dos dados enviados na requisição ou por meio de sua classe. 
 * Nos casos em que a ação é representada por um URI, ele pode se comportar como 
 * o ActionResolver, identificando também a ação.
 * 
 * @author Afonso Brandao
 */
public interface ControllerResolver {

    /**
     * Obtém o controlador a partir dos dados enviados na requisição.
     * @param controllerManager Gestor dos controladores.
     * @param handler Manipulador da requisição.
     * @return Controlador.
     */
    Controller getController( ControllerManager controllerManager, ConfigurableInterceptorHandler handler );

    /**
     * Obtém o controlador a partir de sua classe.
     * @param controllerManager Gestor dos controladores.
     * @param controllerClass Classe que representa o controlador.
     * @return Controlador.
     */
    Controller getController( ControllerManager controllerManager, Class controllerClass );
    
}
