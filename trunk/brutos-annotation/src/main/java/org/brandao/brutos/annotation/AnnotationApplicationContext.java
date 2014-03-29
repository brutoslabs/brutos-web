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

import java.util.Arrays;
import java.util.List;
import org.brandao.brutos.AbstractApplicationContext;
import org.brandao.brutos.ApplicationContext;
import org.brandao.brutos.ComponentRegistry;

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
    
    private Class[] configClass;
    
    private ComponentConfigurer componentConfigurer;

    /**
     * Cria uma nova aplicação.
     */
    public AnnotationApplicationContext() {
        this(null, null);
    }
    
    /**
     * Cria uma nova aplicação.
     * @param clazz Componentes da aplicação.
     * @throws IllegalArgumentException Lançada se na lista conter classes de
     * configuração.
     */
    public AnnotationApplicationContext(Class[] clazz) throws IllegalArgumentException{
        this(clazz, null);
    }

    /**
     * Cria uma nova aplicação.
     * @param clazz Componentes da aplicação.
     * @param parent Aplicação.
     * @throws IllegalArgumentException Lançada se na lista conter classes de
     * configuração.
     */
    public AnnotationApplicationContext(Class[] clazz,
            ApplicationContext parent) throws IllegalArgumentException{
        super(parent);
        
        if(clazz != null)
            this.checkOnlyComponenetClass(clazz);
        
        this.allClazz = clazz;
        this.componentConfigurer = new ComponentConfigurer(this);
    }

    /**
     * Define as classes de configuração da aplicação.
     * @param clazz Classes de configuração.
     * @throws IllegalArgumentException Lançada se na lista conter classes que
     * não possuem configuração.
     * @throws IllegalStateException Lançada se as classes da aplicação já foram definidas.
     */
    public void setConfigClass(List<Class> clazz) throws IllegalStateException, IllegalArgumentException{
        
        if(this.allClazz != null)
            throw new IllegalStateException("classes have been defined");
        else{
            this.configClass = clazz.toArray(new Class[]{});
            this.checkOnlyConfigurationClass(this.configClass);
        }
    }
    
    private void checkOnlyConfigurationClass(Class[] list){
        for(Class c: list){
            if(!c.isAnnotationPresent(Configuration.class))
                throw new IllegalArgumentException();
        }
    }
    
    private void checkOnlyComponenetClass(Class[] list){
        for(Class c: list){
            if(c.isAnnotationPresent(Configuration.class))
                throw new IllegalArgumentException();
        }
    }
    
    @Override
    protected void loadDefinitions(ComponentRegistry registry) {
        this.componentConfigurer
                .setComponentList(
                        allClazz == null? 
                            Arrays.asList(this.configClass) : 
                            Arrays.asList(this.allClazz));
        this.componentConfigurer.init(registry);
    }
    
}
