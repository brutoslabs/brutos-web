/*
 * Brutos Web MVC http://www.brutosframework.com.br/
 * Copyright (C) 2009 Afonso Brandao. (afonso.rbn@gmail.com)
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

package org.brandao.brutos.web;

import org.brandao.brutos.*;
import org.brandao.brutos.interceptor.InterceptorHandler;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.mapping.Action;
import org.brandao.brutos.scope.Scope;

/**
 * Implementação padrão do ActionResolver.
 * 
 * @author Afonso Brandao
 */
public class WebActionResolver implements ActionResolver{
    
    public ResourceAction getResourceAction(Controller controller,
            InterceptorHandler handler) {

        if( controller.getId() != null ){
            Scope scope = handler.getContext().getScopes()
                    .get(WebScopeType.PARAM.toString());

            return getResourceAction( 
                    controller,
                    String.valueOf(
                            scope.get( controller.getActionId() ) ),
                    handler);
        }
        else
            return getResourceAction( controller, handler.requestId(), handler );
        
    }

    public ResourceAction getResourceAction(Controller controller, String actionId, 
            InterceptorHandler handler) {

        Action method = controller
                .getActionByName( actionId );
        return method == null? null : getResourceAction( method );
    }

    public ResourceAction getResourceAction(Action action) {
        return new DefaultResourceAction( action );
    }

}
