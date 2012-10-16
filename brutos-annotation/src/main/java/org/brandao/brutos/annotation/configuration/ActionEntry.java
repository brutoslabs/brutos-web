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
import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 *
 * @author Brandao
 */
public class ActionEntry {
    
    private String name;
    
    private Annotation[] annotation;

    private Class[] exceptionTypes;
            
    private Class controllerClass;
    
    private Type[] genericParameterTypes;
    
    private Class[] parameterTypes;
    
    private Annotation[][] parameterAnnotations;
            
    private String view;
    
    private String dispatcher;
    
    private boolean abstractAction;
    
    public ActionEntry(Method method){
        this(
            method.getName(),
            method.getAnnotations(),
            method.getDeclaringClass(),
            method.getExceptionTypes(),
            method.getGenericParameterTypes(),
            method.getParameterTypes(),
            method.getParameterAnnotations(),
            false);
    }
    
    public ActionEntry(String name, Annotation[] annotation, Class controllerClass, 
            Class[] exceptionTypes, Type[] genericParameterTypes, Class[] parameterTypes,
            Annotation[][] parameterAnnotations, boolean abstractAction){
        this.name = name;
        this.annotation = annotation;
        this.exceptionTypes = exceptionTypes;
        this.controllerClass = controllerClass;
        this.genericParameterTypes = genericParameterTypes;
        this.parameterTypes = parameterTypes;
        this.parameterAnnotations = parameterAnnotations;
        this.abstractAction = abstractAction;
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
    
    public String getName() {
        return this.name;
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

    public void setExceptionTypes(Class[] exceptionTypes) {
        this.exceptionTypes = exceptionTypes;
    }
    
    public Class[] getExceptionTypes() {
        return this.exceptionTypes;
    }

    public Class getControllerClass() {
        return controllerClass;
    }

    public void setControllerClass(Class controllerClass) {
        this.controllerClass = controllerClass;
    }

    public Type[] getGenericParameterTypes() {
        return genericParameterTypes;
    }

    public void setGenericParameterTypes(Type[] genericTypes) {
        this.genericParameterTypes = genericTypes;
    }

    public Class[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public Annotation[][] getParameterAnnotations() {
        return parameterAnnotations;
    }

    public void setParameterAnnotations(Annotation[][] parameterAnnotations) {
        this.parameterAnnotations = parameterAnnotations;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public String getDispatcher() {
        return dispatcher;
    }

    public void setDispatcher(String dispatcher) {
        this.dispatcher = dispatcher;
    }

    public boolean isAbstractAction() {
        return abstractAction;
    }

    public void setAbstractAction(boolean abstractAction) {
        this.abstractAction = abstractAction;
    }
   
}
