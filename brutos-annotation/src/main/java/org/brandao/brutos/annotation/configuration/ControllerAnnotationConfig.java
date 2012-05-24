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
import org.brandao.brutos.BrutosConstants;
import org.brandao.brutos.ConfigurableApplicationContext;
import org.brandao.brutos.ControllerBuilder;
import org.brandao.brutos.DispatcherType;
import org.brandao.brutos.annotation.*;

/**
 *
 * @author Brandao
 */
@Stereotype(target=Controller.class,executeAfter=Intercepts.class)
public class ControllerAnnotationConfig 
    extends AbstractAnnotationConfig{

    @Override
    public Object applyConfiguration(Object arg0, Object arg1,
            ConfigurableApplicationContext applicationContext){
        
        Class source = (Class)arg0;
        ControllerBuilder builder;
        
        Controller annotationController =
                (Controller) source.getAnnotation(Controller.class);

        
        String controllerID = null;
        
        String view;
        DispatcherType dispatcher;
        String name;
        String actionID;

        if(annotationController == null){
            name = source.getSimpleName().replaceAll("Controller$", "");
            actionID = "invoke";
        }
        else{
            controllerID = annotationController.id();
            name = annotationController.name();
            actionID = annotationController.actionId();
        }

        View viewAnnotation = (View) source.getAnnotation(View.class);
        view = viewAnnotation == null? null : viewAnnotation.value();

        Dispatcher dispatcherAnnotation = (Dispatcher) source.getAnnotation(Dispatcher.class);
        dispatcher = dispatcherAnnotation == null? 
                BrutosConstants.DEFAULT_DISPATCHERTYPE : 
                DispatcherType.valueOf(dispatcherAnnotation.value());
        
        builder =
                applicationContext.getControllerManager().addController(
                    controllerID,
                    view,
                    dispatcher,
                    name, source,
                    actionID);

        addActions( builder, applicationContext, source );

        return builder;
    }
    
    protected void addActions( ControllerBuilder controllerBuilder, 
            ConfigurableApplicationContext applicationContext, Class clazz ){
        
        Method[] methods = clazz.getMethods();

        for( Method m: methods ){
            super.applyInternalConfiguration(m, controllerBuilder, applicationContext);
        }
    }
    
    public boolean isApplicable(Object source) {
        return source instanceof Class && 
               (((Class)source).isAnnotationPresent( Controller.class ) ||
               ((Class)source).getSimpleName().endsWith("Controller"));
    }
    
}
