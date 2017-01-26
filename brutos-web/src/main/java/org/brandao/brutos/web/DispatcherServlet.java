

package org.brandao.brutos.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.brandao.brutos.BrutosConstants;
import org.brandao.brutos.ConfigurableApplicationContext;


public class DispatcherServlet extends HttpServlet {
    
    private WebApplicationContext webApplicationContext;
    private WebInvoker invoker;

    public void init() throws ServletException{
        super.init();
        webApplicationContext = ContextLoader.getCurrentWebApplicationContext();

        if( webApplicationContext == null ){
            throw new IllegalStateException(
                    "Unable to initialize the servlet was not configured for the application context root - " +
                    "make sure you have defined in your web.xml ContextLoader!"
            );
        }
        else
            invoker = (WebInvoker)((ConfigurableApplicationContext)webApplicationContext).getInvoker();

        Throwable ex = (Throwable)getServletContext().getAttribute( BrutosConstants.EXCEPTION );

        if( ex != null )
            throw new ServletException( ex );

    }
    
    public void destroy(){
        super.destroy();
    }
    
    protected void processRequest(ServletRequest request, ServletResponse response)
    throws ServletException, IOException {
        invoker.invoker(request, response, null);
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
