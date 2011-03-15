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

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.brandao.brutos.BrutosConstants;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.RedirectException;
import org.brandao.brutos.ScopeType;
import org.brandao.brutos.mapping.Form;
import org.brandao.brutos.old.programatic.WebFrameManager;

/**
 *
 * @author Afonso Brandao
 */
public class WebFrame {
    
    private String defaultMethodParameterName = "invoke";
    
    private String defaultMethodName;
    
    private String page;
    
    private HttpServletRequest request;
    
    private HttpServletResponse response;
    
    private HttpSession session;
    
    private ServletContext servletContext;
    
    private boolean updatable;
    
    private String name;
    
    private String path;
    
    private String methodParameterName;
    
    private ScopeType scope;
    
    public WebFrame(){
    }
    
    public WebFrame( String page ) {
        this.setPage(page);
        this.setUpdatable(true);
    }

    protected void preAction(){
    }
    
    protected void postAction(){
    }
    
    public void redirect( Class controller ){
        if( controller == null )
            throw new NullPointerException();
        
        WebFrameManager wfm = (WebFrameManager)servletContext
                            .getAttribute( BrutosConstants.WEBFRAME_MANAGER );
        Form wf = wfm.getForm( controller );
        
        if( wf == null )
            throw new BrutosException( "invalid controller: " + controller.getName() );
        
        RedirectException re = new RedirectException();
        re.setPage( wf.getUri() );
        throw re;
    }

    public void redirect( String uri ){
        if( uri == null )
            throw new NullPointerException();

        RedirectException re = new RedirectException();
        re.setPage( uri );
        throw re;
    }

    public String getDefaultMethodParameterName() {
        return defaultMethodParameterName;
    }

    public String getDefaultMethodName() {
        return defaultMethodName;
    }

    public void setDefaultMethodName(String defaultMethodName) {
        this.defaultMethodName = defaultMethodName;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public HttpServletRequest getRequest() {
        return request;
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

    public HttpSession getSession() {
        return session;
    }

    public void setSession(HttpSession session) {
        this.session = session;
    }

    public ServletContext getServletContext() {
        return servletContext;
    }

    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public boolean isUpdatable() {
        return updatable;
    }

    public void setUpdatable(boolean updatable) {
        this.updatable = updatable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMethodParameterName() {
        return methodParameterName == null? this.defaultMethodParameterName : methodParameterName;
    }

    public void setMethodParameterName(String methodParameterName) {
        this.methodParameterName = methodParameterName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public ScopeType getScope() {
        return scope;
    }

    public void setScope(ScopeType scope) {
        this.scope = scope;
    }
}
