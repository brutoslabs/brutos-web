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

import java.io.IOException;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.brandao.brutos.BrutosConstants;
import org.brandao.brutos.ConfigurableApplicationContext;
import org.brandao.brutos.Invoker;
import org.brandao.brutos.WebScopeType;
import org.brandao.brutos.scope.Scope;
import org.brandao.brutos.web.http.StaticBrutosRequest;
import org.brandao.brutos.web.http.UploadListener;

/**
 * 
 * @author Afonso Brandao
 */
public class DispatcherServlet extends HttpServlet {
    
    private WebApplicationContext webApplicationContext;
    private Invoker invoker;

    public void init() throws ServletException{
        super.init();
        webApplicationContext = ContextLoader.getCurrentWebApplicationContext();

        if( webApplicationContext == null ){
            throw new IllegalStateException(
                    "Unable to initialize the servlet was not configured for the application context root - " +
                    "make sure you have defined in your web.xml ContextLoader!"
            );
        }
        else
            invoker = ((ConfigurableApplicationContext)webApplicationContext).getInvoker();

        Throwable ex = (Throwable)getServletContext().getAttribute( BrutosConstants.EXCEPTION );

        if( ex != null )
            throw new ServletException( ex );

    }
    
    public void destroy(){
        super.destroy();
    }
    
    protected void processRequest(ServletRequest request, ServletResponse response)
    throws ServletException, IOException {

        RequestInfo requestInfo = RequestInfo.getCurrentRequestInfo();
        StaticBrutosRequest staticRequest = 
                (StaticBrutosRequest) requestInfo.getRequest();
        
        ConfigurableWebApplicationContext context =
            (ConfigurableWebApplicationContext)
                ContextLoader.getCurrentWebApplicationContext();
                
        String requestId = staticRequest.getRequestId();
        Map mappedUploadStats = null;
        try{
            requestInfo.setResponse(response);

            Scope scope = context.getScopes().get(WebScopeType.SESSION);

            mappedUploadStats =
                    (Map) scope.get( BrutosConstants.SESSION_UPLOAD_STATS );

            UploadListener listener = staticRequest.getUploadListener();

            mappedUploadStats.put( requestId, listener.getUploadStats() );
            
            FileUploadException fue = null;
            
            try{
                staticRequest.parseRequest();
            }
            catch( FileUploadException e ){
                fue = e;
            }
            
            invoker.invoke(requestId, fue);
        }
        finally{
            mappedUploadStats.remove(requestId);
        }
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }
    
    public String getServletInfo() {
        return "Brutos Servlet";
    }
}
