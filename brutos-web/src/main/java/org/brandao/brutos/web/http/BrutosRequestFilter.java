/*
 * Brutos Web MVC http://www.brutosframework.com.br/
 * Copyright (C) 2009 Afonso Brandao. (afonso.rbn@gmail.com)
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


package org.brandao.brutos.web.http;

import java.io.IOException;
import java.util.Map;
import javax.servlet.*;
import org.brandao.brutos.BrutosConstants;
import org.brandao.brutos.BrutosContext;
import org.brandao.brutos.Invoker;
import org.brandao.brutos.WebScopeType;
import org.brandao.brutos.scope.Scope;
import org.brandao.brutos.web.ConfigurableWebApplicationContext;
import org.brandao.brutos.web.ContextLoader;
import org.brandao.brutos.web.FileUploadException;
import org.brandao.brutos.web.RequestInfo;

/**
 *
 * @author Afonso Brandao
 */
public class BrutosRequestFilter implements Filter{

    private FilterConfig filterConfig = null;
    private static ThreadLocal<FilterChain> currentFilter;

    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig   = filterConfig;
        this.currentFilter  = new ThreadLocal<FilterChain>();
    }

    public static FilterChain getCurrentFilterChain(){
        return currentFilter.get();
    }
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
            throws IOException, ServletException {
        
        if( filterConfig == null )
            return;

        RequestInfo requestInfo = RequestInfo.getCurrentRequestInfo();
        StaticBrutosRequest staticRequest =
                (StaticBrutosRequest) requestInfo.getRequest();
        
        ConfigurableWebApplicationContext context =
            (ConfigurableWebApplicationContext)
                ContextLoader.getCurrentWebApplicationContext();

        Invoker invoker =
                context.getInvoker();
        
        String requestId = staticRequest.getRequestId();
        Map mappedUploadStats = null;
        try{
            requestInfo.setResponse(response);

            Scope scope = context.getScopes().get(WebScopeType.SESSION);

            mappedUploadStats =
                    (Map) scope.get( BrutosConstants.SESSION_UPLOAD_STATS );
            
            UploadListener listener = staticRequest.getUploadListener();
            mappedUploadStats.put( requestId, listener.getUploadStats() );

            FileUploadException fue = null;
            try{
                staticRequest.parseRequest();
            }
            catch( FileUploadException e ){
                fue = e;
            }
            
            currentFilter.set(chain);
            if( context instanceof BrutosContext ){
                if( !invoker.invoke((String)null, fue) )
                    chain.doFilter( staticRequest, response);
            }
            else{
                if( !invoker.invoke( requestId, fue ) )
                    chain.doFilter( staticRequest, response);
            }
        }
        finally{
            mappedUploadStats.remove( requestId );
            currentFilter.remove();
        }
    }

    public void destroy() {
        this.filterConfig = null;
    }

}
