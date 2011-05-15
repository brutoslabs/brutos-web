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

import java.util.ArrayList;
import java.util.Properties;
import org.brandao.brutos.DefinitionReader;
import org.brandao.brutos.io.Resource;
import org.brandao.brutos.xml.ContextDefinitionReader;
import org.brandao.brutos.xml.ControllerDefinitionReader;

/**
 *
 * @author Brandao
 */
public abstract class AbstractXMLWebApplicationContext
        extends AbstractWebApplicationContext {

    public void configure( Properties config ){
        super.configure(config);
        load();
    }

    protected void load(){
        ContextDefinitionReader ccdr =
            new ContextDefinitionReader(
                this,
                new ArrayList(),
                this);

        loadDefinitions(ccdr);

        ControllerDefinitionReader cdr =
            new ControllerDefinitionReader(
                this,
                new ArrayList(),
                this);

        loadDefinitions(cdr);
    }

    protected void loadDefinitions(
            DefinitionReader definitionReader ){

        Resource[] resources = getContextResources();

        if( resources != null)
            definitionReader.loadDefinitions(resources);
    }

    protected abstract Resource[] getContextResources();

}
