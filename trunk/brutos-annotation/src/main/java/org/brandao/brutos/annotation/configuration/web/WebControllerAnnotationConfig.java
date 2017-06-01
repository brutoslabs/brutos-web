package org.brandao.brutos.annotation.configuration.web;

import org.brandao.brutos.ActionType;
import org.brandao.brutos.ComponentRegistry;
import org.brandao.brutos.ControllerBuilder;
import org.brandao.brutos.DispatcherType;
import org.brandao.brutos.annotation.Controller;
import org.brandao.brutos.annotation.Stereotype;
import org.brandao.brutos.annotation.configuration.ControllerAnnotationConfig;
import org.brandao.brutos.web.WebActionType;
import org.brandao.brutos.web.WebControllerBuilder;

@Stereotype(
	target = Controller.class,
	minorVersion = 1
)
public class WebControllerAnnotationConfig 
	extends ControllerAnnotationConfig{

	protected ControllerBuilder registerController(
			ComponentRegistry componentRegistry, String id, String view,
			boolean resolvedView, DispatcherType dispatcherType, String name,
			Class<?> classType, String actionId, ActionType actionType){
		
		WebActionType webActionType = 
				actionType == null? 
					null : 
					WebActionType.valueOf(actionType.id());
		
		WebControllerBuilder builder = 
			(WebControllerBuilder)componentRegistry
			.registerController(id, view, resolvedView,
				dispatcherType, name, classType, actionId, webActionType);
		
		return builder;
	}
	
	protected ControllerBuilder addAlias(ControllerBuilder builder, String id) {
		return builder.addAlias(id);
	}
	
	protected String getControllerName(ComponentRegistry componentRegistry,
			Class<?> controllerClass) {
		return "/" + super.getControllerName(componentRegistry, controllerClass);
	}
	
}
