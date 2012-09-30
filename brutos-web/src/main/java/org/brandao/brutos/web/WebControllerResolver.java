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

import java.util.*;
import org.brandao.brutos.*;
import javax.servlet.http.HttpServletRequest;
import org.brandao.brutos.interceptor.InterceptorHandler;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.old.programatic.WebFrameManager;
import org.brandao.brutos.ControllerManager;
import org.brandao.brutos.interceptor.ConfigurableInterceptorHandler;
import org.brandao.brutos.mapping.Action;
import org.brandao.brutos.scope.Scope;

/**
 *
 * @author Afonso Brandao
 */
public class WebControllerResolver implements ControllerResolver{

    private static Map<String,URIMapping> uris = new HashMap<String, URIMapping>();

    public WebControllerResolver() {
    }

    public static URIMapping getURIMapping( String uri ){
        try{
            if( uris.containsKey( uri ) )
                return uris.get( uri );
            else{
                URIMapping map = new URIMapping( uri );
                uris.put( uri , map);
                return map;
            }
        }
        catch( Exception e ){
            throw new BrutosException( e.getMessage(), e );
        }
    }

    public Controller getController(WebFrameManager webFrameManager, HttpServletRequest request) {
        String path         = request.getRequestURI();
        String contextPath  = request.getContextPath();
        path = path.substring( contextPath.length(), path.length() );
        
        path = path.replace( "\\", "/" );
        return webFrameManager.getForm( path );
    }

    public Controller getController(ControllerManager controllerManager, 
            ConfigurableInterceptorHandler handler) {
        String uri = handler.requestId();
        Iterator controllers = controllerManager.getAllControllers();
        Scope paramScope =
                handler.getContext().getScopes().get(WebScopeType.PARAM);
        
        while(controllers.hasNext()){
            Controller controller = (Controller)controllers.next();
            
            URIMapping uriMap;
            if(controller.getId() != null){
                uriMap = getURIMapping( controller.getId() );
                if(uriMap.matches(uri)){
                    updateRequest(uri, paramScope, uriMap);
                    return controller;
                }
            }
            else{
                Set actionsId = controller.getActions().keySet();
                for(Object id: actionsId){
                    uriMap = getURIMapping( (String)id );
                    if(uriMap.matches(uri)){
                        updateRequest(uri, paramScope, uriMap);
                        
                        ConfigurableApplicationContext context =
                            (ConfigurableApplicationContext)handler.getContext();
                        
                        ActionResolver actionResolver =
                                context.getActionResolver();
                        
                        Action action = 
                                controller.getAction((String)id);
                        
                        ResourceAction resourceAction = 
                                actionResolver.getResourceAction(action);
                        
                        handler.setResourceAction(resourceAction);
                        
                        return controller;
                    }
                }
            }
        }

        return null;
    }

    private void updateRequest(String uri, Scope paramScope, URIMapping uriMap){
        Map<String,String> params = uriMap.getParameters(uri);
        for(String key: params.keySet() )
            paramScope.put(key, params.get(key) );
    }
    
    public Controller getController(ControllerManager controllerManager, Class controllerClass) {
        Controller controller =
                controllerManager.getController(controllerClass);

        if(controller == null)
            throw new BrutosException(
                "controller not found: " + controllerClass.getName() );
        return controller;
    }

}