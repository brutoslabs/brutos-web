package org.brandao.brutos.hibernatemysql.registry;

public class UserRegistryException extends Exception{

	private static final long serialVersionUID = -4800481453724870910L;

	public UserRegistryException() {
		super();
	}

	public UserRegistryException(String message, Throwable cause) {
		super(message, cause);
	}

	public UserRegistryException(String message) {
		super(message);
	}

	public UserRegistryException(Throwable cause) {
		super(cause);
	}

}
