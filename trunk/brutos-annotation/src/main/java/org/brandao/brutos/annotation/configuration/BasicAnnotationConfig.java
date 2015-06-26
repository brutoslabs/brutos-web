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
import org.brandao.brutos.type.TypeUtil;

/**
 *
 * @author Brandao
 */
@Stereotype(target=Basic.class,executeAfter={Controller.class,Bean.class,Action.class})
public class BasicAnnotationConfig extends AbstractAnnotationConfig{

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
        
        Basic basic = source.getAnnotation(Basic.class);
        
        if(!source.isAnnotationPresent(Any.class) && AnnotationUtil.isBuildEntity(componentRegistry, basic, source.getType()))
            paramBuilder = buildParameter(builder, source, componentRegistry);
        else
            paramBuilder = addParameter(source, builder, componentRegistry);
        
        super.applyInternalConfiguration(source, paramBuilder, componentRegistry);
        
    }
    
    protected void addIdentify(BeanPropertyAnnotation source, Object builder,
            ComponentRegistry componentRegistry){
        
        PropertyBuilder propertyBuilder;        

        Basic basic = source.getAnnotation(Basic.class);
        
        if(!source.isAnnotationPresent(Any.class) && AnnotationUtil.isBuildEntity(componentRegistry, basic, source.getType()))
            propertyBuilder = buildProperty(builder, source, componentRegistry);
        else
            propertyBuilder = addProperty(source, builder, componentRegistry);
        
        super.applyInternalConfiguration(
                source, propertyBuilder, componentRegistry);
        
    }

    protected void addIdentify(ConstructorArgEntry source, ConstructorBuilder builder,
            ComponentRegistry componentRegistry){

        ConstructorArgBuilder constructorBuilder;
        
        Basic basic = source.getAnnotation(Basic.class);
        
        if(!source.isAnnotationPresent(Any.class) && AnnotationUtil.isBuildEntity(componentRegistry, basic, source.getType()))
            constructorBuilder = buildConstructorArg(builder, source, componentRegistry);
        else
            constructorBuilder = addConstructorArg(source, builder, componentRegistry);
        
        super.applyInternalConfiguration(source, constructorBuilder, componentRegistry);
        
    }
    
    protected ConstructorArgBuilder addConstructorArg(ConstructorArgEntry source, ConstructorBuilder builder,
            ComponentRegistry componentRegistry){
        
        Basic basic = source.getAnnotation(Basic.class);
        
        String name = source.getName() == null? source.getDefaultName() : source.getName();
        ScopeType scope = AnnotationUtil.getScope(basic);
        EnumerationType enumProperty = AnnotationUtil.getEnumerationType(source.getAnnotation(Enumerated.class));
        String temporalProperty = AnnotationUtil.getTemporalProperty(source.getAnnotation(Temporal.class));
        org.brandao.brutos.type.Type type = AnnotationUtil.getTypeInstance(source.getAnnotation(Type.class));
        
        if(source.isAnnotationPresent(Any.class)){
            return builder.addGenericContructorArg(name, 
            		TypeUtil.getRawType(source.getGenericType()));
        }
        else{
            return builder.addContructorArg(name, enumProperty, temporalProperty, 
                    null, scope, null, false, type, source.getGenericType());
        }
        
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
        ScopeType scope = AnnotationUtil.getScope(property.getAnnotation(Basic.class));
        EnumerationType enumProperty = AnnotationUtil.getEnumerationType(property.getAnnotation(Enumerated.class));
        String temporalProperty = AnnotationUtil.getTemporalProperty(property.getAnnotation(Temporal.class));
        org.brandao.brutos.type.Type type = AnnotationUtil.getTypeInstance(property.getAnnotation(Type.class));
        
        
        if(property.isAnnotationPresent(Any.class)){
            if(builder instanceof BeanBuilder){
                return ((BeanBuilder)builder).addGenericProperty(name, propertyName, 
                		TypeUtil.getRawType(property.getGenericType()));
            }
            else{
                return ((ControllerBuilder)builder).addGenericProperty(propertyName, name, 
                		TypeUtil.getRawType(property.getGenericType()));
            }
        }
        else{
            if(builder instanceof BeanBuilder){
                return ((BeanBuilder)builder)
                		.addProperty( name, propertyName,
                	            enumProperty, temporalProperty, null, 
                	            scope, null, false, property.getGenericType(), type);
            }
            else{
                return ((ControllerBuilder)builder)
                		.addProperty(propertyName, name, 
                        scope, enumProperty, temporalProperty, 
                        null, null, false, property.getGenericType(), type);
            }
        }
        
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
        
        Basic basic = source.getAnnotation(Basic.class);
        
        String name = source.getName() == null? source.getDefaultName() : source.getName();
        ScopeType scope = AnnotationUtil.getScope(basic);
        EnumerationType enumProperty = AnnotationUtil.getEnumerationType(source.getAnnotation(Enumerated.class));
        String temporalProperty = AnnotationUtil.getTemporalProperty(source.getAnnotation(Temporal.class));
        org.brandao.brutos.type.Type type = AnnotationUtil.getTypeInstance(source.getAnnotation(Type.class));
        
        if(source.isAnnotationPresent(Any.class)){
            return builder.addGenericParameter(name, TypeUtil.getRawType(source.getGenericType()));
        }
        else{
            return builder.addParameter(name, scope, enumProperty, 
                    temporalProperty, null, type, null, false, source.getGenericType());
        }
    }
    
    private String getPropertyName(BeanPropertyAnnotation param){
        return param.getName();
    }

	@SuppressWarnings("unchecked")
	public Class<? extends Annotation>[] getExecutionOrder(){
        return new Class[]{
		  Any.class,
          Bean.class,
          Restriction.class,
          Restrictions.class
        };
    }
    
}