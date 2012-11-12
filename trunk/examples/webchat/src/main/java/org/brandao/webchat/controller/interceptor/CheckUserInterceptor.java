package org.brandao.webchat.controller.interceptor;

import javax.enterprise.context.ApplicationScoped;
import org.brandao.brutos.FlowController;
import org.brandao.brutos.Scopes;
import org.brandao.brutos.WebScopeType;
import org.brandao.brutos.annotation.Intercepts;
import org.brandao.brutos.interceptor.AbstractInterceptor;
import org.brandao.brutos.interceptor.InterceptedException;
import org.brandao.brutos.interceptor.InterceptorHandler;
import org.brandao.brutos.interceptor.InterceptorStack;
import org.brandao.brutos.scope.Scope;
import org.brandao.webchat.controller.RoomController;
import org.brandao.webchat.model.User;

@ApplicationScoped
@Intercepts
public class CheckUserInterceptor extends AbstractInterceptor{

    @Override
    public void intercepted(InterceptorStack is, InterceptorHandler ih) 
            throws InterceptedException {
        Scopes scopes = 
            ih.getContext().getScopes();
        
        Scope sessionScope = 
                scopes.get(WebScopeType.SESSION);
        
        User user = (User) sessionScope.get("user");
        
        if( user != null && user.getRoom() != null )
            is.next(ih);
        else
            FlowController.execute(RoomController.class, "login");
        
    }
    
    @Override
    public boolean accept(InterceptorHandler handler) {
        String actionName = 
            handler.getResourceAction().getMethodForm().getName();
        
        return !"login".equals(actionName) && !"enter".equals(actionName);
    }
    
}
