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

import org.brandao.brutos.scope.Scope;
import org.brandao.brutos.web.AbstractWebApplicationContext;
import javax.servlet.http.HttpServletRequest;
import org.brandao.brutos.interceptor.InterceptorHandler;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.mapping.Action;
import org.brandao.brutos.web.ContextLoader;
import org.brandao.brutos.web.WebApplicationContext;

/**
 *
 * @deprecated 
 * @author Afonso Brandao
 */
public class DefaultMethodResolver implements MethodResolver{
    
    public ResourceMethod getResourceMethod( HttpServletRequest request ){
        WebApplicationContext brutosContext = ContextLoader
                .getCurrentWebApplicationContext();
        Controller controller = null;//brutosContext.getController();
        Action method = controller
                .getMethodByName( request.getParameter( controller.getMethodId() ) );
        
        return method == null? null : getResourceMethod( method );
    }

    private ResourceMethod getResourceMethod( Action methodForm ){
        return new DefaultResourceMethod( methodForm );
    }

    public ResourceAction getResourceAction(Controller controller, Scopes scopes,
            InterceptorHandler handler) {
        Scope scope = scopes.get(ScopeType.PARAM);
        Action method = controller
                .getMethodByName( String.valueOf( scope.get( controller.getMethodId() ) ) );
        return method == null? null : getResourceMethod( method );
    }

    public ResourceAction getResourceAction(Controller controller, InterceptorHandler handler) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ResourceAction getResourceAction(Controller controller, String actionId, InterceptorHandler handler) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ResourceAction getResourceAction(Action action) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
