package org.brandao.brutos.interceptor;

import org.brandao.brutos.mapping.Interceptor;

public class InterceptorEntry {

	private org.brandao.brutos.mapping.Interceptor interceptor;
	
	private InterceptorEntry next;
	
	
	public InterceptorEntry(Interceptor interceptor) {
		this.interceptor = interceptor;
	}

	public org.brandao.brutos.mapping.Interceptor getInterceptor() {
		return interceptor;
	}

	public void setInterceptor(org.brandao.brutos.mapping.Interceptor interceptor) {
		this.interceptor = interceptor;
	}

	public InterceptorEntry getNext() {
		return next;
	}

	public void setNext(InterceptorEntry next) {
		this.next = next;
	}

}
