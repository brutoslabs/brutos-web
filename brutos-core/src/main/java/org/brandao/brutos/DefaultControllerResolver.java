package org.brandao.brutos;

import org.brandao.brutos.interceptor.ConfigurableInterceptorHandler;
import org.brandao.brutos.mapping.Controller;

public class DefaultControllerResolver implements ControllerResolver {

	public Controller getController(ControllerManager controllerManager,
			ConfigurableInterceptorHandler handler) {
		String id = handler.requestId();
		return controllerManager.getController(id);
	}

	public Controller getController(ControllerManager controllerManager,
			Class controllerClass) {
		return controllerManager.getController(controllerClass);
	}

}
