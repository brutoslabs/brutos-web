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
import org.brandao.brutos.annotation.Bean;
import org.brandao.brutos.annotation.KeyCollection;
import org.brandao.brutos.annotation.Stereotype;
import org.brandao.brutos.type.TypeManager;

/**
 *
 * @author Brandao
 */
@Stereotype(target=KeyCollection.class, executeAfter=Bean.class)
public class KeyCollectionAnnotationConfig 
    extends AbstractAnnotationConfig{

    public boolean isApplicable(Object source) {
        return source instanceof KeyEntry;
    }

    public Object applyConfiguration(Object source, Object builder, 
            ComponentRegistry componentRegistry) {
    
        try{
            return applyConfiguration0(source, builder, componentRegistry);
        }
        catch(Exception e){
            throw 
                new BrutosException(
                        "can't create key of collection: " + ((KeyEntry)source).getName(),
                        e );
        }
        
    }
    
    public Object applyConfiguration0(Object source, Object builder, 
            ComponentRegistry componentRegistry) {
        
        KeyEntry key = (KeyEntry)source;
        if(AnnotationUtil.isBuildEntity(key.isUseBean(), key.getClassType()))
            buildKey(key, builder, componentRegistry);
        else
            addKey(key, (BeanBuilder)builder, componentRegistry);
        

        return builder;
    }
    
    protected void addKey(KeyEntry keyEntry, BeanBuilder builder, 
            ComponentRegistry componentRegistry){
        
        String key = keyEntry.getName();
        EnumerationType enumType = keyEntry.getEnumerated();
        String tempType = keyEntry.getTemporal();
        ScopeType scope = keyEntry.getScopeType();
        org.brandao.brutos.type.Type type = 
                keyEntry.getType() == null? null : AnnotationUtil.getTypeInstance(keyEntry.getType());
        
        Object classType = keyEntry.getTarget() == null? keyEntry.getGenericType() : keyEntry.getTarget();
        
        builder.setKey(
            key, enumType, tempType, null, scope, null, type, classType);
    }
    
    protected void buildKey(KeyEntry key, Object builder, 
            ComponentRegistry componentRegistry){
        super.applyInternalConfiguration(
                key, 
                builder, 
                componentRegistry);
    }
    
    
}
