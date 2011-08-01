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

package org.brandao.brutos.web;

import java.util.Map;
import org.brandao.brutos.*;
import org.brandao.brutos.interceptor.InterceptorHandler;
import org.brandao.brutos.scope.Scope;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.mapping.MethodForm;
import org.brandao.brutos.Scopes;

/**
 * Implementa��o padr�o do ActionResolver.
 * 
 * @author Afonso Brandao
 */
public class WebActionResolver implements ActionResolver{
    
    public ResourceAction getResourceAction(Controller controller,
            InterceptorHandler handler) {

        if( controller.getUri() != null ){
            Scope scope = handler.getContext().getScopes()
                    .get(ScopeType.PARAM.toString());

            return getResourceAction( 
                    controller,
                    String.valueOf(
                            scope.get( controller.getMethodId() ) ),
                    handler);
        }
        else
            return getResourceAction( controller, handler.requestId(), handler );
        
    }

    public ResourceAction getResourceAction(Controller controller, String actionId, 
            InterceptorHandler handler) {

        if( controller.getUri() != null ){
            MethodForm method = controller
                    .getMethodByName( actionId );
            return method == null? null : getResourceAction( method );
        }
        else{
            Scopes scopes = handler.getContext().getScopes();
            Scope request = scopes.get( ScopeType.PARAM );
            for( String u: controller.getMethods().keySet() ){

                URIMapping uriMap = WebControllerResolver.getURIMapping( u );
                if( uriMap.matches(actionId) ){

                    Map<String,String> params =
                            uriMap.getParameters(actionId);

                    for(String key: params.keySet() )
                        request.put(key, params.get(key) );
                    return getResourceAction( controller.getMethods().get(u) );
                }

            }
            return null;
        }
    }

    public ResourceAction getResourceAction(MethodForm action) {
        return new DefaultResourceAction( action );
    }

}
