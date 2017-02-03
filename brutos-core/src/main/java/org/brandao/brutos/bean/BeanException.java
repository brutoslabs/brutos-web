package org.brandao.brutos.bean;

import org.brandao.brutos.BrutosException;


public class BeanException extends BrutosException{
    
    public BeanException() {
	super();
    }

    public BeanException(String message) {
	super(message);
    }

    public BeanException(String message, Throwable cause) {
        super(message, cause);
    }

    public BeanException(Throwable cause) {
        super(cause);
    }
}