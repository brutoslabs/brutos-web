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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.brandao.brutos.ApplicationContext;
import org.brandao.brutos.BrutosConstants;
import org.brandao.brutos.ConfigurableApplicationContext;
import org.brandao.brutos.Invoker;
import org.brandao.brutos.ScopeType;
import org.brandao.brutos.scope.Scope;
import org.brandao.brutos.web.http.BrutosRequest;
import org.brandao.brutos.web.http.UploadListener;

/**
 * 
 * @author Afonso Brandao
 */
public class DispatcherServlet extends HttpServlet {
    
    private ApplicationContext webApplicationContext;
    private Invoker invoker;

    public void init() throws ServletException{
        super.init();
        webApplicationContext = (WebApplicationContext) getServletContext().getAttribute( BrutosConstants.ROOT_APPLICATION_CONTEXT_ATTRIBUTE );

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
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

        WebApplicationContext app =
            ContextLoader.getCurrentWebApplicationContext();
                

        Scope scope = app.getScopes().get(ScopeType.SESSION);
        
        Map mappedUploadStats =
                (Map) scope.get( BrutosConstants.SESSION_UPLOAD_STATS );

        String path         = request.getRequestURI();
        String contextPath  = request.getContextPath();
        path = path.substring( contextPath.length(), path.length() );

        try{
            RequestInfo requestInfo = new RequestInfo();

            BrutosRequest brutosRequest = (BrutosRequest)request;

            requestInfo.setRequest(request);
            requestInfo.setResponse(response);
            RequestInfo.setCurrent(requestInfo);

            UploadListener listener = brutosRequest.getUploadListener();


            mappedUploadStats.put( path, listener.getUploadStats() );

            invoker.invoke(path);
        }
        finally{
            mappedUploadStats.remove( path );
            ((ConfigurableApplicationContext)app).getRequestFactory().destroyRequest();
            ((ConfigurableApplicationContext)app).getResponseFactory().destroyResponse();
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
