

package org.brandao.brutos.web.test;

import org.brandao.brutos.ActionResolver;
import org.brandao.brutos.ConfigurableApplicationContext;
import org.brandao.brutos.ControllerManager;
import org.brandao.brutos.ControllerResolver;
import org.brandao.brutos.ObjectFactory;
import org.brandao.brutos.RenderView;
import org.brandao.brutos.StackRequestElement;
import org.brandao.brutos.web.WebInvoker;


public class MockWebInvoker extends WebInvoker{

    private StackRequestElement element;
    
    private String requestId;
    
    private Object request;
    
    private Object response;

    public MockWebInvoker( ControllerResolver controllerResolver, ObjectFactory objectFactory, 
            ControllerManager controllerManager, ActionResolver actionResolver, 
            ConfigurableApplicationContext applicationContext, RenderView renderView){
        super(controllerResolver, objectFactory, 
                controllerManager, actionResolver, applicationContext, 
                renderView);
    }
    
    public boolean invoke( StackRequestElement element ){
        this.element = element;
        return super.invoke(element);
    }

    public boolean invoke( String requestId ){
        this.requestId = requestId;
        return super.invoke(requestId);
    }

    public StackRequestElement getElement() {
        return element;
    }

    public String getRequestId() {
        return this.requestId == null? 
                this.element.getHandler().requestId() : 
                this.requestId;
    }

    public Object getRequest() {
        return request;
    }

    public Object getResponse() {
        return response;
    }

}
