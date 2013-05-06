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
 * Responsável por identificar uma ação a partir dos dados enviados
 * na requisição, mapeamento ou a partir da identificação da ação.
 * Os controladores cujo o mapemaneto de ação for PARAMETER, a ação será obtida
 * a partir de sua identificação, nesse caso, a identificação da ação não pode
 * representar um URI. Nos demais mapeamentos de ação, a ação pode ser identificada
 * a partir dos dados da requisição ou por meio de seu mapeamento. Se a ação
 * já for identificada no momento em que o controlador for obtido, ele não será
 * executado.
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
     * Obtém a ação a partir de sua identificação, normalmente um URI.
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
