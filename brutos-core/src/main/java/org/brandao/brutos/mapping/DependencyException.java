

package org.brandao.brutos.mapping;

import org.brandao.brutos.BrutosException;


public class DependencyException extends MappingException{
    
    public DependencyException() {
	super();
    }

    public DependencyException(String message) {
	super(message);
    }

    public DependencyException(String message, Throwable cause) {
        super(message, cause);
    }

    public DependencyException(Throwable cause) {
        super(cause);
    }
    
}
