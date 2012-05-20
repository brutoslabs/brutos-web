/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.brandao.brutos.annotation.configuration;

import java.lang.reflect.Method;
import org.brandao.brutos.ConfigurableApplicationContext;
import org.brandao.brutos.ControllerBuilder;
import org.brandao.brutos.DispatcherType;
import org.brandao.brutos.annotation.CustomAnnotation;
import org.brandao.brutos.annotation.Controller;
import org.brandao.brutos.annotation.Intercepts;

/**
 *
 * @author Brandao
 */
@CustomAnnotation(target=Controller.class,depends=Intercepts.class)
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
        String view         = null;
        
        DispatcherType dispatcher;
        String name;
        String actionID;

        if(annotationController == null){
            dispatcher = DispatcherType.FORWARD;
            name = source.getSimpleName();
            actionID = "invoke";
        }
        else{
            controllerID = annotationController.id();
            view = annotationController.view();
            dispatcher = DispatcherType.valueOf(annotationController.dispatcher());
            name = annotationController.name();
            actionID = annotationController.actionId();
        }
        
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
