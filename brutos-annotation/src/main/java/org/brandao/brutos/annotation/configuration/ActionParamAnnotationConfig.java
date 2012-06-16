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
import org.brandao.brutos.annotation.ActionParam;
import org.brandao.brutos.annotation.Enumerated;
import org.brandao.brutos.annotation.Temporal;
import org.brandao.brutos.annotation.Type;
import org.brandao.brutos.mapping.StringUtil;
import org.brandao.brutos.type.TypeManager;

/**
 *
 * @author Brandao
 */
//@Stereotype(target=ActionParam.class,executeAfter=Action.class)
public class ActionParamAnnotationConfig extends AbstractAnnotationConfig{

    public boolean isApplicable(Object source) {
        return source instanceof ActionParamEntry;
    }

    public Object applyConfiguration(Object source, Object builder, 
            ConfigurableApplicationContext applicationContext) {
        
        ActionParamEntry param = (ActionParamEntry)source;
        ActionBuilder actionBuilder = (ActionBuilder)builder;
        
        ParameterBuilder paramBuilder;
        
        if(TypeManager.isStandardType(param.getType()))
            paramBuilder = buildParameter(actionBuilder,param,applicationContext);
        else
            paramBuilder = addParameter(param,actionBuilder,applicationContext);
        
        super.applyInternalConfiguration(source, paramBuilder, applicationContext);
        
        return actionBuilder;
    }

    protected ParameterBuilder buildParameter(ActionBuilder builder, 
            ActionParamEntry property, 
            ConfigurableApplicationContext applicationContext){
        
        
        super.applyInternalConfiguration(property, builder, 
                applicationContext);
        
        return builder.getParameter(builder.getParametersSize()-1);
    }
    
    protected ParameterBuilder addParameter(ActionParamEntry source,ActionBuilder builder,
            ConfigurableApplicationContext applicationContext){
        ActionParam actionParam = (ActionParam)source.getAnnotation(ActionParam.class);

        String name = source.getName();
        ScopeType scope = getScope(actionParam);
        EnumerationType enumProperty = getEnumerationType(source);
        String temporalProperty = getTemporalProperty(source);
        org.brandao.brutos.type.Type type = getType(source);

        return builder.addParameter(name, scope, enumProperty, 
                temporalProperty, null, type, null, false, source.getType());
        
    }
    
    private org.brandao.brutos.type.Type getType(ActionParamEntry param){
        try{
            Type type = param.getAnnotation(Type.class);
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
    
    private String getTemporalProperty(ActionParamEntry value){
        if(value.isAnnotationPresent(Temporal.class))
            return value.getAnnotation(Temporal.class).value();
        else
            return BrutosConstants.DEFAULT_TEMPORALPROPERTY;
    }
    private EnumerationType getEnumerationType(ActionParamEntry property){
        if(property.isAnnotationPresent(Enumerated.class))
            return EnumerationType.valueOf(property.getAnnotation(Enumerated.class).value());
        else
            return BrutosConstants.DEFAULT_ENUMERATIONTYPE;
    }
    
    private ScopeType getScope(ActionParam value){
        
        if(value != null){
            String scope = StringUtil.adjust(value.scope());
            if(!StringUtil.isEmpty(scope))
                return ScopeType.valueOf(value.scope());
        }
        
        return BrutosConstants.DEFAULT_SCOPETYPE;
    }
    
}
