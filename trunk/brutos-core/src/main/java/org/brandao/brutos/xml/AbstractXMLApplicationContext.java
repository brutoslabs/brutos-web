/*
 * Brutos Web MVC http://www.brutosframework.com.br/
 * Copyright (C) 2009-2012 Afonso Brandao. (afonso.rbn@gmail.com)
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

package org.brandao.brutos.xml;

import java.util.ArrayList;
import org.brandao.brutos.AbstractApplicationContext;
import org.brandao.brutos.io.Resource;
import java.util.Properties;
import org.brandao.brutos.DefinitionReader;
/**
 *
 * @author Brandao
 */
public abstract class AbstractXMLApplicationContext
        extends AbstractApplicationContext{

    public AbstractXMLApplicationContext( AbstractApplicationContext parent ){
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
                this,
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
