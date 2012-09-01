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
import java.util.Map;
import javax.servlet.*;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import org.brandao.brutos.BrutosConstants;
import org.brandao.brutos.scope.ThreadScope;
import org.brandao.brutos.web.http.StaticBrutosRequest;

/**
 * 
 * @author Afonso Brandao
 */
public class ContextLoaderListener implements ServletContextListener,
        HttpSessionListener, ServletRequestListener{
    
    private ContextLoader contextLoader;
    
    public ContextLoaderListener() {
        contextLoader = new ContextLoader();
    }

    public void contextInitialized(ServletContextEvent sce) {
        contextLoader.init(sce.getServletContext());
    }

    public void contextDestroyed(ServletContextEvent sce) {
        contextLoader.destroy(sce.getServletContext());
    }

    public void sessionCreated(HttpSessionEvent se) {

        Map mappedUploadStats = new HashMap();

        se.getSession()
            .setAttribute(
                BrutosConstants.SESSION_UPLOAD_STATS,
                mappedUploadStats );
    }

    public void sessionDestroyed(HttpSessionEvent se) {
        se.getSession()
            .removeAttribute(
                BrutosConstants.SESSION_UPLOAD_STATS );
    }

    public void requestDestroyed(ServletRequestEvent sre) {
        ThreadScope.destroy();
        RequestInfo.removeCurrent();
        ConfigurableWebApplicationContext context =
            (ConfigurableWebApplicationContext)ContextLoader
                .getCurrentWebApplicationContext();
        context.getRequestFactory().destroyRequest();
        context.getResponseFactory().destroyResponse();


    }

    public void requestInitialized(ServletRequestEvent sre) {
        
        StaticBrutosRequest staticRequest;
        
        ThreadScope.create();
        
        ServletRequest request = sre.getServletRequest();

        staticRequest = new StaticBrutosRequest(request);
        
        RequestInfo requestInfo = new RequestInfo();
        requestInfo.setRequest(staticRequest);
        RequestInfo.setCurrent(requestInfo);

    }
    
}
