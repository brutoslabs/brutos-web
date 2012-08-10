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

import java.util.Map;
import org.brandao.brutos.EnumerationType;
import org.brandao.brutos.*;
import org.brandao.brutos.annotation.*;
import org.brandao.brutos.ScopeType;
import org.brandao.brutos.annotation.bean.BeanPropertyAnnotation;
import org.brandao.brutos.mapping.StringUtil;
import org.brandao.brutos.type.TypeManager;

/**
 *
 * @author Brandao
 */
@Stereotype(target=Identify.class,executeAfter={Controller.class, Bean.class,Action.class})
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
            ConfigurableApplicationContext applicationContext) {
        
        if(source instanceof ActionParamEntry)
            addIdentify((ActionParamEntry)source, (ActionBuilder)builder, applicationContext);
        else
        if(source instanceof BeanPropertyAnnotation)
            addIdentify((BeanPropertyAnnotation)source, builder, applicationContext);
        else
        if(source instanceof ConstructorArgEntry)
            addIdentify((ConstructorArgEntry)source, (BeanBuilder)builder, applicationContext);
            
        return source;
    }
    
    protected void addIdentify(ActionParamEntry source, ActionBuilder builder,
            ConfigurableApplicationContext applicationContext){

        ParameterBuilder paramBuilder;
        
        Identify identify = source.getAnnotation(Identify.class);
        boolean useMapping = 
            identify == null?
                true : 
                identify.useMapping();
        
        if(useMapping && !TypeManager.isStandardType(source.getType()))
            paramBuilder = buildParameter(builder,source,applicationContext);
        else
            paramBuilder = addParameter(source,builder,applicationContext);
        
        super.applyInternalConfiguration(source, paramBuilder, applicationContext);
        
    }
    
    protected void addIdentify(BeanPropertyAnnotation source, Object builder,
            ConfigurableApplicationContext applicationContext){
        
        PropertyBuilder propertyBuilder;        

        Identify identify = source.getAnnotation(Identify.class);
        boolean useMapping = 
            identify == null?
                true : 
                identify.useMapping();
        
        if(useMapping && !TypeManager.isStandardType(source.getType()))
            propertyBuilder = buildProperty((BeanBuilder)builder, source, applicationContext);
        else
            propertyBuilder = addProperty(source, builder, applicationContext);
        
        super.applyInternalConfiguration(
                source, propertyBuilder, applicationContext);
        
    }

    protected void addIdentify(ConstructorArgEntry source, BeanBuilder builder,
            ConfigurableApplicationContext applicationContext){

        ConstructorBuilder constructorBuilder;
        
        Identify identify = source.getAnnotation(Identify.class);
        boolean useMapping = 
            identify == null?
                true : 
                identify.useMapping();
        
        if(useMapping && !TypeManager.isStandardType(source.getType()))
            constructorBuilder = buildConstructorArg(builder,source,applicationContext);
        else
            constructorBuilder = addConstructorArg(source,builder,applicationContext);
        
        super.applyInternalConfiguration(source, constructorBuilder, applicationContext);
        
    }
    
    protected ConstructorBuilder addConstructorArg(ConstructorArgEntry source, BeanBuilder builder,
            ConfigurableApplicationContext applicationContext){
        
        Identify identify = source.getAnnotation(Identify.class);
        
        String name = source.getName();
        ScopeType scope = AnnotationUtil.getScope(identify);
        EnumerationType enumProperty = AnnotationUtil.getEnumerationType(source.getAnnotation(Enumerated.class));
        String temporalProperty = AnnotationUtil.getTemporalProperty(source.getAnnotation(Temporal.class));
        org.brandao.brutos.type.Type type = AnnotationUtil.getTypeInstance(source.getAnnotation(Type.class));
        //String mapping = identify != null && identify.useMapping()? name : null;
                
        return builder.addContructorArg(name, enumProperty, temporalProperty, 
                /*mapping*/null, scope, null, false, type, source.getType());
        
    }
    
    protected ConstructorBuilder buildConstructorArg(BeanBuilder builder, 
            ConstructorArgEntry arg, 
            ConfigurableApplicationContext applicationContext){
        
        super.applyInternalConfiguration(new BeanEntryConstructorArg(arg), builder, 
                applicationContext);
        
        return builder.getConstructorArg(builder.getConstructorArgSize()-1);
    }
    
    protected PropertyBuilder addProperty(BeanPropertyAnnotation property,Object builder,
            ConfigurableApplicationContext applicationContext){
        
        String propertyName = getPropertyName(property);
        String name = getBeanName(property);
        ScopeType scope = AnnotationUtil.getScope(property.getAnnotation(Identify.class));
        EnumerationType enumProperty = AnnotationUtil.getEnumerationType(property.getAnnotation(Enumerated.class));
        String temporalProperty = AnnotationUtil.getTemporalProperty(property.getAnnotation(Temporal.class));
        org.brandao.brutos.type.Type type = AnnotationUtil.getTypeInstance(property.getAnnotation(Type.class));
        //String mapping = identify != null && identify.mapping()? name : null;

        PropertyBuilder propertyBuilder;
        if(builder instanceof BeanBuilder){
            propertyBuilder = addProperty((BeanBuilder)builder,property, propertyName,
                name, scope, enumProperty, temporalProperty, type,
                /*mapping*/null, applicationContext);
        }
        else{
            propertyBuilder = addProperty((ControllerBuilder)builder,property, propertyName,
                name, scope, enumProperty, temporalProperty, type,
                /*mapping*/null, applicationContext);
        }
        
        return propertyBuilder;
    }
    
    protected PropertyBuilder addProperty(BeanBuilder beanBuilder, 
        BeanPropertyAnnotation property, String propertyName,
        String name, ScopeType scope, EnumerationType enumProperty,
        String temporalProperty, org.brandao.brutos.type.Type type, 
        String mapping, ConfigurableApplicationContext applicationContext){
        
        PropertyBuilder builder = 
            beanBuilder.addProperty(name, propertyName, enumProperty, 
            temporalProperty, mapping, scope, null, false, type);
        
        return builder;
    }

    protected PropertyBuilder addProperty(ControllerBuilder controllerBuilder, 
        BeanPropertyAnnotation property, String propertyName,
        String name, ScopeType scope, EnumerationType enumProperty,
        String temporalProperty, org.brandao.brutos.type.Type type,
        String mapping, ConfigurableApplicationContext applicationContext){
        
        PropertyBuilder builder = 
            controllerBuilder.addProperty(propertyName, name, scope, 
                enumProperty, temporalProperty, mapping, null, false, type);
        
        return builder;
    }

     protected PropertyBuilder buildProperty(BeanBuilder beanBuilder, 
            BeanPropertyAnnotation property, 
            ConfigurableApplicationContext applicationContext){
         
        super.applyInternalConfiguration(new BeanEntryProperty(property), beanBuilder, 
                applicationContext);

        return beanBuilder.getProperty(property.getName());
    }
    
    protected ParameterBuilder buildParameter(ActionBuilder builder, 
            final ActionParamEntry property, 
            ConfigurableApplicationContext applicationContext){
        
        super.applyInternalConfiguration(new BeanActionParamEntry(property), builder, 
                applicationContext);
        
        return builder.getParameter(builder.getParametersSize()-1);
    }
    
    protected ParameterBuilder addParameter(ActionParamEntry source,ActionBuilder builder,
            ConfigurableApplicationContext applicationContext){
        
        Identify identify = source.getAnnotation(Identify.class);
        
        String name = source.getName();
        ScopeType scope = AnnotationUtil.getScope(identify);
        EnumerationType enumProperty = AnnotationUtil.getEnumerationType(source.getAnnotation(Enumerated.class));
        String temporalProperty = AnnotationUtil.getTemporalProperty(source.getAnnotation(Temporal.class));
        org.brandao.brutos.type.Type type = AnnotationUtil.getTypeInstance(source.getAnnotation(Type.class));
        //String mapping = identify != null && identify.mapping()? name : null;
                
        return builder.addParameter(name, scope, enumProperty, 
                temporalProperty, /*mapping*/ null, type, null, false, source.getType());
        
    }
    
    private String getPropertyName(BeanPropertyAnnotation param){
        return param.getName();
    }

    private String getBeanName(BeanPropertyAnnotation property){
        Identify id = property.getAnnotation(Identify.class);
        if(id != null){
            String bean = StringUtil.adjust(id.bean());
            if(!StringUtil.isEmpty(bean))
                return id.bean();
        }
        
        return property.getName();
    }
    
}
