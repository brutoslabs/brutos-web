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

package org.brandao.brutos.annotation.bean;

import java.lang.annotation.Annotation;
import org.brandao.brutos.bean.BeanProperty;
import org.brandao.brutos.bean.BeanPropertyWrapper;

/**
 *
 * @author Brandao
 */
public class BeanPropertyAnnotationImp 
    extends BeanPropertyWrapper implements BeanPropertyAnnotation{
    
    public BeanPropertyAnnotationImp(BeanProperty beanProperty){
        super(beanProperty);
    }

    public <T extends Annotation> T getAnnotation(Class<T> annotation) {
        
        if(this.beanProperty.getSet() != null){
            T value = (T)this.beanProperty.getSet().getAnnotation(annotation);
            
            if(value != null)
                return value;
        }
        
        if(this.beanProperty.getGet() != null){
            T value = (T)this.beanProperty.getGet().getAnnotation(annotation);
            
            if(value != null)
                return value;
        }
        
        if(this.beanProperty.getField() != null){
            T value = (T)this.beanProperty.getField().getAnnotation(annotation);
            
            if(value != null)
                return value;
        }
        
        return null;
    }

    public boolean isAnnotationPresent(Class<? extends Annotation> annotation) {
        
        if(this.beanProperty.getSet() != null){
            boolean value = this.beanProperty.getSet().isAnnotationPresent(annotation);
            if(value)
                return true;
        }
        if(this.beanProperty.getGet() != null){
            boolean value = this.beanProperty.getGet().isAnnotationPresent(annotation);
            
            if(value)
                return true;
        }
        
        if(this.beanProperty.getField() != null){
            boolean value = this.beanProperty.getField().isAnnotationPresent(annotation);
            if(value)
                return true;
        }
        
        return false;
    }
}
