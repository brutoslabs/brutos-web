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

@Intercepts(isDefault=false)
@InterceptsStackList({
	@InterceptsStack(
		name="stackA",
		params={
				@Param(name="param1_1_a", value="value1_1_a"),
				@Param(name="param1_2_a", value="value1_2_a")
		}
	),
	@InterceptsStack(
			name="stackB",
			params={
					@Param(name="param1_1_b", value="value1_1_bx"),
					@Param(name="param1_2_b", value="value1_2_bx")
			}
		)
})
@InterceptsStack(
	name="stackC",
	params={
			@Param(name="param1_1_c", value="value1_1_c"),
			@Param(name="param1_2_c", value="value1_2_c")
	}
)
public class Interceptor1 extends AbstractInterceptor {

	public void intercepted(InterceptorStack stack, InterceptorHandler handler)
			throws InterceptedException {

		Map<String, Object> prop = this.props;

		RequestInfo rq = RequestInfo.getCurrentRequestInfo();
		ServletRequest request = rq.getRequest();

		Integer count = (Integer) request.getAttribute("count");
		
		if(count == null)
			count = 1;
		
		for (String key : prop.keySet()) {
			request.setAttribute("i1."+key, prop.get(key));
		}

		request.setAttribute("intercepted.interceptor1", count++);
		request.setAttribute("count", count);
		
		stack.next(handler);
		
	}

}