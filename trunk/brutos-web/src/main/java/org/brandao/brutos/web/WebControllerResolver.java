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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.brandao.brutos.*;
import org.brandao.brutos.interceptor.ConfigurableInterceptorHandler;
import org.brandao.brutos.mapping.Action;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.old.programatic.WebFrameManager;
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
            ActionType actionType = controller.getActionType();
            
            if(this.isMatch(actionType, controller, paramScope, uri) || 
               this.isMatch(actionType, controller, paramScope, handler, uri))
                return controller;
            
        }

        return null;
    }

    private boolean isMatch(ActionType actionType, Controller controller, 
            Scope scope, ConfigurableInterceptorHandler handler, String uri){
        
        if(actionType == ActionType.PARAMETER)
            return false;

        URIMapping uriMap;
        
        Iterator actionsId = controller.getActions().values().iterator();
        
        while(actionsId.hasNext()){
            
            Action action = (Action) actionsId.next();
            
            String id = action.getId();
            
            uriMap = getURIMapping( id );
            if(uriMap.matches(uri)){
                updateRequest(uri, scope, uriMap);

                ConfigurableApplicationContext context =
                    (ConfigurableApplicationContext)handler.getContext();

                ActionResolver actionResolver =
                        context.getActionResolver();
                
                /*
                Action action = 
                        controller.getAction(id);
                */
                
                ResourceAction resourceAction = 
                        actionResolver.getResourceAction(action);

                handler.setResourceAction(resourceAction);

                return true;
            }
        }
        
        return false;
    }
    
    private boolean isMatch(ActionType actionType, Controller controller, 
            Scope scope, String uri){
        
        if(actionType == ActionType.DETACHED)
            return false;

        URIMapping uriMap;
        
        uriMap = getURIMapping( controller.getId() );

        if(uriMap.matches(uri)){
            updateRequest(uri, scope, uriMap);
            return true;
        }
        else{
            Iterator alias = controller.getAlias().iterator();

            while(alias.hasNext()){
                uriMap = getURIMapping( (String)alias.next() );

                if(uriMap.matches(uri)){
                    updateRequest(uri, scope, uriMap);
                    return true;
                }
            }
        }
        
        return false;
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