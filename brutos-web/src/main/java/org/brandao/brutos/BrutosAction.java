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

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.brandao.brutos.web.RequestInfo;

/**
 * @deprecated 
 * @author Afonso Brandao
 */
public class BrutosAction extends HttpServlet {
    
    private BrutosContext brutosCore;
    private Invoker invoker;

    @Override
    public void init() throws ServletException{
        super.init();
        brutosCore = (BrutosContext) getServletContext().getAttribute( BrutosConstants.ROOT_APPLICATION_CONTEXT_ATTRIBUTE );

        if( brutosCore == null ){
            throw new IllegalStateException(
                    "Unable to initialize the servlet was not configured for the application context root - " +
                    "make sure you have defined in your web.xml ContextLoader!"
            );
        }
        else
            invoker = brutosCore.getInvoker();

        Throwable ex = (Throwable)getServletContext().getAttribute( BrutosConstants.EXCEPTION );

        if( ex != null )
            throw new ServletException( ex );

    }
    
    @Override
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
            brutosCore.getInvoker().invoke( (String)null, null );
        }
        finally{
            RequestInfo.removeCurrent();
        }

    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }
    
    @Override
    public String getServletInfo() {
        return "Brutos Servlet";
    }
}
