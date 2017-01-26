


package org.brandao.brutos.web;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import org.brandao.brutos.BrutosConstants;


public class ContextLoaderListener implements ServletContextListener,
        HttpSessionListener, ServletRequestListener{
    
    private ContextLoader contextLoader;
    
    public ContextLoaderListener() {
        contextLoader = new ContextLoader();
    }

    public void contextInitialized(ServletContextEvent sce) {
        contextLoader.init(sce.getServletContext());
    }

    public void contextDestroyed(ServletContextEvent sce) {
        contextLoader.destroy(sce.getServletContext());
    }

    public void sessionCreated(HttpSessionEvent se) {

        Map mappedUploadStats = new HashMap();

        se.getSession()
            .setAttribute(
                BrutosConstants.SESSION_UPLOAD_STATS,
                mappedUploadStats );
    }

    public void sessionDestroyed(HttpSessionEvent se) {
        se.getSession()
            .removeAttribute(
                BrutosConstants.SESSION_UPLOAD_STATS );
    }

    public void requestDestroyed(ServletRequestEvent sre) {
        
    }

    public void requestInitialized(ServletRequestEvent sre) {
        
    }
    
}
