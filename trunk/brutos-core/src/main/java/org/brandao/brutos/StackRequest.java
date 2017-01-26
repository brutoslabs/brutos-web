


package org.brandao.brutos;


public interface StackRequest {

    void push( StackRequestElement stackrequestElement );

    StackRequestElement getCurrent();

    StackRequestElement getNext( StackRequestElement stackrequestElement );

    boolean isEmpty();
    
    void pop();

}
