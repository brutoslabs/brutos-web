

package org.brandao.brutos;

import java.util.Iterator;
import java.util.List;
import org.brandao.brutos.logger.Logger;
import org.brandao.brutos.mapping.Controller;


public interface ControllerManager {

    
    ControllerBuilder addController( Class<?> classtype );

    
    ControllerBuilder addController( String id, Class<?> classType );
    
    
    ControllerBuilder addController( String id, String view, boolean resolvedView, 
            Class<?> classType );
    
    
    ControllerBuilder addController( String id, String view,
           boolean resolvedView, String name, Class<?> classType, String actionId );

    
    ControllerBuilder addController( String id, String view, 
            boolean resolvedView, DispatcherType dispatcherType,
            String name, Class<?> classType, String actionId );
    
    
    ControllerBuilder addController( String id, String view, 
            boolean resolvedView, DispatcherType dispatcherType,
            String name, Class<?> classType, String actionId, ActionType actionType );

    
    ControllerBuilder addController( String id, String view, DispatcherType dispatcherType,
            boolean resolvedView, String name, Class<?> classType, String actionId, ActionType actionType );
    
    
    boolean contains( String id );
    
    
    Controller getController( String id );

    
    Controller getController( Class<?> controllerClass );

    
    List<Controller> getControllers();

    
    Iterator<Controller> getAllControllers();
    
    
    ControllerBuilder getCurrent();
    
    
    void setParent( ControllerManager parent );
    
    
    ControllerManager getParent();
    
    
    Logger getLogger();

    
    InterceptorManager getInterceptorManager();
   
    
    void setInterceptorManager(InterceptorManager interceptorManager);
    
    
    ValidatorFactory getValidatorFactory();
    
    
    void setValidatorFactory(ValidatorFactory validatorFactory);

    
    ConfigurableApplicationContext getApplicationContext();
    
    
    void setApplicationContext(ConfigurableApplicationContext applicationContext);

    void removeController(Class<?> clazz);
    
    void removeController(String name);
    
    public static interface InternalUpdate{
        
        void addControllerAlias( Controller controller, String alias );
        
        void removeControllerAlias( Controller controller, String alias );
        
    }
    
}
