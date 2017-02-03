package org.brandao.brutos;

public class BrutosException extends RuntimeException {

	public BrutosException() {
		super();
	}

	public BrutosException(String message) {
		super(message);
	}

	public BrutosException(String message, Throwable cause) {
		super(message, cause);
	}

	public BrutosException(Throwable cause) {
		super(cause);
	}
}