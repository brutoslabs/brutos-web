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

package org.brandao.brutos.annotation;

import java.util.*;
import org.brandao.brutos.*;
import org.brandao.brutos.logger.Logger;
import org.brandao.brutos.logger.LoggerProvider;

/**
 * Classe que permite a configuração de uma aplicação usando 
 * anotações e "Convention over configuration".
 * <p>Para que a configuração seja carregada em uma 
 * aplicação web, é necessário a utilização das tags 
 * <b><code>&lt;context:annotation-config/&gt;</code></b> e 
 * <b><code>&lt;context:component-scan/&gt;</code></b> no 
 * arquivo de configuração brutos-config.xml</p>
 * 
 * @author Afonso Brandao
 */
public class AnnotationApplicationContext extends AbstractApplicationContext{
    
    private Class[] allClazz;
    private ComponentConfigurer componentConfigurer;

    /**
     * Cria uma nova aplicação.
     */
    public AnnotationApplicationContext() {
        this(new Class[]{}, null);
    }
    
    /**
     * Cria uma nova aplicação.
     * @param clazz Componentes da aplicação.
     */
    public AnnotationApplicationContext(Class[] clazz) {
        this(clazz, null);
    }

    /**
     * Cria uma nova aplicação.
     * @param clazz Componentes da aplicação.
     * @param parent Aplicação.
     */
    public AnnotationApplicationContext(Class[] clazz,
            ApplicationContext parent) {
        super(parent);
        this.allClazz = clazz;
        this.componentConfigurer = new ComponentConfigurer();
    }

    public void flush() {

        this.initLogger();
        
        this.init();
        
        this.initScopes();
        
        this.initControllers();
        
        this.componentConfigurer.init(this);
        
    }
    
}
