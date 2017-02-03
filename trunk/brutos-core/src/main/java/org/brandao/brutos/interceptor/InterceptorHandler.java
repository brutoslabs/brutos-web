package org.brandao.brutos.interceptor;

import java.text.ParseException;
import org.brandao.brutos.ApplicationContext;
import org.brandao.brutos.ResourceAction;

public interface InterceptorHandler {

	ResourceAction getResourceAction();

	Object getResource();

	String requestId();

	ApplicationContext getContext();

	Object[] getParameters() throws InstantiationException,
			IllegalAccessException, ParseException;

	Object getResult();

}
