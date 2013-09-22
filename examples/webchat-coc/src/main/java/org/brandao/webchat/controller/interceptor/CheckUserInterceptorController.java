package org.brandao.webchat.controller.interceptor;

import javax.enterprise.context.ApplicationScoped;
import org.brandao.brutos.FlowController;
import org.brandao.brutos.ResourceAction;
import org.brandao.brutos.WebScopeType;
import org.brandao.brutos.interceptor.AbstractInterceptor;
import org.brandao.brutos.interceptor.InterceptedException;
import org.brandao.brutos.interceptor.InterceptorHandler;
import org.brandao.brutos.interceptor.InterceptorStack;
import org.brandao.brutos.scope.Scope;
import org.brandao.webchat.controller.RoomController;
import org.brandao.webchat.model.User;

@ApplicationScoped
public class CheckUserInterceptorController extends AbstractInterceptor{

    public CheckUserInterceptorController(){
    }
    @Override
    public void intercepted(InterceptorStack is, InterceptorHandler ih) 
            throws InterceptedException {
        
        Scope scope = ih.getContext().getScopes().get(WebScopeType.SESSION);
        User user = (User) scope.get("currentUser");
        
        if( user != null && user.getRoom() != null )
            is.next(ih);
        else{
            RoomController controller = 
                    (RoomController)FlowController
                        .getController(RoomController.class);
            controller.loginAction();
        }
        
    }
    
    @Override
    public boolean accept(InterceptorHandler handler) {
        Object controllerInstance = handler.getResource();
        
        if(controllerInstance instanceof RoomController){
            ResourceAction ra = handler.getResourceAction();
            String actionName = 
                ra == null? 
                    null : 
                    ra.getMethodForm().getName();

            return actionName != null && !"/login".equals(actionName) && 
                    !"/enter".equals(actionName) && !"/default".equals(actionName);
        }
        else
            return false;
    }
    
}
