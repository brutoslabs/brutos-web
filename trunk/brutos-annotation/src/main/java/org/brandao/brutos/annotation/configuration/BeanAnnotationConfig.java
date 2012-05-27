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

import java.util.List;
import org.brandao.brutos.BeanBuilder;
import org.brandao.brutos.ConfigurableApplicationContext;
import org.brandao.brutos.ControllerBuilder;
import org.brandao.brutos.annotation.Bean;
import org.brandao.brutos.annotation.Stereotype;
import org.brandao.brutos.annotation.bean.BeanPropertyAnnotationImp;
import org.brandao.brutos.bean.BeanInstance;
import org.brandao.brutos.bean.BeanProperty;

/**
 *
 * @author Brandao
 */
@Stereotype(target=Bean.class)
public class BeanAnnotationConfig extends AbstractAnnotationConfig{

    public boolean isApplicable(Object source) {
        return source instanceof Class;
    }

    public Object applyConfiguration(
            Object source, Object builder, 
            ConfigurableApplicationContext applicationContext) {

        Class clazz = (Class)source;
        ControllerBuilder controllerBuilder = (ControllerBuilder)builder;
        
        Bean beanAnnotation = (Bean) clazz.getAnnotation(Bean.class);
        
        String name = beanAnnotation == null || "".equals(beanAnnotation.name())?
                clazz.getSimpleName().toLowerCase() :
                beanAnnotation.name();
        
        BeanBuilder beanBuilder = controllerBuilder.buildMappingBean(name, clazz);
        
        addProperties(beanBuilder, applicationContext, clazz);
        
        return builder;
    }
 
    protected void addProperties(BeanBuilder beanBuilder, 
            ConfigurableApplicationContext applicationContext, Class clazz){
    
        BeanInstance instance = new BeanInstance(null,clazz);
        List props = instance.getProperties();
        for(int i=0;i<props.size();i++){
            BeanProperty prop = (BeanProperty) props.get(i);
            super.applyInternalConfiguration(new BeanPropertyAnnotationImp(prop), 
                    beanBuilder, applicationContext);
        }
    }
    
}
