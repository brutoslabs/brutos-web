

package org.brandao.brutos.web;

import org.brandao.brutos.*;
import org.brandao.brutos.interceptor.InterceptorHandler;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.mapping.Action;
import org.brandao.brutos.scope.Scope;


public class WebActionResolver implements ActionResolver{
    
    public ResourceAction getResourceAction(Controller controller,
            InterceptorHandler handler) {

        if( controller.getId() != null ){
            Scope scope = handler.getContext().getScopes()
                    .get(WebScopeType.PARAM.toString());

            return getResourceAction( 
                    controller,
                    String.valueOf(
                            scope.get( controller.getActionId() ) ),
                    handler);
        }
        else
            return getResourceAction( controller, handler.requestId(), handler );
        
    }

    public ResourceAction getResourceAction(Controller controller, String actionId, 
            InterceptorHandler handler) {

        Action method = controller
                .getActionByName( actionId );
        return method == null? null : getResourceAction( method );
    }

    public ResourceAction getResourceAction(Action action) {
        return new DefaultResourceAction( action );
    }

}
