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

import org.brandao.brutos.*;
import org.brandao.brutos.annotation.*;
import org.brandao.brutos.annotation.bean.BeanPropertyAnnotation;
import org.brandao.brutos.type.ObjectType;
import org.brandao.brutos.type.TypeManager;

/**
 *
 * @author Brandao
 */
@Stereotype(target=Property.class,executeAfter={Controller.class, Bean.class})
public class PropertyAnnotationConfig extends AbstractAnnotationConfig{

    public boolean isApplicable(Object source) {
        return source instanceof BeanPropertyAnnotation &&
                !((BeanPropertyAnnotation)source).isAnnotationPresent(Transient.class);
    }

    public Object applyConfiguration(Object source, Object builder, 
            ConfigurableApplicationContext applicationContext) {
        
        BeanPropertyAnnotation property = (BeanPropertyAnnotation)source;
        
        BeanBuilder beanBuilder = builder instanceof BeanBuilder? 
                (BeanBuilder)builder :
                null;
        
        ControllerBuilder controllerBuilder = builder instanceof ControllerBuilder?
                (ControllerBuilder)builder :
                beanBuilder.getControllerBuilder();
        
        Property propertyAnnotation = (Property)property.getAnnotation(Property.class);
        
        String propertyName = getPropertyName(property);
        String name = getBeanName(property,propertyAnnotation);
        ScopeType scope = getScope(propertyAnnotation);
        EnumerationType enumProperty = getEnumerationType(property);
        String temporalProperty = getTemporalProperty(property);
        String mapping = getMappingName(propertyAnnotation,property,controllerBuilder,applicationContext);
        org.brandao.brutos.type.Type type = getType(property);
        
        PropertyBuilder propertyBuilder;
        
        if(beanBuilder != null ){
            propertyBuilder = beanBuilder
                .addProperty(
                    name, 
                    propertyName, 
                    enumProperty, 
                    temporalProperty, 
                    mapping, 
                    scope, 
                    null, 
                    true, 
                    type);
        }
        else{
            propertyBuilder = controllerBuilder
                .addProperty(
                    propertyName, 
                    name, 
                    scope, 
                    enumProperty, 
                    temporalProperty, 
                    mapping, 
                    null, 
                    true, 
                    type);
        }
        super.applyInternalConfiguration(source, propertyBuilder, applicationContext);
        
        return builder;
    }

    private org.brandao.brutos.type.Type getType(BeanPropertyAnnotation property){
        try{
            Type type = property.getAnnotation(Type.class);
            if(type != null){
                Class typeClass = type.value();
                return (org.brandao.brutos.type.Type)ClassUtil.getInstance(typeClass);
            }
            else
                return null;
            
            
        }
        catch(Exception e){
            throw new BrutosException(e);
        }
    }
    
    private String getMappingName(Property propertyAnnotation,
            BeanPropertyAnnotation property,
            ControllerBuilder controllerBuilder,
            ConfigurableApplicationContext applicationContext){
        
        if(propertyAnnotation != null && !"".equals(propertyAnnotation.bean()) 
                && propertyAnnotation.mapping())
            return propertyAnnotation.bean();
        else{
            Type type = (Type) TypeManager.getType(property.getType());
            if(type instanceof ObjectType){
                super.applyInternalConfiguration(
                        property.getType(), controllerBuilder, applicationContext);
                return property.getType().getSimpleName().toLowerCase();
            }
            else
                return null;
        }
    }
    
    private String getTemporalProperty(BeanPropertyAnnotation property){
        if(property.isAnnotationPresent(Temporal.class))
            return property.getAnnotation(Temporal.class).value();
        else
            return BrutosConstants.DEFAULT_TEMPORALPROPERTY;
    }
    private EnumerationType getEnumerationType(BeanPropertyAnnotation property){
        if(property.isAnnotationPresent(Enumerated.class))
            return EnumerationType.valueOf(property.getAnnotation(Enumerated.class).value());
        else
            return BrutosConstants.DEFAULT_ENUMERATIONTYPE;
    }
    
    private ScopeType getScope(Property propertyAnnotation){
        if(propertyAnnotation != null && !"".equals(propertyAnnotation.scope()))
            return ScopeType.valueOf(propertyAnnotation.scope());
        else
            return BrutosConstants.DEFAULT_SCOPETYPE;
    }
    
    private String getBeanName(BeanPropertyAnnotation property, Property propertyAnnotation){
        
        if(propertyAnnotation != null || !"".equals(propertyAnnotation.bean()) )
            return propertyAnnotation.bean();
        else
            return property.getName();
    }
    
    private String getPropertyName(BeanPropertyAnnotation param){
        return param.getName();
    }
}
