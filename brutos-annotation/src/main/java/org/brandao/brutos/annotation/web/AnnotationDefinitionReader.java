/*
 * Brutos Web MVC http://www.brutosframework.com.br/
 * Copyright (C) 2009 Afonso Brandao. (afonso.rbn@gmail.com)
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

package org.brandao.brutos.annotation.web;

import org.brandao.brutos.ComponentRegistry;
import org.brandao.brutos.ConfigurableApplicationContext;
import org.brandao.brutos.annotation.ComponentConfigurer;
import org.brandao.brutos.annotation.configuration.AnnotationUtil;
import org.brandao.brutos.xml.XMLComponentDefinitionReader;

/**
 *
 * @author Brandão
 */
public class AnnotationDefinitionReader 
    extends XMLComponentDefinitionReader {

    private ConfigurableApplicationContext applicationContext;
    
    public AnnotationDefinitionReader(ConfigurableApplicationContext applicationContext, 
            ComponentRegistry componentRegistry) {
        super(componentRegistry);
        this.applicationContext = applicationContext;
    }

    public void loadDefinitions(){
        ComponentConfigurer componentConfigurer = 
                new ComponentConfigurer(applicationContext);
        componentConfigurer.setConfiguration(AnnotationUtil.createDefaultConfiguration());
        componentConfigurer.init(this.componentRegistry);
    }
    

}
