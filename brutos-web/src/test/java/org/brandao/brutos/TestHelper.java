/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.brandao.brutos;

import com.mockrunner.mock.web.MockHttpServletRequest;
import com.mockrunner.mock.web.MockHttpServletResponse;
import com.mockrunner.mock.web.MockHttpSession;
import com.mockrunner.mock.web.MockServletContext;
import java.util.Map;
import java.util.Properties;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletRequestEvent;
import javax.servlet.http.HttpSessionEvent;
import org.brandao.brutos.ioc.IOCProvider;
import org.brandao.ioc.IOCContainer;
import org.brandao.ioc.RootContainer;

/**
 *
 * @author Brandao
 */
public class TestHelper {

    public static void executeTest(ApplicationContext applicationContext,
            Map initParameters, String requestID, TestExecutor executor ){
        MockServletContext servletContext = new MockServletContext();
        ServletContextEvent sce = new ServletContextEvent( servletContext );
        
        org.brandao.brutos.web.ContextLoaderListener listener =
                new org.brandao.brutos.web.ContextLoaderListener();

        org.brandao.ioc.web.ContextLoaderListener iocContext =
                new org.brandao.ioc.web.ContextLoaderListener();

        org.brandao.ioc.web.RequestContextListener iocRequest =
                new org.brandao.ioc.web.RequestContextListener();

        initParameters.put("org.brandao.brutos.ioc.provider",DefaultIOCProvider.class.getName() );
        
        String[] keys = (String[]) initParameters.keySet().toArray(new String[]{});

        for( int i=0;i<keys.length;i++ ){
            servletContext
                        .setInitParameter(
                            keys[i], (String) initParameters.get(keys[i]));
        }

        try{
            //MockApplicationContext.setCurrentApplicationContext(applicationContext);
            iocContext.contextInitialized(sce);
            listener.contextInitialized(sce);
            MockHttpServletRequest request = new MockHttpServletRequest();
            MockHttpServletResponse response = new MockHttpServletResponse();
            MockHttpSession session = new MockHttpSession();
            ServletRequestEvent sre = new ServletRequestEvent(servletContext, request);

            request.setContextPath("");
            request.setRequestURI(requestID);
            try{
                iocRequest.requestInitialized(sre);
                listener.requestInitialized(sre);
                executor.execute(request, response, session, servletContext);
            }
            finally{
                iocRequest.requestDestroyed(sre);
                listener.requestDestroyed(sre);
            }

        }
        finally{
            iocContext.contextDestroyed(sce);
            listener.contextDestroyed(sce);
        }
    }

    public static interface TestExecutor{

        public void execute( MockHttpServletRequest request, MockHttpServletResponse response,
                MockHttpSession session, MockServletContext context );
    }

    public static class DefaultIOCProvider extends IOCProvider{

        IOCContainer container = RootContainer.getInstance();


        public Object getBean(String name) {
            return container.getBean(name);
        }

        public void configure(Properties properties) {
        }

        public void configure(Configuration properties, ServletContextEvent sce) {
        }

        public void requestDestroyed(ServletRequestEvent sre) {
        }

        public void requestInitialized(ServletRequestEvent sre) {
        }

        public void sessionCreated(HttpSessionEvent se) {
        }

        public void sessionDestroyed(HttpSessionEvent se) {
        }

        public void destroy() {
        }

    }
}
