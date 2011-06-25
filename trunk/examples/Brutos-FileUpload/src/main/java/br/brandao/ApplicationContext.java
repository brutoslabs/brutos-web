package br.brandao;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.brandao.brutos.ConfigurableApplicationContext;
import org.brandao.brutos.web.ContextLoader;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Neto
 */
public class ApplicationContext implements ServletContextListener{

    public void contextInitialized(ServletContextEvent sce) {
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
