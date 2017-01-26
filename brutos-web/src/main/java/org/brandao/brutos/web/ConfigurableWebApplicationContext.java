

package org.brandao.brutos.web;

import javax.servlet.ServletContext;
import org.brandao.brutos.ComponentRegistry;
import org.brandao.brutos.ConfigurableApplicationContext;
import org.brandao.brutos.io.Resource;


public interface ConfigurableWebApplicationContext 
        extends 
        WebApplicationContext, 
        ConfigurableApplicationContext,
        ComponentRegistry {

    public static final String defaultConfigContext = "WEB-INF/brutos-config.xml";

    public static final String  contextConfigName   = "contextConfig";
    
    void setServletContext( ServletContext servletContext );
    
    void setLocations(String[] locations);
    
    void setResources(Resource[] resources);

    String[] getLocations();
    
    Resource[] getResources();
    
}
