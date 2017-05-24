package org.brandao.brutos.web;

import org.brandao.brutos.BrutosException;

public class RequestMethodException extends BrutosException{

	private static final long serialVersionUID = -8693091268556751602L;

	public RequestMethodException() {
		super();
	}

	public RequestMethodException(String message, Throwable cause) {
		super(message, cause);
	}

	public RequestMethodException(String message) {
		super(message);
	}

	public RequestMethodException(Throwable cause) {
		super(cause);
	}

}
