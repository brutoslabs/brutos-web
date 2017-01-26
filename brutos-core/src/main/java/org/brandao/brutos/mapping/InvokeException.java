

package org.brandao.brutos.mapping;


public class InvokeException extends RuntimeException{
    
    public InvokeException() {
	super();
    }

    public InvokeException(String message) {
	super(message);
    }

    public InvokeException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvokeException(Throwable cause) {
        super(cause);
    }
    
}
