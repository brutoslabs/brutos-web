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
import org.brandao.brutos.type.Type;

/**
 *
 * @author Brandao
 */
@Stereotype(target=ActionParam.class,executeAfter=Action.class)
public class ActionParamAnnotationConfig extends AbstractAnnotationConfig{

    public boolean isApplicable(Object source) {
        return source instanceof ActionParamEntry;
    }

    public Object applyConfiguration(Object source, Object builder, 
            ConfigurableApplicationContext applicationContext) {
        
        ActionParamEntry param = (ActionParamEntry)source;
        ActionBuilder actionBuilder = (ActionBuilder)builder;
        
        ActionParam actionParam = (ActionParam)param.getAnnotation(ActionParam.class);
        
        String name = getName(param,actionParam);
        ScopeType scope = getScope(actionParam);
        EnumerationType enumProperty = getEnumerationType(param);
        String temporalProperty = getTemporalProperty(param);
        String mapping = getMappingName(actionParam);
        Type type = getType(actionParam);
        
        ParameterBuilder paramBuilder = 
                actionBuilder.addParameter(name, scope, enumProperty, 
                temporalProperty, mapping, type, null, false, param.getType());
                
        super.applyInternalConfiguration(source, paramBuilder, applicationContext);
        
        return actionBuilder;
    }

    private Type getType(ActionParam actionParam){
        try{
            if(actionParam == null || actionParam.factory() == Type.class)
                return null;
            else{
                Class typeClass = actionParam.factory();
                return (Type)typeClass.newInstance();
            }
            
            
        }
        catch(Exception e){
            throw new BrutosException(e);
        }
    }
    
    private String getMappingName(ActionParam actionParam){
        if(actionParam != null && !"".equals(actionParam.bean()) && actionParam.mapping())
            return actionParam.bean();
        else
            return null;
    }
    
    private String getTemporalProperty(ActionParamEntry param){
        if(param.isAnnotationPresent(Temporal.class))
            return param.getAnnotation(Temporal.class).value();
        else
            return BrutosConstants.DEFAULT_TEMPORALPROPERTY;
    }
    private EnumerationType getEnumerationType(ActionParamEntry param){
        if(param.isAnnotationPresent(Enumerated.class))
            return EnumerationType.valueOf(param.getAnnotation(Enumerated.class).value());
        else
            return BrutosConstants.DEFAULT_ENUMERATIONTYPE;
    }
    
    private ScopeType getScope(ActionParam actionParam){
        if(actionParam != null && !"".equals(actionParam.scope()))
            return ScopeType.valueOf(actionParam.scope());
        else
            return BrutosConstants.DEFAULT_SCOPETYPE;
    }
    
    private String getName(ActionParamEntry param,ActionParam actionParam){
        
        if(actionParam != null || !"".equals(actionParam.bean()) )
            return actionParam.bean();
        else
        if( param.getName() != null )
            return param.getName();
        else
            return "arg"+param.getIndex();
    }
}
