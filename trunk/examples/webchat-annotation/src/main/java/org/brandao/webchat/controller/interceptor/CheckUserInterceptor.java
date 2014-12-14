package org.brandao.webchat.controller.interceptor;

import javax.enterprise.context.ApplicationScoped;
import org.brandao.brutos.FlowController;
import org.brandao.brutos.ResourceAction;
import org.brandao.brutos.annotation.Intercepts;
import org.brandao.brutos.interceptor.AbstractInterceptor;
import org.brandao.brutos.interceptor.InterceptedException;
import org.brandao.brutos.interceptor.InterceptorHandler;
import org.brandao.brutos.interceptor.InterceptorStack;
import org.brandao.brutos.scope.Scope;
import org.brandao.brutos.web.WebScopeType;
import org.brandao.webchat.controller.RoomController;
import org.brandao.webchat.model.User;

@ApplicationScoped
@Intercepts
public class CheckUserInterceptor extends AbstractInterceptor{

    public CheckUserInterceptor(){
    }
    
    @Override
    public void intercepted(InterceptorStack is, InterceptorHandler ih) 
            throws InterceptedException {
        
        Scope scope = ih.getContext().getScopes().get(WebScopeType.SESSION);
        User user = (User) scope.get("sessionUser");
        
        if( user != null && user.getRoom() != null )
            is.next(ih);
        else
            FlowController.execute(RoomController.class,"/login");
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
