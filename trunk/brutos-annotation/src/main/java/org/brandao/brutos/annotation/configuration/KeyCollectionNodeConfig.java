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
import org.brandao.brutos.BeanBuilder;
import org.brandao.brutos.ConfigurableApplicationContext;
import org.brandao.brutos.annotation.Bean;
import org.brandao.brutos.annotation.Identify;
import org.brandao.brutos.annotation.KeyCollection;
import org.brandao.brutos.annotation.Stereotype;
import org.brandao.brutos.type.TypeManager;

/**
 *
 * @author Brandao
 */
@Stereotype(target=KeyCollection.class, executeAfter=Bean.class)
public class KeyCollectionNodeConfig extends AbstractAnnotationConfig{

    public boolean isApplicable(Object source) {
        if(source instanceof BeanEntry){
            Class clazz = ((BeanEntry)source).getBeanType();
            
            return Map.class.isAssignableFrom(clazz);
        }
        else 
            return false;
    }

    public Object applyConfiguration(Object source, Object builder, 
            ConfigurableApplicationContext applicationContext) {
        
        Class<?> clazz = (Class)source;
        BeanBuilder beanBuilder = (BeanBuilder)builder;
        
        Class keyType = TypeManager.getKeyType(clazz);
        
        KeyEntry key = new KeyEntry(keyType, clazz.getAnnotation(KeyCollection.class));
        Identify identify = key.getAnnotation(Identify.class);
        
        boolean useMapping =
            identify == null?
                true :
                identify.useMapping();
                
        if(useMapping && !TypeManager.isStandardType(key.getType())){
            
        }

        return builder;
    }
    
    protected void setKey(
            BeanBuilder builder, 
            KeyEntry source,
            ConfigurableApplicationContext applicationContext){
        
    }
}
