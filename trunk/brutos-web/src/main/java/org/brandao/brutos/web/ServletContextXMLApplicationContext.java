/*
 * Brutos Web MVC http://brutos.sourceforge.net/
 * Copyright (C) 2009 Afonso Brandao. (afonso.rbn@gmail.com)
 *
 * This library is free software. You can redistribute it
 * and/or modify it under the terms of the GNU General Public
 * License (GPL) version 3.0 or (at your option) any later
 * version.
 * You may obtain a copy of the License at
 *
 * http://www.gnu.org/licenses/gpl.html
 *
 * Distributed WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied.
 *
 */

package org.brandao.brutos.web;

import javax.servlet.ServletContext;
import org.brandao.brutos.*;
import org.brandao.brutos.io.Resource;
import org.brandao.brutos.web.io.ServletContextResource;
import org.brandao.brutos.AbstractXMLApplicationContext;

/**
 *
 * @author Brandao
 */
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
