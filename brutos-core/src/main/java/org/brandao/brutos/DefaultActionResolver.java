


package org.brandao.brutos;

import org.brandao.brutos.interceptor.InterceptorHandler;
import org.brandao.brutos.scope.Scope;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.mapping.Action;


public class DefaultActionResolver implements ActionResolver{
    
    public ResourceAction getResourceAction(Controller controller,
            InterceptorHandler handler) {
        Scope scope = handler.getContext().getScopes().get(ScopeType.PARAM);
        return getResourceAction(
                controller,
                String.valueOf( scope.get( controller.getActionId() ) ),
                handler );
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
