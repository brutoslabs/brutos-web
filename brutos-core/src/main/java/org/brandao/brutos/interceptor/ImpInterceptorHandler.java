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

package org.brandao.brutos.interceptor;

import org.brandao.brutos.ResourceMethod;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.brandao.brutos.ResourceAction;

/**
 *
 * @author Afonso Brandao
 */
public class ImpInterceptorHandler implements InterceptorHandler{
    
    private ServletContext context;
    
    private HttpServletRequest request;
    
    private HttpServletResponse response;
    
    private String URI;

    private String requestId;

    private ResourceAction resourceAction;

    
    private Object resource;
    
    public ImpInterceptorHandler() {
    }

    public ServletContext getContext() {
        return context;
    }

    public void setContext(ServletContext context) {
        this.context = context;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    public String getURI() {
        return URI;
    }

    public void setURI(String URI) {
        this.URI = URI;
        this.setRequestId(URI);
    }

    public ResourceAction getResourceAction() {
        return resourceAction;
    }

    public void setResourceAction(ResourceAction resourceAction) {
        this.resourceAction = resourceAction;
    }

    public Object getResource() {
        return resource;
    }

    public void setResource(Object resource) {
        this.resource = resource;
    }

    public HttpServletRequest getRequest() {
        return this.request;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String requestId() {
        return this.requestId;
    }
    
}
