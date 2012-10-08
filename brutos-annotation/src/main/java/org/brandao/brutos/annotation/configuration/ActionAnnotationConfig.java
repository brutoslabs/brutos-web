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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.brandao.brutos.*;
import org.brandao.brutos.annotation.*;
import org.brandao.brutos.mapping.StringUtil;

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
        View viewAnnotation = method.getAnnotation(View.class);
        
        String result;
        String view;
        org.brandao.brutos.DispatcherType dispatcher;
        String id;
        
        id = getId(action, method, applicationContext);
        
        Result resultAnnotation = method.getAnnotation(Result.class);
        result = resultAnnotation == null? null : resultAnnotation.value();

        dispatcher = 
            viewAnnotation == null || "".equals(viewAnnotation.dispatcher())? 
                BrutosConstants.DEFAULT_DISPATCHERTYPE : 
                org.brandao.brutos.DispatcherType.valueOf(viewAnnotation.dispatcher());
        
        ActionBuilder actionBuilder =
        controllerBuilder
            .addAction(
                id, 
                result,
                null, 
                dispatcher,
                method.getName() );

        view = getView(
                viewAnnotation, 
                actionBuilder,
                applicationContext);
        
        actionBuilder.setView(view);
        
        if(action != null && action.value().length > 1){
            String[] ids = action.value();
            for(int i=1;i<ids.length;i++ ){
                if(!StringUtil.isEmpty(ids[i]))
                    actionBuilder.addAlias(StringUtil.adjust(ids[i]));
                else{
                    throw new BrutosException(
                        "invalid action id: " + 
                        method.getDeclaringClass().getName() + "." + 
                        method.getName());
                }
            }
        }
        
        throwsSafe(actionBuilder, method,applicationContext);
        
        addParameters(actionBuilder, method,applicationContext);
        
        return actionBuilder;
    }

    protected String getId(Action action, Method method,
            ConfigurableApplicationContext applicationContext){
        
        boolean hasActionId =
            action != null && action.value().length > 0 && 
            !StringUtil.isEmpty(action.value()[0]);
        
        if(hasActionId)
            return StringUtil.adjust(action.value()[0]);
        else{
            String id = method.getName();
            id = id.replaceAll("Action$", "");
            
            if(StringUtil.isEmpty(id))
                throw new BrutosException("invalid action name: " + id);
            
            return id;
        }
    }
    
    protected String getView(View viewAnnotation, ActionBuilder action,
        ConfigurableApplicationContext applicationContext){
        
        boolean rendered = viewAnnotation == null? true : viewAnnotation.rendered();
        
        String view = 
            viewAnnotation != null && viewAnnotation.id().trim().length() > 0?
                viewAnnotation.id() : null;
        
        
        if(rendered){
            if(view != null)
                return viewAnnotation.id();
            else
                return createActionView(action, applicationContext);
        }
        else
            return null;
    }

    protected String createActionView(ActionBuilder action,
            ConfigurableApplicationContext applicationContext){
        
        return applicationContext.getViewResolver()
                .getView(action.getControllerBuilder(), action, null,
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

    protected void throwsSafe(ActionBuilder builder, Method method,
            ConfigurableApplicationContext applicationContext){
        
        List<ThrowableEntry> list = new ArrayList<ThrowableEntry>();
        ThrowSafeList throwSafeList = method.getAnnotation(ThrowSafeList.class);
        ThrowSafe throwSafe = method.getAnnotation(ThrowSafe.class);
        
        if(throwSafeList != null)
            list.addAll(
                    AnnotationUtil.toList(AnnotationUtil.toList(throwSafeList)));

        if(throwSafe != null)
            list.add(
                    AnnotationUtil.toEntry(throwSafe));
        
        Class[] exs = method.getExceptionTypes();
        Map<Class<? extends Throwable>,ThrowableEntry> map = 
                new HashMap<Class<? extends Throwable>,ThrowableEntry>();
        
        for(Class ex: exs){
            ThrowableEntry entry = new ThrowableEntry(ex);
            map.put(ex,entry);
        }
        
        for(ThrowableEntry entry: list)
            map.put(entry.getTarget(),entry);
        
        for(ThrowableEntry entry: map.values())
            super.applyInternalConfiguration(entry, builder, applicationContext);
        
    }
    
    public boolean isApplicable(Object source) {
        return source instanceof Method && 
               (((Method)source).isAnnotationPresent( Action.class ) ||
               ((Method)source).getName().endsWith("Action")) &&
               !((Method)source).isAnnotationPresent(Transient.class);
    }
    
}
