package org.brandao.brutos.interceptor;

import org.brandao.brutos.mapping.Interceptor;

public class InterceptorStackFlow {

	private org.brandao.brutos.mapping.Interceptor interceptor;
	
	private InterceptorStackFlow next;
	
	
	public InterceptorStackFlow(Interceptor interceptor) {
		this.interceptor = interceptor;
	}

	public org.brandao.brutos.mapping.Interceptor getInterceptor() {
		return interceptor;
	}

	public void setInterceptor(org.brandao.brutos.mapping.Interceptor interceptor) {
		this.interceptor = interceptor;
	}

	public InterceptorStackFlow getNext() {
		return next;
	}

	public void setNext(InterceptorStackFlow next) {
		this.next = next;
	}

}
