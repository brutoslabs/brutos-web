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

import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.brandao.brutos.ActionBuilder;
import org.brandao.brutos.BeanBuilder;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.ConfigurableApplicationContext;
import org.brandao.brutos.annotation.Bean;
import org.brandao.brutos.annotation.Identify;
import org.brandao.brutos.annotation.Stereotype;
import org.brandao.brutos.annotation.bean.BeanPropertyAnnotation;
import org.brandao.brutos.annotation.bean.BeanPropertyAnnotationImp;
import org.brandao.brutos.bean.BeanInstance;
import org.brandao.brutos.bean.BeanProperty;
import org.brandao.brutos.mapping.StringUtil;

/**
 *
 * @author Brandao
 */
@Stereotype(target=Bean.class, executeAfter=Identify.class)
public class BeanAnnotationConfig extends AbstractAnnotationConfig{

    private String path;
    
    public BeanAnnotationConfig(){
        this.path = "";
    }
    
    public boolean isApplicable(Object source) {
        if(source instanceof BeanEntry){
            Class clazz = ((BeanEntry)source).getBeanType();
            
            if(!clazz.isAnnotationPresent(Bean.class))
                throw new BrutosException("expected @Bean: " + clazz.getName() );    
            
            return 
                !Map.class.isAssignableFrom(clazz) &&
                !Collection.class.isAssignableFrom(clazz);
        }
        else 
            return false;
    }

    public Object applyConfiguration(
            Object source, Object builder, 
            ConfigurableApplicationContext applicationContext) {

        boolean isRoot = StringUtil.isEmpty(path);
        
        
        if(builder instanceof ActionBuilder){
            String key =
                isRoot? ((ActionParamEntry)source).getType().getName().replace(".", "-") :
                ((ActionParamEntry)source).getName();
            
            if(path.indexOf(key) != -1)
                throw new BrutosException("circular reference");
            
            path += key;
            createBean((ActionBuilder)builder, (ActionParamEntry)source, applicationContext);
        }
        else
        if(builder instanceof BeanBuilder){
            
            String key =
                isRoot? ((BeanPropertyAnnotation)source).getType().getName().replace(".", "-") :
                ((BeanPropertyAnnotation)source).getName();
            
            if(path.indexOf(key) != -1)
                throw new BrutosException("circular reference");
            
            path += key;
            createBean((BeanBuilder)builder, (BeanPropertyAnnotation)source, applicationContext);
        }
        
        if(isRoot)
            path = "";
        
        return builder;
    }
 
    protected void createBean(ActionBuilder builder, 
            ActionParamEntry actionParam, ConfigurableApplicationContext applicationContext){
        
        BeanBuilder beanBuilder = 
            builder.buildParameter(actionParam.getName(), actionParam.getType());
        
        addProperties(beanBuilder, applicationContext, actionParam.getType());
    }

    protected void createBean(BeanBuilder builder, 
            BeanPropertyAnnotation source, ConfigurableApplicationContext applicationContext){
        
        BeanBuilder beanBuilder = 
            builder.buildProperty(source.getName(), source.getName(), source.getType());
        
        addProperties(beanBuilder, applicationContext, source.getType());
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
