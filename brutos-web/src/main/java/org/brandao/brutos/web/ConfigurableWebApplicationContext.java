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
import org.brandao.brutos.ComponentRegistry;
import org.brandao.brutos.ConfigurableApplicationContext;
import org.brandao.brutos.DefinitionReader;
import org.brandao.brutos.io.Resource;

/**
 *
 * @author Brandao
 */
public interface ConfigurableWebApplicationContext 
        extends 
        WebApplicationContext, 
        ConfigurableApplicationContext,
        ComponentRegistry {

    public static final String defaultConfigContext = "WEB-INF/brutos-config.xml";

    public static final String  contextConfigName   = "contextConfig";
    
    void setServletContext( ServletContext servletContext );
    
    void loadDefinitions(DefinitionReader definitionReader);
    
    void setLocations(String[] locations);
    
    void setResources(Resource[] resources);

    String[] getLocations();
    
    Resource[] getResources();
    
}
