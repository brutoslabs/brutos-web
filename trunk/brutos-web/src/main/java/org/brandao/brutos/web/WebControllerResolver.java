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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.brandao.brutos.*;
import javax.servlet.http.HttpServletRequest;
import org.brandao.brutos.interceptor.InterceptorHandler;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.old.programatic.WebFrameManager;
import org.brandao.brutos.ControllerManager;
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

    public Controller getController(ControllerManager controllerManager, InterceptorHandler handler) {
        String uri = handler.requestId();
        Map<String, Controller> forms = controllerManager.getForms();
        /*BrutosRequest request = (BrutosRequest) ContextLoaderListener
                                    .currentRequest.get();
        */
        Scope paramScope =
                handler.getContext().getScopes().get(WebScopeType.PARAM);
        for( Controller  form: forms.values() ){
            List<String> uriList = new ArrayList<String>();

            uriList.addAll( form.getAlias() );
            if( form.getId() != null )
                uriList.add( form.getId() );
            else
                uriList.addAll( form.getMethods().keySet() );

            for( String u: uriList ){
                
                URIMapping uriMap = getURIMapping( u );
                if( uriMap.matches(uri) ){

                    Map<String,String> params = uriMap.getParameters(uri);
                    for(String key: params.keySet() )
                        paramScope.put(key, params.get(key) );
                    return form;
                }
                
            }
            
        }

        /*
        for( String u: forms.keySet() ){
            URIMap uriMap = getURIMapping( u );
            if( uriMap.matches(uri) ){
                Map<String,String> params = uriMap.getParameters(uri);
                for(String key: params.keySet() )
                    request.setParameter(key, params.get(key) );
                return forms.get(u);
            }
        }
        */

        return null;
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