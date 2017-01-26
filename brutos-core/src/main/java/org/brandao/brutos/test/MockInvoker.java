

package org.brandao.brutos.test;

import org.brandao.brutos.Invoker;
import org.brandao.brutos.StackRequestElement;


public class MockInvoker extends Invoker{

    private StackRequestElement element;
    private String requestId;
    private Object request;
    private Object response;
    
    public MockInvoker(){
    }
    
    public boolean invoke( StackRequestElement element ){
        this.element = element;
        return true;
    }

    public boolean invoke( String requestId ){
        this.requestId = requestId;
        return true;
    }

    public StackRequestElement getElement() {
        return element;
    }

    public String getRequestId() {
        return requestId;
    }

    public Object getRequest() {
        return request;
    }

    public Object getResponse() {
        return response;
    }

}
