/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.brandao.brutos.annotation.configuration;

import java.lang.reflect.Method;
import org.brandao.brutos.ActionBuilder;
import org.brandao.brutos.ConfigurableApplicationContext;
import org.brandao.brutos.ControllerBuilder;
import org.brandao.brutos.DispatcherType;
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
        
        
        if( action == null){
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
        dispatcher = dispatcherAnnotation == null? null : DispatcherType.valueOf(dispatcherAnnotation.value());
        
        ActionBuilder actionBuilder =
        controllerBuilder
            .addAction(
                id, 
                result,
                view, 
                dispatcher,
                method.getName() );
        
        return actionBuilder;
    }

    public boolean isApplicable(Object source) {
        return source instanceof Method && 
               (((Method)source).isAnnotationPresent( Action.class ) ||
               ((Method)source).getName().endsWith("Action"));
    }
    
}
