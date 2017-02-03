package org.brandao.brutos;

import org.brandao.brutos.interceptor.ConfigurableInterceptorHandler;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.mapping.ThrowableSafeData;

public class StackRequestElementImp implements StackRequestElement {

	private Throwable objectThrow;
	private ThrowableSafeData throwableSafeData;
	private Object[] parameters;
	private Controller controller;
	private ResourceAction action;
	private Object resultAction;
	private ConfigurableInterceptorHandler handler;
	private Object resource;
	private String view;
	private DispatcherType dispatcherType;

	public Throwable getObjectThrow() {
		return objectThrow;
	}

	public void setObjectThrow(Throwable objectThrow) {
		this.objectThrow = objectThrow;
	}

	public ThrowableSafeData getThrowableSafeData() {
		return throwableSafeData;
	}

	public void setThrowableSafeData(ThrowableSafeData throwableSafeData) {
		this.throwableSafeData = throwableSafeData;
	}

	public Object[] getParameters() {
		return parameters;
	}

	public void setParameters(Object[] parameters) {
		this.parameters = parameters;
	}

	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	public ResourceAction getAction() {
		return action;
	}

	public void setAction(ResourceAction action) {
		this.action = action;
	}

	public Object getResultAction() {
		return resultAction;
	}

	public void setResultAction(Object resultAction) {
		this.resultAction = resultAction;
	}

	public ConfigurableInterceptorHandler getHandler() {
		return handler;
	}

	public void setHandler(ConfigurableInterceptorHandler handler) {
		this.handler = handler;
	}

	public Object getResource() {
		return resource;
	}

	public void setResource(Object resource) {
		this.resource = resource;
	}

	public String getView() {
		return view;
	}

	public void setView(String view) {
		this.view = view;
	}

	public DispatcherType getDispatcherType() {
		return dispatcherType;
	}

	public void setDispatcherType(DispatcherType dispatcherType) {
		this.dispatcherType = dispatcherType;
	}

}
