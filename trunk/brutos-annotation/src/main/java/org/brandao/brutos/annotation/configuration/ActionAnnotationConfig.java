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
import org.brandao.brutos.*;
import org.brandao.brutos.annotation.*;

/**
 *
 * @author Brandao
 */
@CustomAnnotation(target=Action.class,depends=Controller.class)
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
        
        
        if( action == null || "".equals(action.value()) ){
            id = controllerBuilder.getClassType().getSimpleName().toLowerCase();
            id = id.endsWith("controller")? id.replaceAll("controller^", "") : id;
        }
        else
            id = action.value();
        
        Result resultAnnotation = method.getAnnotation(Result.class);
        result = resultAnnotation == null? null : resultAnnotation.value();

        View viewAnnotation = method.getAnnotation(View.class);
        view = viewAnnotation == null? null : viewAnnotation.value();

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

    private void addParameters(ActionBuilder builder, 
            Method method,ConfigurableApplicationContext applicationContext){
        
        Type[] genericTypes = (Type[]) method.getGenericParameterTypes();
        Class[] types = method.getParameterTypes();
        Annotation[][] annotations = method.getParameterAnnotations();
        
        for(int i=0;i<types.length;i++){
            ActionParamEntry actionParamEntry = 
                    new ActionParamEntry(null,types[i],genericTypes[i],annotations[i]);
            
            super.applyInternalConfiguration(actionParamEntry, builder, applicationContext);
        }
    }
    
    public boolean isApplicable(Object source) {
        return source instanceof Method && 
               (((Method)source).isAnnotationPresent( Action.class ) ||
               ((Method)source).getName().endsWith("Action"));
    }
    
}
