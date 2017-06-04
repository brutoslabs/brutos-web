/*
 * Brutos Web MVC http://www.brutosframework.com.br/
 * Copyright (C) 2009-2017 Afonso Brandao. (afonso.rbn@gmail.com)
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
