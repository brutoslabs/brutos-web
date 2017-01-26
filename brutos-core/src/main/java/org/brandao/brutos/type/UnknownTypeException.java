


package org.brandao.brutos.type;

import org.brandao.brutos.BrutosException;


public class UnknownTypeException extends BrutosException{
    
    private String page;
    
    public UnknownTypeException() {
	super();
    }

    public UnknownTypeException(String message) {
	super(message);
    }

    public UnknownTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownTypeException(Throwable cause) {
        super(cause);
    }
}
