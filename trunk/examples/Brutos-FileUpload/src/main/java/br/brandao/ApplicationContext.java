package br.brandao;

import javax.servlet.ServletContextEvent;
import org.brandao.brutos.ConfigurableApplicationContext;
import org.brandao.brutos.web.ContextLoader;
import org.brandao.brutos.web.ContextLoaderListener;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Neto
 */
public class ApplicationContext extends ContextLoaderListener{

    public void contextInitialized(ServletContextEvent sce) {
        super.contextInitialized(sce);
        
        ConfigurableApplicationContext context =
                (ConfigurableApplicationContext)
                ContextLoader.getCurrentWebApplicationContext();

        context.getConfiguration()
            .setProperty(
                "web.root" ,
                sce.getServletContext().getRealPath( "/" ) );
    }

    public void contextDestroyed(ServletContextEvent sce) {
        
    }

}
