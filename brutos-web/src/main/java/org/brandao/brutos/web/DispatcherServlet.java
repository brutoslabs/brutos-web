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

import java.io.IOException;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.brandao.brutos.AbstractApplicationContext;
import org.brandao.brutos.BrutosConstants;
import org.brandao.brutos.ConfigurableApplicationContext;
import org.brandao.brutos.Invoker;
import org.brandao.brutos.ScopeType;
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

        StaticBrutosRequest staticRequest;

        if( request instanceof StaticBrutosRequest )
            staticRequest = (StaticBrutosRequest)request;
        else
            staticRequest = new StaticBrutosRequest(request);

        WebApplicationContext app =
            ContextLoader.getCurrentWebApplicationContext();
                
        String requestId = staticRequest.getRequestId();
        Map mappedUploadStats = null;
        try{
            RequestInfo requestInfo = new RequestInfo();

            requestInfo.setRequest(staticRequest);
            requestInfo.setResponse(response);
            RequestInfo.setCurrent(requestInfo);

            Scope scope = app.getScopes().get(ScopeType.SESSION);

            mappedUploadStats =
                    (Map) scope.get( BrutosConstants.SESSION_UPLOAD_STATS );

            UploadListener listener = staticRequest.getUploadListener();


            mappedUploadStats.put( requestId, listener.getUploadStats() );

            invoker.invoke(requestId);
        }
        finally{
            mappedUploadStats.remove(requestId);
            ConfigurableApplicationContext capp =
                    (ConfigurableApplicationContext)app;

            capp.getRequestFactory().destroyRequest();
            capp.getResponseFactory().destroyResponse();

            RequestInfo.removeCurrent();
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
