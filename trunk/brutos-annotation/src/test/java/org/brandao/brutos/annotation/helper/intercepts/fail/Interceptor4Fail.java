package org.brandao.brutos.annotation.helper.intercepts.fail;

import org.brandao.brutos.annotation.Intercepts;
import org.brandao.brutos.interceptor.AbstractInterceptor;
import org.brandao.brutos.interceptor.InterceptedException;
import org.brandao.brutos.interceptor.InterceptorHandler;
import org.brandao.brutos.interceptor.InterceptorStack;

@Intercepts(name="interc.eptor")
public class Interceptor4Fail 
	extends AbstractInterceptor{

	public void intercepted(InterceptorStack stack, InterceptorHandler handler)
			throws InterceptedException {
	}

}
