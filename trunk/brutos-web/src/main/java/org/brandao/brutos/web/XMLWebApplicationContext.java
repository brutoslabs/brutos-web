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

import org.brandao.brutos.ComponentRegistry;
import org.brandao.brutos.xml.XMLComponentDefinitionReader;

/**
 *
 * @author Brandao
 */
public class XMLWebApplicationContext
        extends AbstractWebApplicationContext{

    @Override
    public void loadDefinitions(ComponentRegistry registry){
    
        XMLComponentDefinitionReader definitionReader = 
                new XMLComponentDefinitionReader(this);
        
        if( super.resources != null)
            definitionReader.loadDefinitions(super.resources);
        
        if( super.locations != null)
            definitionReader.loadDefinitions(super.locations);
    }

}
