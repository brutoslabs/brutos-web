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

package org.brandao.brutos.ioc;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.brandao.brutos.BrutosException;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 *
 * @author Afonso Brandão
 */
public class SpringIOCProvider 
    extends IOCProvider implements ApplicationContextAware{

    private static final Map currentApplicationCopntext;
    
    static{
        currentApplicationCopntext = new HashMap();
    }
    
    public SpringIOCProvider(){
    }
    
    public void setApplicationContext(
            ApplicationContext applicationContext) throws BeansException {
        registerApplicationContext(applicationContext);
    }

    public static synchronized void registerApplicationContext(
            ApplicationContext applicationContext) throws BeansException {

        ClassLoader classLoader = 
                Thread.currentThread().getContextClassLoader();
        
        if( currentApplicationCopntext.containsKey(classLoader)){
            throw new IllegalStateException(
                    "Multiple application context definitions has been detected.");
        }
        else
            currentApplicationCopntext.put(classLoader, applicationContext);
    }
    
    
    protected ApplicationContext getApplicationContext(){
        ClassLoader classLoader = 
                Thread.currentThread().getContextClassLoader();
        
        ApplicationContext applicationContext 
                = (ApplicationContext) currentApplicationCopntext.get(classLoader);
    
        if(applicationContext == null)
            throw new BrutosException("application context not found!");
        else
            return applicationContext;
    }
    
    public Object getBean(String name) {
        ApplicationContext applicationContext = this.getApplicationContext();
        return applicationContext.containsBeanDefinition(name)? 
                applicationContext.getBean(name) : 
                null;
    }

    public Object getBean(Class clazz) {
        ApplicationContext applicationContext = this.getApplicationContext();
        return applicationContext.getBean(clazz);
    }

    public void configure(Properties properties) {
    }

    public void destroy() {
    }

}
