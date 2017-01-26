

package org.brandao.brutos;

import org.brandao.brutos.mapping.Controller;


public interface ControllerRegistry {
    
    ControllerBuilder registerController( Class<?> classtype );

    ControllerBuilder registerController( String id, Class<?> classType );
    
    ControllerBuilder registerController( String id, String view, 
            boolean resolvedView, Class<?> classType );
    
    ControllerBuilder registerController( String id, String view,
           boolean resolvedView, String name, Class<?> classType, String actionId );

    ControllerBuilder registerController( String id, String view, 
            boolean resolvedView, DispatcherType dispatcherType,
            String name, Class<?> classType, String actionId );
    
    ControllerBuilder registerController( String id, String view, 
            boolean resolvedView, DispatcherType dispatcherType,
            String name, Class<?> classType, String actionId, ActionType actionType );
    
    ControllerBuilder registerController( String id, String view, DispatcherType dispatcherType,
            boolean resolvedView, String name, Class<?> classType, String actionId, ActionType actionType );
    
    Controller getRegisteredController(Class<?> clazz);
    
    Controller getRegisteredController(String name);

}
