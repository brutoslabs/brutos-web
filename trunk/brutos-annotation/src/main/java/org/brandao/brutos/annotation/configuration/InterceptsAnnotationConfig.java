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

package org.brandao.brutos.annotation.configuration;

import org.brandao.brutos.ConfigurableApplicationContext;
import org.brandao.brutos.InterceptorBuilder;
import org.brandao.brutos.InterceptorManager;
import org.brandao.brutos.annotation.Intercepts;
import org.brandao.brutos.annotation.Param;
import org.brandao.brutos.annotation.Stereotype;
import org.brandao.brutos.annotation.TypeDef;

/**
 *
 * @author Brandao
 */
@Stereotype(target=Intercepts.class,executeAfter=TypeDef.class)
public class InterceptsAnnotationConfig extends AbstractAnnotationConfig{

    public boolean isApplicable(Object source) {
        return source instanceof Class && 
               ((Class)source).isAnnotationPresent( Intercepts.class ) ||
               ((Class)source).getSimpleName().endsWith("InterceptorController");
    }

    public Object applyConfiguration(Object source, Object builder, 
            ConfigurableApplicationContext applicationContext) {
        
        Class clazz = (Class)source;
        InterceptorManager interceptorManager = 
                applicationContext.getInterceptorManager();
     
        Intercepts intercepts = (Intercepts)clazz.getAnnotation(Intercepts.class);
        
        String name = clazz.getSimpleName().replaceAll("InterceptorController$", "");
        boolean isDefault = true;
        Param[] params = null;
        
        if(intercepts != null){
            name = "".equals(intercepts.name())? name : intercepts.name();
            isDefault = intercepts.isDefault();
            params = intercepts.params();
        }
        
        InterceptorBuilder interceptorBuilder = 
                interceptorManager.addInterceptor(name, clazz, isDefault);
        
        if(params != null){
            for(Param p: params)
                interceptorBuilder.addParameter(p.name(), p.value());
        }
        
        return interceptorBuilder;
    }
    
}
