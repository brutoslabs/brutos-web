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

import java.util.Properties;
import org.brandao.brutos.io.Resource;
import org.brandao.brutos.io.ServletContextResource;


/**
 *
 * @author Brandao
 */
public class XMLWebApplicationContext
        extends AbstractXMLWebApplicationContext{

    private Resource[] resources;

    public XMLWebApplicationContext(){
    }

    public void configure( Properties prop ){

        String configContext = prop.getProperty(
            WebApplicationContext.contextConfigName,
            WebApplicationContext.defaultConfigContext);

        this.resources = new Resource[]{
            new ServletContextResource(this.getContext(),configContext) };
        
        super.configure( prop );
    }

    protected Resource[] getContextResources() {
        return this.resources;
    }

}
