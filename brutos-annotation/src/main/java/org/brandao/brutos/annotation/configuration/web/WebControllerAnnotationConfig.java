package org.brandao.brutos.annotation.configuration.web;

import java.util.ArrayList;
import java.util.List;

import org.brandao.brutos.ActionType;
import org.brandao.brutos.ComponentRegistry;
import org.brandao.brutos.ControllerBuilder;
import org.brandao.brutos.DispatcherType;
import org.brandao.brutos.annotation.Controller;
import org.brandao.brutos.annotation.Stereotype;
import org.brandao.brutos.annotation.configuration.ControllerAnnotationConfig;
import org.brandao.brutos.annotation.configuration.ThrowableEntry;
import org.brandao.brutos.annotation.web.ResponseError;
import org.brandao.brutos.annotation.web.ResponseErrors;
import org.brandao.brutos.annotation.web.ResponseStatus;
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
		
		ResponseStatus responseStatus = 
				classType.getAnnotation(ResponseStatus.class);
		
		int responseStatusCode = 
				responseStatus == null?
					0 :
					responseStatus.value();
		
		builder.setResponseStatus(responseStatusCode);
		
		return builder;
	}
	
	protected void throwsSafe(ControllerBuilder builder, Class<?> clazz,
			ComponentRegistry componentRegistry) {
		
		List<ThrowableEntry> list = new ArrayList<ThrowableEntry>();
		
		ResponseErrors throwSafeList = 
				clazz.getAnnotation(ResponseErrors.class);
		
		ResponseError throwSafe = clazz.getAnnotation(ResponseError.class);
		
		if (throwSafeList != null && throwSafeList.exceptions().length != 0) {
			list.addAll(
				WebAnnotationUtil.toList(
					WebAnnotationUtil.toList(throwSafeList)));
		}

		if (throwSafe != null)
			list.add(WebAnnotationUtil.toEntry(throwSafe));

		if(throwSafeList != null){
			ThrowableEntry entry = 
					new WebThrowableEntry(
						throwSafeList, 
						Throwable.class);
			
			if (!list.contains(entry)) {
				list.add(entry);
			}
			
		}
		
		for (ThrowableEntry entry : list){
			super.applyInternalConfiguration(entry, builder, componentRegistry);
		}		
	}
	
	protected ControllerBuilder addAlias(ControllerBuilder builder, String id) {
		return builder.addAlias(id);
	}
	
	protected String getControllerName(ComponentRegistry componentRegistry,
			Class<?> controllerClass) {
		return "/" + super.getControllerName(componentRegistry, controllerClass);
	}
	
}
