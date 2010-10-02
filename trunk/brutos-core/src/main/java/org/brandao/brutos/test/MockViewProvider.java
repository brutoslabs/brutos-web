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

package org.brandao.brutos.test;

import org.brandao.brutos.view.*;
import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.brandao.brutos.Configuration;

/**
 *
 * @author Afonso Brandao
 */
public class MockViewProvider extends ViewProvider{
    
    private String view;
    private boolean redirect;
    private ServletRequest request;
    private HttpServletResponse response;
    private ServletContext context;

    public MockViewProvider() {
    }

    public void configure(Configuration properties) {
    }

    public void show(String page, boolean redirect,
            ServletRequest request, HttpServletResponse response,
            ServletContext context) throws ServletException, IOException {

        this.context = context;
        this.redirect = redirect;
        this.request = request;
        this.response = response;
        this.view = page;
    }

    public void show(String page, ServletRequest request, HttpServletResponse response, ServletContext context) throws ServletException, IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public boolean isRedirect() {
        return redirect;
    }

    public void setRedirect(boolean redirect) {
        this.redirect = redirect;
    }

    public ServletRequest getRequest() {
        return request;
    }

    public void setRequest(ServletRequest request) {
        this.request = request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    public ServletContext getContext() {
        return context;
    }

    public void setContext(ServletContext context) {
        this.context = context;
    }

}
