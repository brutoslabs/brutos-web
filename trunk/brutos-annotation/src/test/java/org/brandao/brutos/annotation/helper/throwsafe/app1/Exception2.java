package org.brandao.brutos.annotation.helper.throwsafe.app1;

import org.brandao.brutos.annotation.Transient;

@SuppressWarnings("serial")
public class Exception2 
	extends Exception{

	@Transient
	private Throwable[] suppressed;
	
	@Override
	@Transient
	public synchronized Throwable getCause() {
		return super.getCause();
	}
	
	@Override
	@Transient
	public String getLocalizedMessage() {
		return super.getLocalizedMessage();
	}
	
	@Override
	@Transient
	public String getMessage() {
		return super.getMessage();
	}
	
	@Override
	@Transient
	public StackTraceElement[] getStackTrace() {
		return super.getStackTrace();
	}
	
}
