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

import java.lang.reflect.Method;
import java.util.List;
import org.brandao.brutos.BrutosConstants;
import org.brandao.brutos.ConfigurableApplicationContext;
import org.brandao.brutos.ControllerBuilder;
import org.brandao.brutos.DispatcherType;
import org.brandao.brutos.annotation.*;
import org.brandao.brutos.annotation.bean.BeanPropertyAnnotationImp;
import org.brandao.brutos.bean.BeanInstance;
import org.brandao.brutos.bean.BeanProperty;

/**
 *
 * @author Brandao
 */
@Stereotype(target=Controller.class)
public class ControllerAnnotationConfig 
    extends AbstractAnnotationConfig{

    @Override
    public Object applyConfiguration(Object arg0, Object arg1,
            ConfigurableApplicationContext applicationContext){
        
        Class source = (Class)arg0;
        ControllerBuilder builder;
        
        Controller annotationController =
                (Controller) source.getAnnotation(Controller.class);
        View viewAnnotation = (View) source.getAnnotation(View.class);

        
        String controllerID;
        String view;
        DispatcherType dispatcher;
        
        String name     = null;
        String actionID = null;
        String defaultActionName = null;
        
        if(annotationController != null){
            name = annotationController.name();
            actionID = annotationController.actionId();
            defaultActionName = annotationController.defaultActionName();
        }

        controllerID = this.getControllerId(annotationController, source);
        
        dispatcher = 
            viewAnnotation == null || "".equals(viewAnnotation.dispatcher())? 
                BrutosConstants.DEFAULT_DISPATCHERTYPE : 
                DispatcherType.valueOf(viewAnnotation.dispatcher());
        
        builder =
                applicationContext.getControllerManager().addController(
                    controllerID,
                    null,
                    dispatcher,
                    name,
                    source,
                    actionID);

        
        view = getView((View) source.getAnnotation(View.class), builder,
            applicationContext);
        
        if(annotationController != null && annotationController.id().length > 1){
            String[] ids = annotationController.id();
            for(int i=1;i<ids.length;i++ ){
                builder.addAlias(ids[i]);
            }
        }
            
        builder.setView(view);
        builder.setDefaultAction(defaultActionName);
        
        super.applyInternalConfiguration(source, builder, applicationContext);
        
        addProperties(builder, applicationContext, source);
        addActions( builder, applicationContext, source );
        
        return builder;
    }
    
    protected void throwSafe(ThrowSafeList value, ControllerBuilder controllerBuilder){
        if(value == null)
            return;
        
        List<ThrowableEntry> list = 
            AnnotationUtil.toList(
                AnnotationUtil.toList(value));
        
        
    }
    
    protected String getView(View viewAnnotation, ControllerBuilder controller,
        ConfigurableApplicationContext applicationContext){
        
        boolean rendered = viewAnnotation == null? true : viewAnnotation.rendered();
        
        String view = 
            viewAnnotation != null && viewAnnotation.id().trim().length() > 0?
                viewAnnotation.id() : null;
        
        
        if(rendered){
            if(view != null)
                return viewAnnotation.id();
            else
                return createControllerView(controller, applicationContext);
        }
        else
            return null;
    }
    
    protected String createControllerView(ControllerBuilder controller,
            ConfigurableApplicationContext applicationContext){
        
        return applicationContext.getViewResolver()
                .getView(controller, null, 
                applicationContext.getConfiguration());
    }
    
    protected void addProperties(ControllerBuilder controllerBuilder, 
            ConfigurableApplicationContext applicationContext, Class clazz){
    
        BeanInstance instance = new BeanInstance(null,clazz);
        List props = instance.getProperties();
        for(int i=0;i<props.size();i++){
            BeanProperty prop = (BeanProperty) props.get(i);
            super.applyInternalConfiguration(new BeanPropertyAnnotationImp(prop), 
                    controllerBuilder, applicationContext);
        }
    }
    
    protected void addActions( ControllerBuilder controllerBuilder, 
            ConfigurableApplicationContext applicationContext, Class clazz ){
        
        Method[] methods = clazz.getMethods();

        for( Method m: methods ){
            super.applyInternalConfiguration(m, controllerBuilder, applicationContext);
        }
    }

    protected String getControllerName(Class controllerClass){
        return controllerClass.getSimpleName().replaceAll("Controller$", "");
    }
    
    protected String getControllerId(Controller annotation, Class controllerClass){
        if(annotation != null && annotation.id().length > 0)
            return annotation.id()[0];
        else
            return null;
    }
    
    public boolean isApplicable(Object source) {
        return source instanceof Class && 
               (((Class)source).isAnnotationPresent( Controller.class ) ||
               ((Class)source).getSimpleName().endsWith("Controller")) &&
               !((Class)source).isAnnotationPresent(Transient.class);
    }
    
}
