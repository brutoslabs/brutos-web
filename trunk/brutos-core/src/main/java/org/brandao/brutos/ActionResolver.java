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

import org.brandao.brutos.interceptor.InterceptorHandler;
import org.brandao.brutos.mapping.Action;
import org.brandao.brutos.mapping.Controller;

/**
 * Responsável por identificar uma ação. Uma ação pode ser identificada a partir 
 * dos dados enviados na requisição, mapeamento ou a partir de suas identificações.
 * Os controladores com mapemaneto PARAMETER, terão suas ações obtidas a partir 
 * de suas identificações. Nesse caso, as identificações não poderão ser representadas
 * por URIs. Já nos demais tipos de mapeamentos, as ações serão obtidas a partir 
 * dos dados da requisição ou por meio de seu mapeamento.
 * 
 * @author Afonso Brandao
 */
public interface ActionResolver {

    /**
     * Obtém a ação a partir dos dados enviados na requisição.
     * @param controller Controlador.
     * @param handler Manipulador da requisição.
     * @return Ação.
     */
    ResourceAction getResourceAction( Controller controller,
            InterceptorHandler handler );

    /**
     * Obtém a ação a partir de sua identificação.
     * @param controller Controlador
     * @param actionId Identificação da ação.
     * @param handler Manipulador da requisição.
     * @return Ação.
     */
    ResourceAction getResourceAction( Controller controller, String actionId,
            InterceptorHandler handler );

    /**
     * Obtém a ação a partir de seu mapeamento.
     * @param action Mapeamento da ação.
     * @return Ação.
     */
    ResourceAction getResourceAction( Action action );

}
