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
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.brandao.brutos.*;
import org.brandao.brutos.annotation.*;
import org.brandao.brutos.annotation.bean.BeanPropertyAnnotation;
import org.brandao.brutos.annotation.bean.BeanPropertyAnnotationImp;
import org.brandao.brutos.bean.BeanInstance;
import org.brandao.brutos.bean.BeanProperty;
import org.brandao.brutos.mapping.StringUtil;
import org.brandao.brutos.type.TypeUtil;

/**
 *
 * @author Brandao
 */
@Stereotype(
    target=
        Bean.class, 
    executeAfter={
        Controller.class,
        Identify.class,
        Any.class,
        KeyCollection.class,
        ElementCollection.class
    }
)
public class BeanAnnotationConfig extends AbstractAnnotationConfig{

    private String path;
    
    private int count;
    
    public BeanAnnotationConfig(){
        this.path = "";
        this.count = 1;
    }
    
    public boolean isApplicable(Object source) {
        return source instanceof BeanEntry;
    }

    public Object applyConfiguration(
            Object source, Object builder, 
            ComponentRegistry componentRegistry) {
    
        try{
            return applyConfiguration0(source, builder, componentRegistry);
        }
        catch(Exception e){
            throw 
                new BrutosException(
                        "can't create mapping: " + ((BeanEntry)source).getBeanType(),
                        e );
        }
    }
    
    public Object applyConfiguration0(
            Object source, Object builder, 
            ComponentRegistry componentRegistry) {

        //Class clazz = ((BeanEntry)source).getBeanType();
        
        //if(requiredBeanAnnotation(clazz))
        //    throw new BrutosException("expected @Bean");    
        
        boolean isRoot = StringUtil.isEmpty(path);
        
        try{
            createBean(builder, source, componentRegistry);
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
        
        try{
            Method getClassType = builder.getClass().getMethod("getClassType");
            Method getName = source.getClass().getMethod("getName");
            
            Class<?> classType = (Class<?>) getClassType.invoke(builder);
            String name = (String) getName.invoke(source);
            
            String node = getNode(classType,name);
            checkNode(node);
        }
        catch(BrutosException e){
            throw e;
        }
        catch(Exception e){
            throw new BrutosException(builder.getClass().getName(), e);
        }
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

    private Class getType(Class type){
        if(type.getSuperclass() != Object.class)
            return getType(type.getSuperclass());
        else
            return type;
    }
    
    protected void createBean(Object builder, 
            Object source, ComponentRegistry componentRegistry){
        
        if(builder instanceof ParametersBuilder)
            createBean((ParametersBuilder)builder, (BeanActionParamEntry)source, componentRegistry);
        else
        if(builder instanceof ControllerBuilder){
            if(source instanceof BeanPropertyAnnotation){
                createBean(
                    (ControllerBuilder)builder, (BeanEntryProperty)source, componentRegistry);
            }
            else
            if(source instanceof ImportBeanEntry){
                this.createBean(
                    (ControllerBuilder)builder, (ImportBeanEntry)source, componentRegistry);
            }
        }
        else{
            checkCircularReference(builder,source);
            if(source instanceof BeanPropertyAnnotation){
                createBean(
                    (BeanBuilder)builder, (BeanEntryProperty)source, componentRegistry);
            }
            else
            if(source instanceof BeanEntryConstructorArg){
                createBean(
                    (ConstructorBuilder)builder, (BeanEntryConstructorArg)source, componentRegistry);
            }
            else
            if(source instanceof KeyEntry){
                createBean(
                    (BeanBuilder)builder, (KeyEntry)source, componentRegistry);
            }
            else
            if(source instanceof ElementEntry){
                createBean(
                    (BeanBuilder)builder, (ElementEntry)source, componentRegistry);
            }
        }
    }
    
    protected BeanBuilder createBean(ControllerBuilder controllerBuilder, Class type){
    	
    	if(Map.class.isAssignableFrom(type) ||Collection.class.isAssignableFrom(type))
            return controllerBuilder.buildMappingBean(AnnotationUtil.getBeanName(type) + "#" + count++ , type);
    	else
    	if(controllerBuilder.getBean(AnnotationUtil.getBeanName(type)) == null)
            return controllerBuilder.buildMappingBean(AnnotationUtil.getBeanName(type), type);
        else
            return null;
    }
    
    protected void createBean(BeanBuilder builder, 
            KeyEntry source, ComponentRegistry componentRegistry){
        
        Class classType = source.getTarget() == null? 
                ClassUtil.getInstantiableClass(source.getClassType()) : 
                source.getTarget();
        
        BeanBuilder beanBuilder = this.createBean(builder.getControllerBuilder(), classType);
        builder.setMappedKey(source.getName(), beanBuilder != null? beanBuilder.getName() : AnnotationUtil.getBeanName(classType));
        
        //BeanBuilder beanBuilder = 
        //    builder.buildKey(source.getName(), classType);

        if(beanBuilder != null){
            createBean(beanBuilder, componentRegistry, 
                    source.getGenericType(), null, null, null, null);
        }
    }

    protected void createBean(BeanBuilder builder, 
            ElementEntry source, ComponentRegistry componentRegistry){
        
        Class classType = source.getTarget() == null? 
                ClassUtil.getInstantiableClass(source.getClassType()) : 
                source.getTarget();
        
        BeanBuilder beanBuilder = this.createBean(builder.getControllerBuilder(), classType);
        builder.setMappedElement(source.getName(), beanBuilder != null? beanBuilder.getName() : AnnotationUtil.getBeanName(classType));
        
        //BeanBuilder beanBuilder = 
        //    builder.buildElement(source.getName(), classType);
        
        if(beanBuilder != null){
            createBean(beanBuilder, componentRegistry, 
                    source.getGenericType(), null, null, null, null);
        }
        
    }
    
    protected void createBean(ParametersBuilder builder, 
            BeanActionParamEntry actionParam, ComponentRegistry componentRegistry){
        
        
        Target target = actionParam.getAnnotation(Target.class);
        Class classType = target == null? 
                ClassUtil.getInstantiableClass(actionParam.getType()) : 
                target.value();
        
        BeanBuilder beanBuilder = this.createBean(builder.getControllerBuilder(), classType);
        builder.addParameterMapping(
        		actionParam.getName(), 
        		beanBuilder != null? beanBuilder.getName() : AnnotationUtil.getBeanName(classType), classType);
        
        //BeanBuilder beanBuilder = 
        //    builder.buildParameter(actionParam.getName(), classType);
        
        if(beanBuilder != null){
            createBean(beanBuilder, componentRegistry, 
                    actionParam.getGenericType(), 
                    actionParam.getAnnotation(KeyCollection.class),
                    actionParam.getAnnotation(AnyKeyCollection.class),
                    actionParam.getAnnotation(ElementCollection.class),
                    actionParam.getAnnotation(AnyElementCollection.class));
        }
    }

    protected void createBean(ControllerBuilder builder, 
            BeanEntryProperty source, ComponentRegistry componentRegistry){
        
        Target target = source.getAnnotation(Target.class);
        Class classType = target == null? 
                ClassUtil.getInstantiableClass(source.getType()) : 
                target.value();
        
        BeanBuilder beanBuilder = this.createBean(builder, classType);
        builder.addPropertyMapping(
        		source.getName(),
        		AnnotationUtil.getBeanName(source),
        		beanBuilder != null? beanBuilder.getName() : AnnotationUtil.getBeanName(classType));
        
        //BeanBuilder beanBuilder = 
        //    builder.buildProperty(source.getName(), classType);
        
        if(beanBuilder != null){
            createBean(beanBuilder, componentRegistry, source.getGenericType(), 
                    source.getAnnotation(KeyCollection.class),
                    source.getAnnotation(AnyKeyCollection.class),
                    source.getAnnotation(ElementCollection.class),
            		source.getAnnotation(AnyElementCollection.class));
        }
    }
    
    protected void createBean(BeanBuilder builder, 
            BeanEntryProperty source, ComponentRegistry componentRegistry){
        
        Target target = source.getAnnotation(Target.class);
        Class classType = target == null? 
                ClassUtil.getInstantiableClass(source.getType()) : 
                target.value();
        
        BeanBuilder beanBuilder = this.createBean(builder.getControllerBuilder(), classType);
        builder.addMappedProperty(
        		AnnotationUtil.getBeanName(source), 
        		source.getName(), 
        		beanBuilder != null? beanBuilder.getName() : AnnotationUtil.getBeanName(classType));
        
        //BeanBuilder beanBuilder = 
        //    builder.buildProperty(source.getName(), classType);
        
        if(beanBuilder != null){
            createBean(beanBuilder, componentRegistry, source.getGenericType(), 
                    source.getAnnotation(KeyCollection.class),
                    source.getAnnotation(AnyKeyCollection.class),
                    source.getAnnotation(ElementCollection.class),
            		source.getAnnotation(AnyElementCollection.class));
        }
        
    }

    protected void createBean(ConstructorBuilder builder, 
            BeanEntryConstructorArg source, ComponentRegistry componentRegistry){
        
        Target target = source.getAnnotation(Target.class);
        Class classType = target == null? 
                ClassUtil.getInstantiableClass(source.getType()) : 
                target.value();
        
        BeanBuilder beanBuilder = this.createBean(builder.getBeanBuilder().getControllerBuilder(), classType);
        builder.addMappedContructorArg(
        		source.getName(), 
        		beanBuilder != null? beanBuilder.getName() : AnnotationUtil.getBeanName(classType));
        
        //BeanBuilder beanBuilder = 
        //    builder.buildConstructorArg(source.getName(), classType);
        
        if(beanBuilder != null){
            createBean(beanBuilder, componentRegistry, source.getGenericType(), 
                    source.getAnnotation(KeyCollection.class),
                    source.getAnnotation(AnyKeyCollection.class),
                    source.getAnnotation(ElementCollection.class),
            		source.getAnnotation(AnyElementCollection.class));
        }
    }

    protected void createBean(ControllerBuilder builder, 
            ImportBeanEntry source, ComponentRegistry componentRegistry){
        
            Class type  = source.getBeanType();
            Bean bean = (Bean)type.getAnnotation(Bean.class);
            String name = StringUtil.adjust(bean.value());
            name = 
                StringUtil.isEmpty(name)? 
                    StringUtil.getVariableFormat(type.getSimpleName()) : 
                    name;

            BeanBuilder beanBuilder = 
                    builder.buildMappingBean(name, type);
            
        createBean(beanBuilder, componentRegistry, 
                type, 
                (KeyCollection)type.getAnnotation(KeyCollection.class),
                (AnyKeyCollection)type.getAnnotation(AnyKeyCollection.class),
                (ElementCollection)type.getAnnotation(ElementCollection.class),
        		(AnyElementCollection)type.getAnnotation(AnyElementCollection.class));
            
        addfactories(beanBuilder, componentRegistry, type);
    }
    
    protected void createBean(BeanBuilder beanBuilder, 
            ComponentRegistry componentRegistry, 
            Object genericType, 
            KeyCollection keyCollection, 
            AnyKeyCollection anyKeyCollection, 
            ElementCollection elementCollection,
            AnyElementCollection anyElementCollection){
        
        Class type = TypeUtil.getRawType(genericType);
        
        boolean useDefaultMapping = AnnotationUtil.isUseDefaultMapping(type);
        
        type = ClassUtil.getInstantiableClass(type);
        
        if(!useDefaultMapping){
            addConstructor(beanBuilder, componentRegistry, type);
            addProperties(beanBuilder, componentRegistry, type);
            //addfactories(beanBuilder, applicationContext, type);
        }
        
        if(keyCollection == null)
            keyCollection = (KeyCollection) type.getAnnotation(KeyCollection.class);

        if(elementCollection == null)
            elementCollection = (ElementCollection) type.getAnnotation(ElementCollection.class);

        if(anyKeyCollection == null)
        	anyKeyCollection = (AnyKeyCollection) type.getAnnotation(AnyKeyCollection.class);

        if(anyElementCollection == null)
        	anyElementCollection = (AnyElementCollection) type.getAnnotation(AnyElementCollection.class);
        
        setKey(
        		beanBuilder, 
        		componentRegistry, 
        		genericType, 
        		type, 
        		keyCollection,
        		anyKeyCollection);
        
        setElement(
        		beanBuilder, 
        		componentRegistry, 
        		genericType, 
        		type, 
        		elementCollection,
        		anyElementCollection);
        
        
    }

    protected void setElement(BeanBuilder beanBuilder, 
            ComponentRegistry componentRegistry, 
            Object genericType, Class type, 
            ElementCollection elementCollection, 
            AnyElementCollection anyElementCollection){
        
        if(AnnotationUtil.isCollection(type)){
            Object elementType = AnnotationUtil.getCollectionType(genericType);

            ElementEntry elementEntry = 
                new ElementEntry(
                		TypeUtil.getRawType(elementType),
                		(Type)elementType,
                		elementCollection,
                		anyElementCollection == null? new Annotation[]{} : new Annotation[]{anyElementCollection});

            super.applyInternalConfiguration(
                    elementEntry, 
                    beanBuilder, 
                    componentRegistry);
        }
    }
    
    protected void setKey(BeanBuilder beanBuilder, 
            ComponentRegistry componentRegistry, 
            Object genericType, Class type, 
            KeyCollection keyCollection,
            AnyKeyCollection anyKeyCollection){
        
        if(AnnotationUtil.isMap(type)){
            Object keyType = AnnotationUtil.getKeyType(genericType);

            KeyEntry keyEntry = 
                new KeyEntry(
                		TypeUtil.getRawType(keyType),
                		(Type)keyType,
                		keyCollection,
                		anyKeyCollection == null? new Annotation[]{} : new Annotation[]{anyKeyCollection});

            super.applyInternalConfiguration(
                    keyEntry, 
                    beanBuilder, 
                    componentRegistry);
        }
    }
    
    protected void addConstructor(BeanBuilder beanBuilder, 
            ComponentRegistry componentRegistry, Class clazz){
        
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
            throw new BrutosException("can't determine the constructor of the bean: " + clazz.getName());
        
        Type[] genericTypes = (Type[]) constructor.getGenericParameterTypes();
        Class[] types = constructor.getParameterTypes();
        Annotation[][] annotations = constructor.getParameterAnnotations();
        
        ConstructorBuilder constructorBuilder = beanBuilder.buildConstructor();
        
        for(int i=0;i<genericTypes.length;i++){
            ConstructorArgEntry entry = 
                    new ConstructorArgEntry(null,types[i],genericTypes[i],annotations[i],i);
            super.applyInternalConfiguration(entry, constructorBuilder, componentRegistry);
        }
    }
    
    protected void addfactories(BeanBuilder factoryBuilder, 
            ComponentRegistry componentRegistry, Class clazz){
        
        Method[] methods = clazz.getDeclaredMethods();
        String factoryName = factoryBuilder.getName();
        
        for(Method method: methods){
            
            if(method.isAnnotationPresent(Bean.class)){
                Bean bean = method.getAnnotation(Bean.class);
                Type[] genericTypes = (Type[]) method.getGenericParameterTypes();
                Class[] types = method.getParameterTypes();
                Annotation[][] annotations = method.getParameterAnnotations();
                
                ControllerBuilder controllerBuilder = 
                        factoryBuilder.getControllerBuilder();
                
                Class type  = method.getReturnType();
                String name = StringUtil.adjust(bean.value());
                name = 
                    StringUtil.isEmpty(name)? 
                        StringUtil.getVariableFormat(type.getSimpleName()) : 
                        name;
                
                Target target = method.getAnnotation(Target.class);
                
                type = target != null? target.value() : type;
                
                BeanBuilder beanBuilder = 
                        controllerBuilder.buildMappingBean(name, type);
                
                beanBuilder.setFactory(factoryName);
                beanBuilder.setMethodfactory(method.getName());
                
                ConstructorBuilder constructorBuilder = 
                        beanBuilder.buildConstructor();
                for(int i=0;i<genericTypes.length;i++){
                    ConstructorArgEntry entry = 
                            new ConstructorArgEntry(null,types[i],genericTypes[i],annotations[i],i);
                    super.applyInternalConfiguration(entry, constructorBuilder, componentRegistry);
                }
            }
        }
        
    }
    
    protected void addProperties(BeanBuilder beanBuilder, 
            ComponentRegistry componentRegistry, Class clazz){
    
        BeanInstance instance = new BeanInstance(null,clazz);
        List props = instance.getProperties();
        for(int i=0;i<props.size();i++){
            BeanProperty prop = (BeanProperty) props.get(i);
            BeanPropertyAnnotationImp annotationProp = new BeanPropertyAnnotationImp(prop);
            //BeanEntryProperty beanEntry = new BeanEntryProperty(annotationProp);
            super.applyInternalConfiguration(annotationProp, 
                    beanBuilder, componentRegistry);
        }
    }
    
}
