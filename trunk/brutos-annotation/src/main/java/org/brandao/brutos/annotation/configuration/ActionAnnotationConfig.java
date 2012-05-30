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

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import org.brandao.brutos.*;
import org.brandao.brutos.annotation.*;
import org.brandao.brutos.io.Resource;

/**
 *
 * @author Brandao
 */
@Stereotype(target=Action.class,executeAfter=Controller.class)
public class ActionAnnotationConfig extends AbstractAnnotationConfig{

    public Object applyConfiguration(Object source, Object builder, 
            ConfigurableApplicationContext applicationContext) {
        
        Method method = (Method)source;
        ControllerBuilder controllerBuilder = (ControllerBuilder)builder;
        Action action = (Action)method.getAnnotation(Action.class);
        
        String result;
        String view;
        DispatcherType dispatcher;
        String id;
        
        id = getId(action, method);
        
        Result resultAnnotation = method.getAnnotation(Result.class);
        result = resultAnnotation == null? null : resultAnnotation.value();

        view = getView(
                method.getAnnotation(View.class), 
                controllerBuilder.getClassType(), 
                method,
                applicationContext);

        Dispatcher dispatcherAnnotation = method.getAnnotation(Dispatcher.class);
        dispatcher = dispatcherAnnotation == null? 
                BrutosConstants.DEFAULT_DISPATCHERTYPE : 
                DispatcherType.valueOf(dispatcherAnnotation.value());
        
        ActionBuilder actionBuilder =
        controllerBuilder
            .addAction(
                id, 
                result,
                view, 
                dispatcher,
                method.getName() );
        
        addParameters(actionBuilder, method,applicationContext);
        
        return actionBuilder;
    }

    protected String getId(Action action, Method method){
        if( action == null || "".equals(action.value()) ){
            String id = method.getName().toLowerCase();
            id = id.endsWith("action")? id.replaceAll("action$", "") : id;
            return id;
        }
        else
            return action.value();
    }
    
    protected String getView(View viewAnnotation, Class controllerClass, Method action,
        ConfigurableApplicationContext applicationContext){
        
        if(viewAnnotation != null)
            return viewAnnotation.value();
        else
            return createActionView(controllerClass, action, applicationContext);
    }

    protected String createActionView(Class controllerClass, Method action,
            ConfigurableApplicationContext applicationContext){
        
        return applicationContext.getViewResolver()
                .getView(controllerClass, action, 
                applicationContext.getConfiguration());
    }
    
    private void addParameters(ActionBuilder builder, 
            Method method,ConfigurableApplicationContext applicationContext){
        
        Type[] genericTypes = (Type[]) method.getGenericParameterTypes();
        Class[] types = method.getParameterTypes();
        Annotation[][] annotations = method.getParameterAnnotations();
        
        for(int i=0;i<types.length;i++){
            ActionParamEntry actionParamEntry = 
                    new ActionParamEntry(null,types[i],genericTypes[i],annotations[i],i);
            
            super.applyInternalConfiguration(actionParamEntry, builder, applicationContext);
        }
    }
    
    public boolean isApplicable(Object source) {
        return source instanceof Method && 
               (((Method)source).isAnnotationPresent( Action.class ) ||
               ((Method)source).getName().endsWith("Action")) &&
               !((Method)source).isAnnotationPresent(Transient.class);
    }
    
}
