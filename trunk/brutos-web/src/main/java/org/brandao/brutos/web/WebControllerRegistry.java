package org.brandao.brutos.web;

import org.brandao.brutos.ActionType;
import org.brandao.brutos.ControllerBuilder;
import org.brandao.brutos.ControllerRegistry;
import org.brandao.brutos.DispatcherType;

public interface WebControllerRegistry extends ControllerRegistry{

	ControllerBuilder registerController(String id, RequestMethodType requestType, 
			String view, boolean resolvedView, DispatcherType dispatcherType, 
			String name, Class<?> classType, String actionId, ActionType actionType);
	
}
