/*
 * Brutos Web MVC http://www.brutosframework.com.br/
 * Copyright (C) 2009-2012 Afonso Brandao. (afonso.rbn@gmail.com)
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
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import org.brandao.brutos.*;
import org.brandao.brutos.ioc.IOCProvider;
import org.brandao.brutos.scope.Scope;
import org.brandao.brutos.view.ViewProvider;
import org.brandao.brutos.web.http.BrutosRequest;
import org.brandao.brutos.web.http.StaticBrutosRequest;
import org.brandao.brutos.web.http.UploadListener;

/**
 *
 * @author Brandao
 */
public class WebInvoker extends Invoker{
    
    public WebInvoker(){
        super();
    }
    
    public WebInvoker(ControllerResolver controllerResolver, IOCProvider iocProvider, 
            ControllerManager controllerManager, ActionResolver actionResolver, 
            ConfigurableApplicationContext applicationContext, ViewProvider viewProvider){
        super(controllerResolver, iocProvider, controllerManager, actionResolver, 
            applicationContext, viewProvider);
    }
    
    public void invoker(ServletRequest request, 
            ServletResponse response, FilterChain chain) throws IOException, ServletException{
        
        RequestInfo requestInfo           = RequestInfo.getCurrentRequestInfo();
        boolean isFirstCall               = requestInfo == null;
        ServletRequest oldRequest         = null;
        ServletResponse oldResponse       = null;
        StaticBrutosRequest staticRequest = null;
        
        ConfigurableWebApplicationContext context = 
                (ConfigurableWebApplicationContext)this.applicationContext;
        
        try{
            staticRequest = new StaticBrutosRequest(request);
            if(isFirstCall){
                requestInfo   = new RequestInfo();
                requestInfo.setRequest(staticRequest);
                requestInfo.setResponse(response);
                RequestInfo.setCurrent(requestInfo);
            }
            else{
                oldRequest  = requestInfo.getRequest();
                oldResponse = requestInfo.getResponse();
                requestInfo.setResponse(response);
                requestInfo.setRequest(staticRequest);
            }
            /*
            if(isFirstCall){
                staticRequest = new StaticBrutosRequest(request);
                requestInfo   = new RequestInfo();
                requestInfo.setRequest(staticRequest);
                requestInfo.setResponse(response);
                RequestInfo.setCurrent(requestInfo);
            }
            else{
                staticRequest = (StaticBrutosRequest) requestInfo.getRequest();
                oldRequest  = staticRequest.getRequest();
                oldResponse = requestInfo.getResponse();
                requestInfo.setResponse(response);
                staticRequest.setRequest(request);
            }
            */
            this.invoke0(staticRequest, request, response, chain);
        }
        finally{
            if(isFirstCall){
                RequestInfo.removeCurrent();
                context.getRequestFactory().destroyRequest();
                context.getResponseFactory().destroyResponse();
            }
            else{
                requestInfo.setResponse(oldResponse);
                requestInfo.setRequest(oldRequest);
            }
        }
       
    }

    protected void invoke0(BrutosRequest brutosRequest, ServletRequest request,
            ServletResponse response, FilterChain chain) 
                throws IOException, ServletException{
        
        Map mappedUploadStats = null;
        String requestId      = brutosRequest.getRequestId();
        
        try{
            Scope scope             = this.applicationContext.getScopes().get(WebScopeType.SESSION);
            mappedUploadStats       = (Map) scope.get( BrutosConstants.SESSION_UPLOAD_STATS );
            UploadListener listener = brutosRequest.getUploadListener();

            if(mappedUploadStats != null)
                mappedUploadStats.put( requestId, listener.getUploadStats() );
            
            FileUploadException fue = null;
            
            try{
                brutosRequest.parseRequest();
            }
            catch( FileUploadException e ){
                fue = e;
            }
            
            if(!invoke(requestId, fue)){
                if(chain == null)
                    ((HttpServletResponse)response).setStatus(HttpServletResponse.SC_NOT_FOUND);
                else
                    chain.doFilter(request, response);
            }
        }
        finally{
            if(mappedUploadStats != null)
                mappedUploadStats.remove(requestId);
        }
    }
}
