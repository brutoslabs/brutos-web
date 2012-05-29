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

import org.brandao.brutos.interceptor.InterceptorHandler;
import org.brandao.brutos.scope.Scope;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.mapping.Action;

/**
 * Implementação padrão do ActionResolver.
 * 
 * @author Afonso Brandao
 */
public class DefaultActionResolver implements ActionResolver{
    
    public ResourceAction getResourceAction(Controller controller,
            InterceptorHandler handler) {
        Scope scope = handler.getContext().getScopes().get(ScopeType.PARAM);
        return getResourceAction(
                controller,
                String.valueOf( scope.get( controller.getMethodId() ) ),
                handler );
    }

    public ResourceAction getResourceAction(Controller controller, String actionId,
            InterceptorHandler handler) {
        Action method = controller
                .getMethodByName( actionId );
        return method == null? null : getResourceAction( method );
    }

    public ResourceAction getResourceAction(Action action) {
        return new DefaultResourceAction( action );
    }

}
