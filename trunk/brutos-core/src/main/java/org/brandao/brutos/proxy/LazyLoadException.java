package org.brandao.brutos.proxy;

import org.brandao.brutos.BrutosException;

public class LazyLoadException extends BrutosException{

	private static final long serialVersionUID = 7370694957461211322L;

	public LazyLoadException() {
		super();
	}

	public LazyLoadException(String message, Throwable cause) {
		super(message, cause);
	}

	public LazyLoadException(String message) {
		super(message);
	}

	public LazyLoadException(Throwable cause) {
		super(cause);
	}

}
