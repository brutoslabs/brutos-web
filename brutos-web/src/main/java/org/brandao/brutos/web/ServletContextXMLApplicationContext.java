

package org.brandao.brutos.web;

import javax.servlet.ServletContext;
import org.brandao.brutos.*;
import org.brandao.brutos.io.Resource;
import org.brandao.brutos.web.io.ServletContextResource;
import org.brandao.brutos.AbstractXMLApplicationContext;


public class ServletContextXMLApplicationContext
        extends AbstractXMLApplicationContext{

    private Resource[] resources;

    private ServletContext servletContext;
    
    public ServletContextXMLApplicationContext( ServletContext servletContext,
            String[] locations, AbstractApplicationContext parent ){
        super( parent );

        this.resources = new Resource[locations.length];
        this.servletContext = servletContext;
        for( int i=0;i<locations.length;i++ )
            resources[i] = new ServletContextResource(
                    servletContext, locations[i] );
    }

    public ServletContextXMLApplicationContext( ServletContext servletContext,
            String[] locations ){
        this(servletContext,locations,null);
    }

    public ServletContextXMLApplicationContext( ServletContext servletContext,
            String location ){
        this(servletContext,new String[]{location}, null);
    }

    protected Resource getContextResource( String path ){
        return new ServletContextResource( servletContext,path );
    }

    protected Resource[] getContextResources() {
        return resources;
    }

}
