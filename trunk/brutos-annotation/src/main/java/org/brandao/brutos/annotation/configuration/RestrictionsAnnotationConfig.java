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

import org.brandao.brutos.ConfigurableApplicationContext;
import org.brandao.brutos.ParameterBuilder;
import org.brandao.brutos.PropertyBuilder;
import org.brandao.brutos.annotation.*;
import org.brandao.brutos.annotation.bean.BeanPropertyAnnotation;
import org.brandao.brutos.validator.RestrictionRules;

/**
 *
 * @author Brandao
 */
@Stereotype(target=Restrictions.class,executeAfter={ActionParam.class,Property.class})
public class RestrictionsAnnotationConfig extends AbstractAnnotationConfig{

    public boolean isApplicable(Object source) {
        return (source instanceof ActionParamEntry &&
                ((ActionParamEntry)source).isAnnotationPresent(Restrictions.class)) ||
               (source instanceof BeanPropertyAnnotation &&
                ((BeanPropertyAnnotation)source).isAnnotationPresent(Restrictions.class));
    }

    public Object applyConfiguration(Object source, Object builder, 
            ConfigurableApplicationContext applicationContext) {
        
        ParameterBuilder parameterBuilder = 
            builder instanceof ParameterBuilder? 
                (ParameterBuilder)builder : 
                null;
        
        PropertyBuilder propertyBuilder = 
            builder instanceof PropertyBuilder? 
                (PropertyBuilder)builder : 
                null;
        
        Restrictions restrictions = this.getAnnotation(source);
        
        for(Restriction r: restrictions.value()){
            String rule = r.rule();
            String value = r.value();
            String message = r.message();

            if(parameterBuilder != null){
                parameterBuilder.addRestriction(RestrictionRules.valueOf(rule), value)
                    .setMessage(message);
            }
            else{
                propertyBuilder.addRestriction(RestrictionRules.valueOf(rule), value)
                    .setMessage(message);
            }
        }
        
        return builder;
    }

    private Restrictions getAnnotation(Object source){
        if(source instanceof ActionParamEntry)
            return ((ActionParamEntry)source).getAnnotation(Restrictions.class);
        else
            return ((BeanPropertyAnnotation)source).getAnnotation(Restrictions.class);
    }
    
}
