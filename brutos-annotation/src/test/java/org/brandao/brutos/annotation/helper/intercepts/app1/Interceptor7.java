package org.brandao.brutos.annotation.helper.intercepts.app1;

import java.util.Map;

import javax.servlet.ServletRequest;

import org.brandao.brutos.annotation.Intercepts;
import org.brandao.brutos.annotation.Param;
import org.brandao.brutos.interceptor.AbstractInterceptor;
import org.brandao.brutos.interceptor.InterceptedException;
import org.brandao.brutos.interceptor.InterceptorHandler;
import org.brandao.brutos.interceptor.InterceptorStack;
import org.brandao.brutos.web.RequestInfo;

@Intercepts(
		params={
				@Param(name="param7.1", value="value7.1"),
				@Param(name="param7.2", value="value7.2")
		}
)
public class Interceptor7 
	extends AbstractInterceptor{

	public void intercepted(InterceptorStack stack, InterceptorHandler handler)
			throws InterceptedException {

		Map<String,Object> prop = this.props;
		
		RequestInfo rq = RequestInfo.getCurrentRequestInfo();
		ServletRequest request = rq.getRequest();
		
		for(String key: prop.keySet()){
			request.setAttribute(key, prop.get(key));
		}
		
		request.setAttribute("intercepted.interceptor7", "true");
		
		stack.next(handler);
		
	}

}