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

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Type;
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
        
        
        if(source instanceof ActionParamEntry)
            createBean((ActionBuilder)builder, (ActionParamEntry)source, applicationContext);
        else
        if(source instanceof BeanPropertyAnnotation){
            
            String node =
                getNode(((BeanBuilder)builder).getClassType(),((BeanPropertyAnnotation)source).getName());
            
            if(path.indexOf(node) != -1)
                throw new BrutosException("circular reference");
            
            path += node;
            createBean((BeanBuilder)builder, (BeanPropertyAnnotation)source, applicationContext);
        }
        else
        if(source instanceof BeanEntryConstructorArg){
            
            String node =
                getNode(((BeanBuilder)builder).getClassType(),((BeanEntryConstructorArg)source).getName());
            
            if(path.indexOf(node) != -1)
                throw new BrutosException("circular reference");
            
            path += node;
            createBean((BeanBuilder)builder, (BeanEntryConstructorArg)source, applicationContext);
        }
        
        if(isRoot)
            path = "";
        
        return builder;
    }
 
    private String getNode(Class clazz, String name){
        return "["+clazz.getName()+"]." + name;
    }
    protected void createBean(ActionBuilder builder, 
            ActionParamEntry actionParam, ConfigurableApplicationContext applicationContext){
        
        BeanBuilder beanBuilder = 
            builder.buildParameter(actionParam.getName(), actionParam.getType());
        
        createBean(beanBuilder, applicationContext, actionParam.getType());
    }

    protected void createBean(BeanBuilder builder, 
            BeanPropertyAnnotation source, ConfigurableApplicationContext applicationContext){
        
        BeanBuilder beanBuilder = 
            builder.buildProperty(source.getName(), source.getName(), source.getType());
        
        createBean(beanBuilder, applicationContext, source.getType());
    }

    protected void createBean(BeanBuilder builder, 
            BeanEntryConstructorArg source, ConfigurableApplicationContext applicationContext){
        
        BeanBuilder beanBuilder = 
            builder.buildConstructorArg(source.getName(), source.getType());
        
        createBean(beanBuilder, applicationContext, source.getType());
    }
    
    protected void createBean(BeanBuilder beanBuilder, 
            ConfigurableApplicationContext applicationContext, Class type){
        
        addConstructor(beanBuilder, applicationContext, type);
        addProperties(beanBuilder, applicationContext, type);
    }
    
    protected void addConstructor(BeanBuilder beanBuilder, 
            ConfigurableApplicationContext applicationContext, Class clazz){
        
        Constructor[] constructors = clazz.getDeclaredConstructors();
        
        Constructor constructor = null;
        
        if(constructors.length == 1)
            constructor = constructors[0];
        else{
            for(Constructor c: constructors){
                if(c.isAnnotationPresent(org.brandao.brutos.annotation.Constructor.class)){
                    if(constructor != null)
                        throw new BrutosException("expected @Constructor");
                    else
                        constructor = c;
                }
            }
        }
        
        if(constructor == null)
            throw new BrutosException("can't determine the constructor of the bean");
        
        Type[] genericTypes = (Type[]) constructor.getGenericParameterTypes();
        Class[] types = constructor.getParameterTypes();
        Annotation[][] annotations = constructor.getParameterAnnotations();
        
        for(int i=0;i<genericTypes.length;i++){
            ConstructorArgEntry entry = 
                    new ConstructorArgEntry(null,types[i],genericTypes[i],annotations[i],i);
            super.applyInternalConfiguration(entry, beanBuilder, applicationContext);
        }
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
