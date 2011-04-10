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

package org.brandao.brutos.xml;

import java.util.ArrayList;
import org.brandao.brutos.ApplicationContext;
import org.brandao.brutos.ConfigurableApplicationContextImp;
import org.brandao.brutos.io.Resource;
import java.util.Properties;
import org.brandao.brutos.DefinitionReader;
/**
 *
 * @author Brandao
 */
public abstract class AbstractXMLApplicationContext
        extends ApplicationContext{

    public AbstractXMLApplicationContext( ApplicationContext parent ){
        super(parent);
    }

    public AbstractXMLApplicationContext(){
        this( null );
    }

    public void configure( Properties config ){
        super.configure(config);
        load();
    }

    protected void load(){
        ControllerDefinitionReader cdr = 
            new ControllerDefinitionReader(
                new ConfigurableApplicationContextImp(this),
                new ArrayList(),
                this);

        loadControllersDefinitions(cdr);
    }

    protected void loadControllersDefinitions(
            DefinitionReader definitionReader ){

        Resource[] resources = getContextResources();
        
        if( resources != null)
            definitionReader.loadDefinitions(resources);
    }

    protected abstract Resource[] getContextResources();
    
}
