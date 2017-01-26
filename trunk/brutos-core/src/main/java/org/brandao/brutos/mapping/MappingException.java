

package org.brandao.brutos.mapping;

import org.brandao.brutos.BrutosException;


public class MappingException extends BrutosException{
    
    public MappingException() {
	super();
    }

    public MappingException(String message) {
	super(message);
    }

    public MappingException(String message, Throwable cause) {
        super(message, cause);
    }

    public MappingException(Throwable cause) {
        super(cause);
    }
    
}
