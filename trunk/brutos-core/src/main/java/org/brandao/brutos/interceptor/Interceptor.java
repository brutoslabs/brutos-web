package org.brandao.brutos.interceptor;

import java.util.Map;

public interface Interceptor {

	public void setProperties(Map<String, Object> props);

	public boolean isConfigured();

	public void intercepted(InterceptorStack stack, InterceptorHandler handler)
			throws InterceptedException;

	public boolean accept(InterceptorHandler handler);

}
