


package org.brandao.brutos.web.http;

import java.io.IOException;
import java.util.Map;
import javax.servlet.*;
import org.brandao.brutos.*;
import org.brandao.brutos.scope.Scope;
import org.brandao.brutos.web.*;


public class BrutosRequestFilter implements Filter{

    private FilterConfig filterConfig = null;
    private static ThreadLocal<FilterChain> currentFilter = new ThreadLocal<FilterChain>();
    private WebApplicationContext webApplicationContext;
    private WebInvoker invoker;

    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig   = filterConfig;
        
        webApplicationContext = ContextLoader.getCurrentWebApplicationContext();

        if( webApplicationContext == null ){
            throw new IllegalStateException(
                    "Unable to initialize the servlet was not configured for the application context root - " +
                    "make sure you have defined in your web.xml ContextLoader!"
            );
        }
        else
            invoker = (WebInvoker)((ConfigurableApplicationContext)webApplicationContext).getInvoker();

        Throwable ex = (Throwable)filterConfig.getServletContext().getAttribute( BrutosConstants.EXCEPTION );

        if( ex != null )
            throw new ServletException( ex );
        
    }

    public static FilterChain getCurrentFilterChain(){
        return currentFilter.get();
    }
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
            throws IOException, ServletException {
        
        try{
            if( filterConfig == null )
                return;
            currentFilter.set(chain);
            invoker.invoker(request, response, chain);
        }
        finally{
            currentFilter.remove();
        }
    }

    public void destroy() {
        this.filterConfig = null;
    }

}
