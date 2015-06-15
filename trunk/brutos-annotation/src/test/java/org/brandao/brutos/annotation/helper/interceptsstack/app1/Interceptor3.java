package org.brandao.brutos.annotation.helper.interceptsstack.app1;

import java.util.Map;

import javax.servlet.ServletRequest;

import org.brandao.brutos.annotation.Intercepts;
import org.brandao.brutos.annotation.InterceptsStack;
import org.brandao.brutos.annotation.InterceptsStackList;
import org.brandao.brutos.annotation.Param;
import org.brandao.brutos.interceptor.AbstractInterceptor;
import org.brandao.brutos.interceptor.InterceptedException;
import org.brandao.brutos.interceptor.InterceptorHandler;
import org.brandao.brutos.interceptor.InterceptorStack;
import org.brandao.brutos.web.RequestInfo;

@Intercepts
@InterceptsStackList({
	@InterceptsStack(
		name="stackA",
		params={
				@Param(name="param3_1_a", value="value3_1_ax"),
				@Param(name="param3_2_a", value="value3_2_ax")
		},
		executeAfter=Interceptor1.class
	),
	@InterceptsStack(
			name="stackB",
			params={
					@Param(name="param3_1_b", value="value3_1_bx"),
					@Param(name="param3_2_b", value="value3_2_bx")
			},
			executeAfter=Interceptor1.class
		)
})
public class Interceptor3 extends AbstractInterceptor {

	public void intercepted(InterceptorStack stack, InterceptorHandler handler)
			throws InterceptedException {

		Map<String, Object> prop = this.props;

		RequestInfo rq = RequestInfo.getCurrentRequestInfo();
		ServletRequest request = rq.getRequest();

		Integer count = (Integer) request.getAttribute("count");
		
		if(count == null)
			count = 1;
		
		for (String key : prop.keySet()) {
			request.setAttribute(key, prop.get(key));
		}

		request.setAttribute("intercepted.interceptor3", count);
		request.setAttribute("count", count++);
		
		stack.next(handler);

	}

}