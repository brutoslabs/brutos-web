package org.brandao.brutos.hibernatemysql.entityaccess;

public class EntityAccessException 
	extends Exception{

	private static final long serialVersionUID = 3834702204986277531L;

	public EntityAccessException() {
		super();
	}

	public EntityAccessException(String message, Throwable cause) {
		super(message, cause);
	}

	public EntityAccessException(String message) {
		super(message);
	}

	public EntityAccessException(Throwable cause) {
		super(cause);
	}

}
