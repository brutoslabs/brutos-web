package org.brandao.brutos.interceptor;

import java.util.List;

public class ImpInterceptorStack implements InterceptorStack {

	private List stack;
	private int stackPos;

	public ImpInterceptorStack() {
	}

	public void exec(List interceptors, InterceptorHandler handler) {
		this.stack = interceptors;
		next(handler);
	}

	public void next(InterceptorHandler handler) {
		if (stackPos < this.stack.size()) {
			Interceptor interceptor = (Interceptor) this.stack.get(stackPos++);
			if (interceptor.accept(handler))
				interceptor.intercepted(this, handler);
		}
	}
}