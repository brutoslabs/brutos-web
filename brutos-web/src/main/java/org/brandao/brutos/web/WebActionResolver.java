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
import org.brandao.brutos.mapping.Form;
import org.brandao.brutos.mapping.MethodForm;
import org.brandao.brutos.scope.Scopes;
import org.brandao.brutos.web.WebControllerResolver.URIMap;

/**
 * Implementação padrão do ActionResolver.
 * 
 * @author Afonso Brandao
 */
public class WebActionResolver implements ActionResolver{
    
    private ResourceAction getResourceMethod( MethodForm methodForm ){
        return new DefaultResourceAction( methodForm );
    }

    public ResourceAction getResourceAction(Form controller, InterceptorHandler handler) {

        if( controller.getUri() != null ){
            Scope scope = Scopes.get(ScopeType.PARAM.toString());

            MethodForm method = controller
                    .getMethodByName( 
                        String.valueOf(
                            scope.get( controller.getMethodId() ) ) );
            return method == null? null : getResourceMethod( method );
        }
        else{
            Scope request = Scopes.get( ScopeType.PARAM );
            for( String u: controller.getMethods().keySet() ){

                URIMap uriMap = WebControllerResolver.getURIMapping( u );
                if( uriMap.matches(handler.requestId()) ){

                    Map<String,String> params =
                            uriMap.getParameters(handler.requestId());

                    for(String key: params.keySet() )
                        request.put(key, params.get(key) );
                    return getResourceMethod( controller.getMethods().get(u) );
                }

            }
            return null;
        }
    }

}
