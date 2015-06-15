package org.brandao.brutos.annotation.helper.interceptedby.app1;

import java.util.Map;

import javax.servlet.ServletRequest;

import org.brandao.brutos.interceptor.AbstractInterceptor;
import org.brandao.brutos.interceptor.InterceptedException;
import org.brandao.brutos.interceptor.InterceptorHandler;
import org.brandao.brutos.interceptor.InterceptorStack;
import org.brandao.brutos.web.RequestInfo;

public class TestName2InterceptorController 
	extends AbstractInterceptor{

	public void intercepted(InterceptorStack stack, InterceptorHandler handler)
			throws InterceptedException {
		
		Map<String,Object> prop = this.props;
		
		RequestInfo rq = RequestInfo.getCurrentRequestInfo();
		ServletRequest request = rq.getRequest();
		
		for(String key: prop.keySet()){
			request.setAttribute(key, prop.get(key));
		}
		
		request.setAttribute("intercepted.testName2", "true");
		
		stack.next(handler);
	}

}
