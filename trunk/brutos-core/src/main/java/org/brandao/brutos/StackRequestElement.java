package org.brandao.brutos;

import org.brandao.brutos.interceptor.ConfigurableInterceptorHandler;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.mapping.ThrowableSafeData;

public interface StackRequestElement {

	Throwable getObjectThrow();

	ThrowableSafeData getThrowableSafeData();

	Object[] getParameters();

	Controller getController();

	ResourceAction getAction();

	Object getResultAction();

	Object getResource();

	ConfigurableInterceptorHandler getHandler();

	String getView();

	DispatcherType getDispatcherType();

	void setObjectThrow(Throwable objectThrow);

	void setThrowableSafeData(ThrowableSafeData throwableSafeData);

	void setParameters(Object[] parameters);

	void setController(Controller controller);

	void setAction(ResourceAction action);

	void setResultAction(Object resultAction);

	void setHandler(ConfigurableInterceptorHandler handler);

	void setResource(Object resource);

	void setView(String view);

	void setDispatcherType(DispatcherType dispatcherType);

}
