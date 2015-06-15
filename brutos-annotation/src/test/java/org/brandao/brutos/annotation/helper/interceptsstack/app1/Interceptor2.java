package org.brandao.brutos.annotation.helper.interceptsstack.app1;

import java.util.Map;

import javax.servlet.ServletRequest;

import org.brandao.brutos.annotation.Intercepts;
import org.brandao.brutos.annotation.InterceptsStack;
import org.brandao.brutos.annotation.Param;
import org.brandao.brutos.interceptor.AbstractInterceptor;
import org.brandao.brutos.interceptor.InterceptedException;
import org.brandao.brutos.interceptor.InterceptorHandler;
import org.brandao.brutos.interceptor.InterceptorStack;
import org.brandao.brutos.web.RequestInfo;

@Intercepts
@InterceptsStack(
	name="stackC",
	params={
			@Param(name="param2_1_c", value="value2_1_c"),
			@Param(name="param2_2_c", value="value2_2_c")
	},
	executeAfter=Interceptor1.class
)
public class Interceptor2 extends AbstractInterceptor {

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

		request.setAttribute("intercepted.interceptor2", count);
		request.setAttribute("count", count++);
		
		stack.next(handler);

	}

}