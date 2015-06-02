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
import java.lang.reflect.Type;
import org.brandao.brutos.annotation.Identify;
import org.brandao.brutos.mapping.StringUtil;

/**
 *
 * @author Brandao
 */
public class ConstructorArgEntry {
    
    private int index;
    
    private Type genericType;
    
    private Class type;
    
    private String name;
    
    private Annotation[] annotation;

    public ConstructorArgEntry(){
    }
    
    public ConstructorArgEntry(String name,Class type,Type genericType,Annotation[] annotation, int index){
        this.name = name;
        this.type = type;
        this.genericType = genericType;
        this.annotation = annotation;
        this.index = index;
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
    
    public Type getGenericType() {
        return genericType;
    }

    public void setGenericType(Type genericType) {
        this.genericType = genericType;
    }

    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }

    public String getName() {
        
        Identify identify = 
                (Identify)this.getAnnotation(Identify.class);
        
        if(identify != null){
             String actionName = StringUtil.adjust(identify.bean());
             if(!StringUtil.isEmpty(actionName))
                 return actionName;
        }
        
        if( this.name != null ){
             String actionName = StringUtil.adjust(this.name);
             if(!StringUtil.isEmpty(actionName))
                 return actionName;
            
        }
        
        //return "arg"+index;
        return null;
    }

    public String getDefaultName(){
    	return "arg"+index;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public Annotation[] getAnnotation() {
        return annotation;
    }

    public void setAnnotation(Annotation[] annotation) {
        this.annotation = annotation;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
    
}
