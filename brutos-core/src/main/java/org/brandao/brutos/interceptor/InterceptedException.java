

package org.brandao.brutos.interceptor;

import org.brandao.brutos.*;


public class InterceptedException extends BrutosException{
    
    public InterceptedException() {
	super();
    }

    public InterceptedException(String message) {
	super(message);
    }

    public InterceptedException(String message, Throwable cause) {
        super(message, cause);
    }

    public InterceptedException(Throwable cause) {
        super(cause);
    }
}