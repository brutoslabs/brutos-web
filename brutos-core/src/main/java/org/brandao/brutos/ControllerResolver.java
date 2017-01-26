


package org.brandao.brutos;

import org.brandao.brutos.interceptor.ConfigurableInterceptorHandler;
import org.brandao.brutos.mapping.Controller;


public interface ControllerResolver {

    
    Controller getController( ControllerManager controllerManager, ConfigurableInterceptorHandler handler );

    
    Controller getController( ControllerManager controllerManager, Class controllerClass );
    
}
