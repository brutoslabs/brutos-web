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

import org.brandao.brutos.ActionBuilder;
import org.brandao.brutos.ConfigurableApplicationContext;
import org.brandao.brutos.EnumerationType;
import org.brandao.brutos.ScopeType;
import org.brandao.brutos.annotation.ActionParam;

/**
 *
 * @author Brandao
 */
public class ActionParamAnnotationConfig extends AbstractAnnotationConfig{

    public boolean isApplicable(Object source) {
        return source instanceof ActionParamEntry;
    }

    public Object applyConfiguration(Object source, Object builder, 
            ConfigurableApplicationContext applicationContext) {
        
        ActionParamEntry param = (ActionParamEntry)source;
        ActionBuilder actionBuilder = (ActionBuilder)builder;
        
        ActionParam actionParam = (ActionParam)param.getAnnotation(ActionParam.class);
        
        
        //actionBuilder.addParameter(null, ScopeType.PARAM, EnumerationType.ORDINAL, null, null, null, builder, true, null)
                
        return actionBuilder;
    }
    
}
