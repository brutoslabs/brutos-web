


package org.brandao.brutos.proxy;

import java.lang.reflect.Method;
import org.brandao.brutos.ConfigurableApplicationContext;
import org.brandao.brutos.Invoker;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.mapping.Action;


public abstract class ActionHandlerImp implements ActionHandler{

    private Object resource;
    private Controller controller;
    private ConfigurableApplicationContext context;
    private Invoker invoker;

    public ActionHandlerImp(Object resource, Controller controller,
            ConfigurableApplicationContext context, Invoker invoker){
        this.resource = resource;
        this.context = context;
        this.controller = controller;
        this.invoker = invoker;
    }
    
    public Object invoke(Object self, Method thisMethod, Method proceed,
            Object[] args) throws Throwable {
        Action action = controller.getMethod(thisMethod);
        return invoker.invoke(
            controller,
            context.getActionResolver().getResourceAction(action),
            resource,
            args);
    }
}
