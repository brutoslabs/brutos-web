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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.brandao.brutos.BrutosConstants;
import org.brandao.brutos.Invoker;

/**
 * 
 * @author Afonso Brandao
 */
public class DispatcherServlet extends HttpServlet {
    
    private WebApplicationContext webApplicationContext;
    private Invoker invoker;

    public void init() throws ServletException{
        super.init();
        webApplicationContext = (WebApplicationContext) getServletContext().getAttribute( BrutosConstants.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE );

        if( webApplicationContext == null ){
            throw new IllegalStateException(
                    "Unable to initialize the servlet was not configured for the application context root - " +
                    "make sure you have defined in your web.xml ContextLoader!"
            );
        }
        else
            invoker = webApplicationContext.getInvoker();

        Throwable ex = (Throwable)getServletContext().getAttribute( BrutosConstants.EXCEPTION );

        if( ex != null )
            throw new ServletException( ex );

    }
    
    public void destroy(){
        super.destroy();
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        try{
            RequestInfo requestInfo = new RequestInfo();
            requestInfo.setRequest( request );
            requestInfo.setResponse(response);
            RequestInfo.setCurrent(requestInfo);
            invoker.invoke(request.getRequestURI());
        }
        finally{
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
