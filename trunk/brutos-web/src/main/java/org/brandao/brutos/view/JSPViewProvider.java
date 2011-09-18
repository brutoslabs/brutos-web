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

package org.brandao.brutos.view;

import java.io.IOException;
import java.util.Properties;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.DispatcherType;
import org.brandao.brutos.RequestInstrument;
import org.brandao.brutos.web.RequestInfo;

/**
 *
 * @author Afonso Brandao
 */
public class JSPViewProvider extends ViewProvider{
    
    public JSPViewProvider() {
    }

    public void configure(Properties properties) {
        //not used
    }

    @Deprecated
    public void show(String page, ServletRequest request, HttpServletResponse response, ServletContext context) throws ServletException, IOException {
        if( page != null )
            request.getRequestDispatcher( page ).include( request, response );
    }

    public void show( String page, boolean redirect, ServletRequest request,
            HttpServletResponse response, ServletContext context )
                throws ServletException, IOException{

        if( page != null ){
            if( redirect )
                response.sendRedirect(page);
            else
                request.getRequestDispatcher( page ).include( request, response );
        }
    }

    protected void show(RequestInstrument requestInstrument,
            String view, DispatcherType dispatcherType) throws IOException {
        try{
            show0(view, dispatcherType);
        }
        catch( BrutosException e ){
            throw e;
        }
        catch( ServletException e ){
            throw new BrutosException(e);
        }

    }
    public void show0(String view, DispatcherType dispatcherType) throws IOException, ServletException {

        if( view == null )
            return;
        
        RequestInfo requestInfo = RequestInfo.getCurrentRequestInfo();

        if( dispatcherType == DispatcherType.FORWARD ){
            requestInfo.getRequest().getRequestDispatcher( view )
                        .forward( requestInfo.getRequest(), requestInfo.getResponse() );
        }
        else
        if( dispatcherType == DispatcherType.INCLUDE ){
            requestInfo.getRequest().getRequestDispatcher( view )
                        .include( requestInfo.getRequest(), requestInfo.getResponse() );
        }
        else
        if( dispatcherType == DispatcherType.REDIRECT )
            ((HttpServletResponse)requestInfo.getResponse()).sendRedirect(view);
        else
            throw new BrutosException( "invalid dispatcher type: " + dispatcherType );
    }
}
