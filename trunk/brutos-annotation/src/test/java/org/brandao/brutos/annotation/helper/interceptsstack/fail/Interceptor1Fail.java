package org.brandao.brutos.annotation.helper.interceptsstack.fail;

import org.brandao.brutos.annotation.Intercepts;
import org.brandao.brutos.annotation.InterceptsStack;
import org.brandao.brutos.annotation.InterceptsStackList;
import org.brandao.brutos.interceptor.AbstractInterceptor;
import org.brandao.brutos.interceptor.InterceptedException;
import org.brandao.brutos.interceptor.InterceptorHandler;
import org.brandao.brutos.interceptor.InterceptorStack;

@Intercepts
@InterceptsStackList(
		@InterceptsStack(
		name="stackA"))
public class Interceptor1Fail extends AbstractInterceptor {

	public void intercepted(InterceptorStack stack, InterceptorHandler handler)
			throws InterceptedException {
	}

}