

package org.brandao.brutos.web;

import java.io.IOException;
import java.util.Map;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import org.brandao.brutos.*;
import org.brandao.brutos.scope.Scope;
import org.brandao.brutos.web.http.BrutosRequest;
import org.brandao.brutos.web.http.StaticBrutosRequest;
import org.brandao.brutos.web.http.UploadListener;


public class WebInvoker extends Invoker{
    
    public WebInvoker(){
        super();
    }
    
    public WebInvoker(ControllerResolver controllerResolver, ObjectFactory objectFactory, 
            ControllerManager controllerManager, ActionResolver actionResolver, 
            ConfigurableApplicationContext applicationContext, RenderView renderView){
        super(controllerResolver, objectFactory, controllerManager, actionResolver, 
            applicationContext, renderView);
    }
    
    public void invoker(ServletRequest request, 
            ServletResponse response, FilterChain chain) throws IOException, ServletException{
        
        RequestInfo requestInfo           = RequestInfo.getCurrentRequestInfo();
        boolean isFirstCall               = requestInfo == null;
        ServletRequest oldRequest         = null;
        ServletResponse oldResponse       = null;
        StaticBrutosRequest staticRequest = null;
        
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
            this.invoke0(staticRequest, request, response, chain);
        }
        finally{
            if(isFirstCall){
                RequestInfo.removeCurrent();
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
