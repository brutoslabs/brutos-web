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

    public Controller getController(ControllerManager controllerManager, 
            ConfigurableInterceptorHandler handler) {
        String uri = handler.requestId();
        Iterator controllers = controllerManager.getAllControllers();
        Scope paramScope =
                handler.getContext().getScopes().get(WebScopeType.PARAM);
        
        while(controllers.hasNext()){
            Controller controller = (Controller)controllers.next();
            ActionType actionType = controller.getActionType();
            String controllerId = controller.getId();
            
            URIMapping uriMap = getURIMapping( controllerId );

            if(actionType == ActionType.PARAMETER){
                if(uriMap.matches(controllerId))
                    return controller;
            }
            else{
                if(actionType == ActionType.HIERARCHY && uriMap.matches(uri)){
                    updateRequest(uri, paramScope, uriMap);
                    return controller;
                }
                
                Action action = this.getAction(controller, actionType, uri, paramScope);

                if(action != null){
                    updateHandler(action, handler);
                    return controller;
                }
            }
        }

        return null;
    }
    
    private Action getAction(Controller controller, ActionType actionType, String uri, Scope paramScope){
        Iterator actionsId = controller.getActions().values().iterator();
        
        while(actionsId.hasNext()){
            Action action = (Action) actionsId.next();

            String actionId = 
                    actionType == ActionType.HIERARCHY? 
                        controller.getId() + action.getId() :
                        action.getId();

            URIMapping uriMap = getURIMapping( actionId );

            if(uriMap.matches(uri)){
                updateRequest(uri, paramScope, uriMap);
                return action;
            }
        }
        
        return null;
    }
    
    private void updateHandler(Action action, ConfigurableInterceptorHandler handler){
        ConfigurableApplicationContext context =
            (ConfigurableApplicationContext)handler.getContext();

        ActionResolver actionResolver =
                context.getActionResolver();

        ResourceAction resourceAction = 
                actionResolver.getResourceAction(action);

        handler.setResourceAction(resourceAction);
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