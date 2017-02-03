package org.brandao.brutos;

import org.brandao.brutos.mapping.Interceptor;

public interface InterceptorRegistry {

	InterceptorStackBuilder registerInterceptorStack(String name,
			boolean isDefault);

	InterceptorBuilder registerInterceptor(String name, Class<?> interceptor,
			boolean isDefault);

	Interceptor getRegisteredInterceptor(Class<?> clazz);

	Interceptor getRegisteredInterceptor(String name);

}
