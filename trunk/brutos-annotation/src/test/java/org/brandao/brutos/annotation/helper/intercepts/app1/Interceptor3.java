package org.brandao.brutos.annotation.helper.intercepts.app1;

import java.util.Map;

import javax.servlet.ServletRequest;

import org.brandao.brutos.annotation.Intercepts;
import org.brandao.brutos.interceptor.AbstractInterceptor;
import org.brandao.brutos.interceptor.InterceptedException;
import org.brandao.brutos.interceptor.InterceptorHandler;
import org.brandao.brutos.interceptor.InterceptorStack;
import org.brandao.brutos.web.RequestInfo;

@Intercepts
public class Interceptor3 
	extends AbstractInterceptor{

	public void intercepted(InterceptorStack stack, InterceptorHandler handler)
			throws InterceptedException {

		Map<String,Object> prop = this.props;
		
		RequestInfo rq = RequestInfo.getCurrentRequestInfo();
		ServletRequest request = rq.getRequest();
		
		for(String key: prop.keySet()){
			request.setAttribute(key, prop.get(key));
		}
		
		request.setAttribute("intercepted.interceptor3", "true");
		
		stack.next(handler);
		
	}

}
