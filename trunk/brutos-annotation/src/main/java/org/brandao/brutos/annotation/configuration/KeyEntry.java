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
import java.util.ArrayList;
import java.util.List;
import org.brandao.brutos.annotation.KeyCollection;

/**
 *
 * @author Brandao
 */
public class KeyEntry {
    
    private String name;
    
    private Class<?> type;
    
    private List<Annotation> annotation;

    public KeyEntry(){
        this(null, null);
    }

    public KeyEntry(Class<?> type, KeyCollection definition){
        this.type = type;
        this.annotation = new ArrayList<Annotation>();
        
        if(definition != null){
            this.annotation.add(definition.enumerated());
            this.annotation.add(definition.identify());
            this.annotation.add(definition.target());
            this.annotation.add(definition.temporal());
            this.annotation.add(definition.type());
        }
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    public List<Annotation> getAnnotation() {
        return annotation;
    }

    public void setAnnotation(List<Annotation> annotation) {
        this.annotation = annotation;
    }
    
    public boolean isAnnotationPresent(Class<? extends Annotation> annotation){
        for( Annotation a: this.annotation ){
            if( a.annotationType().isAssignableFrom(annotation) )
                return true;
        }
        
        return false;
    }
    
    public <T> T getAnnotation(Class<T> annotation){
        for( Annotation a: this.annotation ){
            if( a.annotationType().isAssignableFrom(annotation) )
                return (T) a;
        }
        
        return null;
    }
    
}
