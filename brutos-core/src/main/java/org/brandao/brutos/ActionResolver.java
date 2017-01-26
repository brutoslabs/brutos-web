

package org.brandao.brutos;

import org.brandao.brutos.interceptor.InterceptorHandler;
import org.brandao.brutos.mapping.Action;
import org.brandao.brutos.mapping.Controller;


public interface ActionResolver {

    
    ResourceAction getResourceAction( Controller controller,
            InterceptorHandler handler );

    
    ResourceAction getResourceAction( Controller controller, String actionId,
            InterceptorHandler handler );

    
    ResourceAction getResourceAction( Action action );

}
