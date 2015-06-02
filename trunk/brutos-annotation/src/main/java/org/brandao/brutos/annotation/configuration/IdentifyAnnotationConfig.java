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
import org.brandao.brutos.EnumerationType;
import org.brandao.brutos.*;
import org.brandao.brutos.ScopeType;
import org.brandao.brutos.annotation.*;
import org.brandao.brutos.annotation.bean.BeanPropertyAnnotation;

/**
 *
 * @author Brandao
 */
@Stereotype(target=Identify.class,executeAfter={Controller.class,Bean.class,Action.class})
public class IdentifyAnnotationConfig extends AbstractAnnotationConfig{

    public boolean isApplicable(Object source) {
        boolean applicable = source instanceof ActionParamEntry;
        
        applicable = applicable || 
                (source instanceof BeanPropertyAnnotation && 
                !((BeanPropertyAnnotation)source).isAnnotationPresent(Transient.class));
        
        applicable = applicable || source instanceof ConstructorArgEntry;
        
        return applicable;
    }

    public Object applyConfiguration(Object source, Object builder, 
            ComponentRegistry componentRegistry) {
    
        try{
            return applyConfiguration0(source, builder, componentRegistry);
        }
        catch(Exception e){
            
            String type = "source";
            String name = "it is a bug";
            
            if(source instanceof ActionParamEntry){
                type = "parameter";
                name = ((ActionParamEntry)source).getName();
            }
            else
            if(source instanceof BeanPropertyAnnotation){
                type = "property";
                name = ((BeanPropertyAnnotation)source).getName();
            }
            else
            if(source instanceof ConstructorArgEntry){
                type = "constructor arg";
                name = ((ConstructorArgEntry)source).getName();
            }
            
            throw 
                new BrutosException(
                        "can't identify " + type + ": " + name,
                        e );
        }
        
    }
    
    public Object applyConfiguration0(Object source, Object builder, 
            ComponentRegistry componentRegistry) {
        
        if(source instanceof ActionParamEntry)
            addIdentify((ActionParamEntry)source, (ParametersBuilder)builder, componentRegistry);
        else
        if(source instanceof BeanPropertyAnnotation)
            addIdentify((BeanPropertyAnnotation)source, builder, componentRegistry);
        else
        if(source instanceof ConstructorArgEntry)
            addIdentify((ConstructorArgEntry)source, (ConstructorBuilder)builder, componentRegistry);
            
        return source;
    }
    
    protected void addIdentify(ActionParamEntry source, ParametersBuilder builder,
            ComponentRegistry componentRegistry){

        ParameterBuilder paramBuilder;
        
        Identify identify = source.getAnnotation(Identify.class);
        
        if(!source.isAnnotationPresent(Any.class) && AnnotationUtil.isBuildEntity(componentRegistry, identify, source.getType()))
            paramBuilder = buildParameter(builder, source, componentRegistry);
        else
            paramBuilder = addParameter(source, builder, componentRegistry);
        
        super.applyInternalConfiguration(source, paramBuilder, componentRegistry);
        
    }
    
    protected void addIdentify(BeanPropertyAnnotation source, Object builder,
            ComponentRegistry componentRegistry){
        
        PropertyBuilder propertyBuilder;        

        Identify identify = source.getAnnotation(Identify.class);
        
        if(!source.isAnnotationPresent(Any.class) && AnnotationUtil.isBuildEntity(componentRegistry, identify, source.getType()))
            propertyBuilder = buildProperty(builder, source, componentRegistry);
        else
            propertyBuilder = addProperty(source, builder, componentRegistry);
        
        super.applyInternalConfiguration(
                source, propertyBuilder, componentRegistry);
        
    }

    protected void addIdentify(ConstructorArgEntry source, ConstructorBuilder builder,
            ComponentRegistry componentRegistry){

        ConstructorArgBuilder constructorBuilder;
        
        Identify identify = source.getAnnotation(Identify.class);
        
        if(!source.isAnnotationPresent(Any.class) && AnnotationUtil.isBuildEntity(componentRegistry, identify, source.getType()))
            constructorBuilder = buildConstructorArg(builder, source, componentRegistry);
        else
            constructorBuilder = addConstructorArg(source, builder, componentRegistry);
        
        super.applyInternalConfiguration(source, constructorBuilder, componentRegistry);
        
    }
    
    protected ConstructorArgBuilder addConstructorArg(ConstructorArgEntry source, ConstructorBuilder builder,
            ComponentRegistry componentRegistry){
        
        Identify identify = source.getAnnotation(Identify.class);
        
        String name = source.getName() == null? source.getDefaultName() : source.getName();
        ScopeType scope = AnnotationUtil.getScope(identify);
        EnumerationType enumProperty = AnnotationUtil.getEnumerationType(source.getAnnotation(Enumerated.class));
        String temporalProperty = AnnotationUtil.getTemporalProperty(source.getAnnotation(Temporal.class));
        org.brandao.brutos.type.Type type = AnnotationUtil.getTypeInstance(source.getAnnotation(Type.class));
        
        Object classType;
        
        if(source.isAnnotationPresent(Any.class))
        	classType = Object.class;
        else{
	        Target target = source.getAnnotation(Target.class);
	        classType = target == null? source.getGenericType() : target.value();
        }
        
        //String mapping = identify != null && identify.useMapping()? name : null;
                
        return builder.addContructorArg(name, enumProperty, temporalProperty, 
                /*mapping*/null, scope, null, false, type, classType);
        
    }
    
    protected ConstructorArgBuilder buildConstructorArg(ConstructorBuilder builder, 
            ConstructorArgEntry arg, 
            ComponentRegistry componentRegistry){
        
        super.applyInternalConfiguration(new BeanEntryConstructorArg(arg), builder, 
                componentRegistry);
        
        return builder.getConstructorArg(builder.getConstructorArgSize()-1);
    }
    
    protected PropertyBuilder addProperty(BeanPropertyAnnotation property,Object builder,
            ComponentRegistry componentRegistry){
        
        String propertyName = getPropertyName(property);
        String name = AnnotationUtil.getBeanName(property);
        ScopeType scope = AnnotationUtil.getScope(property.getAnnotation(Identify.class));
        EnumerationType enumProperty = AnnotationUtil.getEnumerationType(property.getAnnotation(Enumerated.class));
        String temporalProperty = AnnotationUtil.getTemporalProperty(property.getAnnotation(Temporal.class));
        org.brandao.brutos.type.Type type = AnnotationUtil.getTypeInstance(property.getAnnotation(Type.class));
        
        Object classType;
        
        if(property.isAnnotationPresent(Any.class))
        	classType = Object.class;
        else{
	        Target target = property.getAnnotation(Target.class);
	        classType = target == null? property.getGenericType() : target.value();
        }
        
        //String mapping = identify != null && identify.mapping()? name : null;

        PropertyBuilder propertyBuilder;
        if(builder instanceof BeanBuilder){
            propertyBuilder = addProperty((BeanBuilder)builder,property, propertyName,
                name, scope, enumProperty, temporalProperty, type,
                /*mapping*/null, classType, componentRegistry);
        }
        else{
            propertyBuilder = addProperty((ControllerBuilder)builder,property, propertyName,
                name, scope, enumProperty, temporalProperty, type,
                /*mapping*/null, classType, componentRegistry);
        }
        
        return propertyBuilder;
    }
    
    protected PropertyBuilder addProperty(BeanBuilder beanBuilder, 
        BeanPropertyAnnotation property, String propertyName,
        String name, ScopeType scope, EnumerationType enumProperty,
        String temporalProperty, org.brandao.brutos.type.Type type, 
        String mapping, Object classType, ComponentRegistry componentRegistry){
        
        PropertyBuilder builder = 
            beanBuilder.addProperty(name, propertyName, enumProperty, 
            temporalProperty, mapping, scope, null, false, classType, type);
        
        return builder;
    }

    protected PropertyBuilder addProperty(ControllerBuilder controllerBuilder, 
        BeanPropertyAnnotation property, String propertyName,
        String name, ScopeType scope, EnumerationType enumProperty,
        String temporalProperty, org.brandao.brutos.type.Type type,
        String mapping, Object classType, ComponentRegistry componentRegistry){
        
        PropertyBuilder builder = 
            controllerBuilder.addProperty(propertyName, name, scope, 
                enumProperty, temporalProperty, mapping, null, false, classType, type);
        
        return builder;
    }

     protected PropertyBuilder buildProperty(Object beanBuilder, 
            BeanPropertyAnnotation property, 
            ComponentRegistry componentRegistry){
         
        super.applyInternalConfiguration(new BeanEntryProperty(property), beanBuilder, 
                componentRegistry);

        return beanBuilder instanceof BeanBuilder? 
                ((BeanBuilder)beanBuilder).getProperty(property.getName()) :
                ((ControllerBuilder)beanBuilder).getProperty(property.getName());
    }
    
    protected ParameterBuilder buildParameter(ParametersBuilder builder, 
            final ActionParamEntry property, 
            ComponentRegistry componentRegistry){
        
        super.applyInternalConfiguration(new BeanActionParamEntry(property), builder, 
                componentRegistry);
        
        return builder.getParameter(builder.getParametersSize()-1);
    }
    
    protected ParameterBuilder addParameter(ActionParamEntry source, ParametersBuilder builder,
            ComponentRegistry componentRegistry){
        
        Identify identify = source.getAnnotation(Identify.class);
        
        String name = source.getName() == null? source.getDefaultName() : source.getName();
        ScopeType scope = AnnotationUtil.getScope(identify);
        EnumerationType enumProperty = AnnotationUtil.getEnumerationType(source.getAnnotation(Enumerated.class));
        String temporalProperty = AnnotationUtil.getTemporalProperty(source.getAnnotation(Temporal.class));
        org.brandao.brutos.type.Type type = AnnotationUtil.getTypeInstance(source.getAnnotation(Type.class));
        
        Object classType;
        
        if(source.isAnnotationPresent(Any.class))
        	classType = Object.class;
        else{
	        Target target = source.getAnnotation(Target.class);
	        classType = target == null? source.getGenericType() : target.value();
        }
        
        return builder.addParameter(name, scope, enumProperty, 
                temporalProperty, null, type, null, false, classType);
    }
    
    private String getPropertyName(BeanPropertyAnnotation param){
        return param.getName();
    }

	@SuppressWarnings("unchecked")
	public Class<? extends Annotation>[] getExecutionOrder(){
        return new Class[]{
          Bean.class,
          Restriction.class,
          Restrictions.class
        };
    }
    
}
