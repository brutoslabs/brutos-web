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
import java.util.Set;
import org.brandao.brutos.ActionBuilder;
import org.brandao.brutos.BeanBuilder;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.ClassUtil;
import org.brandao.brutos.ConfigurableApplicationContext;
import org.brandao.brutos.annotation.*;
import org.brandao.brutos.annotation.bean.BeanPropertyAnnotation;
import org.brandao.brutos.annotation.bean.BeanPropertyAnnotationImp;
import org.brandao.brutos.bean.BeanInstance;
import org.brandao.brutos.bean.BeanProperty;
import org.brandao.brutos.mapping.StringUtil;
import org.brandao.brutos.type.TypeManager;

/**
 *
 * @author Brandao
 */
@Stereotype(
    target=
        Bean.class, 
    executeAfter={
        Identify.class,
        KeyCollection.class,
        ElementCollection.class
    }
)
public class BeanAnnotationConfig extends AbstractAnnotationConfig{

    private String path;
    
    public BeanAnnotationConfig(){
        this.path = "";
    }
    
    public boolean isApplicable(Object source) {
        return source instanceof BeanEntry;
    }

    public Object applyConfiguration(
            Object source, Object builder, 
            ConfigurableApplicationContext applicationContext) {

        Class clazz = ((BeanEntry)source).getBeanType();
        
        if(requiredBeanAnnotation(clazz))
            throw new BrutosException("expected @Bean: " + clazz.getName() );    
        
        boolean isRoot = StringUtil.isEmpty(path);
        
        try{
            createBean(builder, source, applicationContext);
        }
        finally{
            if(isRoot)
                path = "";
        }
        
        return builder;
    }

    private boolean requiredBeanAnnotation(Class clazz){
        return clazz != Map.class && 
               !Collection.class.isAssignableFrom(clazz) &&
               !clazz.isAnnotationPresent(Bean.class);
    }
    
    private void checkCircularReference(Object builder, Object source){
        
        String node = null;
        
        if( source instanceof KeyEntry ){
            node = getNode(((BeanBuilder)builder).getClassType(),
                    ((KeyEntry)source).getName());
        }
        else
        if( source instanceof ElementEntry ){
            node = getNode(((BeanBuilder)builder).getClassType(),
                    ((ElementEntry)source).getName());
        }
        else
        if( source instanceof BeanEntryConstructorArg ){
            node = getNode(((BeanBuilder)builder).getClassType(),
                    ((BeanEntryConstructorArg)source).getName());
        }
        else
        if( source instanceof BeanPropertyAnnotation ){
            node = getNode(((BeanBuilder)builder).getClassType(),
                    ((BeanPropertyAnnotation)source).getName());
        }
        
        checkNode(node);
    }

    private void checkNode(String node){
        if(path.indexOf(node) != -1)
            throw new BrutosException("circular reference");
        else
            path += node;
    }
    
    private String getNode(Class clazz, String name){
        return "["+clazz.getName()+"]." + name;
    }

    protected void createBean(Object builder, 
            Object source, ConfigurableApplicationContext applicationContext){
        
        if(source instanceof ActionParamEntry)
            createBean((ActionBuilder)builder, (ActionParamEntry)source, applicationContext);
        else{
            checkCircularReference(builder,source);
            if(source instanceof BeanPropertyAnnotation){
                createBean(
                    (BeanBuilder)builder, (BeanPropertyAnnotation)source, applicationContext);
            }
            else
            if(source instanceof BeanEntryConstructorArg){
                createBean(
                    (BeanBuilder)builder, (BeanEntryConstructorArg)source, applicationContext);
            }
            else
            if(source instanceof KeyEntry){
                createBean(
                    (BeanBuilder)builder, (KeyEntry)source, applicationContext);
            }
            else
            if(source instanceof ElementEntry){
                createBean(
                    (BeanBuilder)builder, (ElementEntry)source, applicationContext);
            }
        }
        
    }
    
    protected void createBean(BeanBuilder builder, 
            KeyEntry source, ConfigurableApplicationContext applicationContext){
        
        Class classType = source.getTarget() == null? 
                ClassUtil.getInstantiableClass(source.getClassType()) : 
                source.getTarget();
        
        BeanBuilder beanBuilder = 
            builder.buildKey(source.getName(), classType);
        
        createBean(beanBuilder, applicationContext, 
                source.getGenericType(), null, null);
    }

    protected void createBean(BeanBuilder builder, 
            ElementEntry source, ConfigurableApplicationContext applicationContext){
        
        Class classType = source.getTarget() == null? 
                ClassUtil.getInstantiableClass(source.getClassType()) : 
                source.getTarget();
        
        BeanBuilder beanBuilder = 
            builder.buildElement(source.getName(), classType);
        
        createBean(beanBuilder, applicationContext, 
                source.getGenericType(), null, null);
    }
    
    protected void createBean(ActionBuilder builder, 
            ActionParamEntry actionParam, ConfigurableApplicationContext applicationContext){
        
        
        Target target = actionParam.getAnnotation(Target.class);
        Class classType = target == null? 
                ClassUtil.getInstantiableClass(actionParam.getType()) : 
                target.value();
        
        BeanBuilder beanBuilder = 
            builder.buildParameter(actionParam.getName(), classType);
        
        createBean(beanBuilder, applicationContext, 
                actionParam.getGenericType(), 
                actionParam.getAnnotation(KeyCollection.class),
                actionParam.getAnnotation(ElementCollection.class));
    }

    protected void createBean(BeanBuilder builder, 
            BeanPropertyAnnotation source, ConfigurableApplicationContext applicationContext){
        
        Target target = source.getAnnotation(Target.class);
        Class classType = target == null? 
                ClassUtil.getInstantiableClass(source.getType()) : 
                target.value();
        
        BeanBuilder beanBuilder = 
            builder.buildProperty(source.getName(), source.getName(), classType);
        
        createBean(beanBuilder, applicationContext, source.getGenericType(), 
                source.getAnnotation(KeyCollection.class),
                source.getAnnotation(ElementCollection.class));
    }

    protected void createBean(BeanBuilder builder, 
            BeanEntryConstructorArg source, ConfigurableApplicationContext applicationContext){
        
        Target target = source.getAnnotation(Target.class);
        Class classType = target == null? 
                ClassUtil.getInstantiableClass(source.getType()) : 
                target.value();
        
        BeanBuilder beanBuilder = 
            builder.buildConstructorArg(source.getName(), classType);
        
        createBean(beanBuilder, applicationContext, source.getGenericType(), 
                source.getAnnotation(KeyCollection.class),
                source.getAnnotation(ElementCollection.class));
    }

    protected void createBean(BeanBuilder beanBuilder, 
            ConfigurableApplicationContext applicationContext, 
            Object genericType, 
            KeyCollection keyCollection, ElementCollection elementCollection){
        
        Class type = TypeManager.getRawType(genericType);
        
        boolean userDefaultMapping = 
            type == Map.class || type == List.class || type == Set.class;
        
        type = ClassUtil.getInstantiableClass(type);
        
        if(!userDefaultMapping){
            addConstructor(beanBuilder, applicationContext, type);
            addProperties(beanBuilder, applicationContext, type);
        }
        
        if(keyCollection == null)
            keyCollection = (KeyCollection) type.getAnnotation(KeyCollection.class);

        if(elementCollection == null)
            elementCollection = (ElementCollection) type.getAnnotation(ElementCollection.class);
        
        setKey(beanBuilder, applicationContext, genericType, type, keyCollection);
        setElement(beanBuilder, applicationContext, genericType, type, elementCollection);
        
        
    }

    protected void setElement(BeanBuilder beanBuilder, 
            ConfigurableApplicationContext applicationContext, 
            Object genericType, Class type, 
            ElementCollection elementCollection){
        
        if(AnnotationUtil.isCollection(type)){
            Object elementType = AnnotationUtil.getCollectionType(genericType);

            ElementEntry elementEntry = 
                new ElementEntry(TypeManager.getRawType(elementType),(Type)elementType,elementCollection);

            super.applyInternalConfiguration(
                    elementEntry, 
                    beanBuilder, 
                    applicationContext);
        }
    }
    
    protected void setKey(BeanBuilder beanBuilder, 
            ConfigurableApplicationContext applicationContext, 
            Object genericType, Class type, 
            KeyCollection keyCollection){
        
        if(AnnotationUtil.isMap(type)){
            Object keyType = AnnotationUtil.getKeyType(genericType);

            KeyEntry keyEntry = 
                new KeyEntry(TypeManager.getRawType(keyType),(Type)keyType,keyCollection);

            super.applyInternalConfiguration(
                    keyEntry, 
                    beanBuilder, 
                    applicationContext);
        }
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
