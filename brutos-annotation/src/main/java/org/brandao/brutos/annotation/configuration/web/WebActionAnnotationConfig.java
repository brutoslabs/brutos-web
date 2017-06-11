package org.brandao.brutos.annotation.configuration.web;

import java.util.HashSet;
import java.util.Set;

import org.brandao.brutos.ActionBuilder;
import org.brandao.brutos.ComponentRegistry;
import org.brandao.brutos.ControllerBuilder;
import org.brandao.brutos.DispatcherType;
import org.brandao.brutos.annotation.AcceptRequestType;
import org.brandao.brutos.annotation.Action;
import org.brandao.brutos.annotation.Controller;
import org.brandao.brutos.annotation.ResponseType;
import org.brandao.brutos.annotation.Stereotype;
import org.brandao.brutos.annotation.configuration.ActionAnnotationConfig;
import org.brandao.brutos.annotation.configuration.ActionEntry;
import org.brandao.brutos.annotation.configuration.ThrowableEntry;
import org.brandao.brutos.annotation.web.RequestMethod;
import org.brandao.brutos.annotation.web.ResponseError;
import org.brandao.brutos.annotation.web.ResponseErrors;
import org.brandao.brutos.annotation.web.ResponseStatus;
import org.brandao.brutos.mapping.StringUtil;
import org.brandao.brutos.web.MediaType;
import org.brandao.brutos.web.RequestMethodType;
import org.brandao.brutos.web.WebActionBuilder;
import org.brandao.brutos.web.WebControllerBuilder;

@Stereotype(
	target = Action.class, 
	executeAfter = Controller.class,
	minorVersion = 1
)
public class WebActionAnnotationConfig 
	extends ActionAnnotationConfig{

	protected ActionBuilder addAction(ActionEntry actionEntry,
			ControllerBuilder controllerBuilder, String id, String result,
			boolean resultRendered, String view, boolean resolved,
			DispatcherType dispatcher, String executor){
		
		AcceptRequestType acceptRequestType = actionEntry.getAnnotation(AcceptRequestType.class);
		ResponseType responseType = actionEntry.getAnnotation(ResponseType.class);
		
		
		RequestMethod requestMethod = 
			actionEntry.isAnnotationPresent(RequestMethod.class)?
				actionEntry.getAnnotation(RequestMethod.class) :
				actionEntry.getControllerClass().getAnnotation(RequestMethod.class);
		
		RequestMethodType requestMethodType = 
				requestMethod == null?
					null :
					RequestMethodType.valueOf(StringUtil.adjust(requestMethod.value()));
		
		WebControllerBuilder webControllerBuilder = 
				(WebControllerBuilder)controllerBuilder;
		
		WebActionBuilder builder = 
			(WebActionBuilder)webControllerBuilder.addAction(id, 
				requestMethodType, result, resultRendered, view, 
				dispatcher, resolved, executor);

		ResponseStatus responseStatus = 
				actionEntry.isAnnotationPresent(ResponseStatus.class)?
					actionEntry.getAnnotation(ResponseStatus.class) :
					actionEntry.getControllerClass().getAnnotation(ResponseStatus.class);

		if(acceptRequestType != null){
			String[] values = acceptRequestType.value();
			for(String v: values){
				builder.addRequestType(MediaType.valueOf(v));
			}
		}
	
		if(responseType != null){
			String[] values = responseType.value();
			for(String v: values){
				builder.addResponseType(MediaType.valueOf(v));
			}
		}
					
		if(responseStatus != null){
			builder.setResponseStatus(responseStatus.value());
		}
		
		return builder;
	}
	
	@SuppressWarnings("unchecked")
	protected void throwsSafe(ActionBuilder builder, ActionEntry method,
			ComponentRegistry componentRegistry) {

		Set<ThrowableEntry> list = new HashSet<ThrowableEntry>();

		ResponseErrors throwSafeList = 
				method.getAnnotation(ResponseErrors.class);

		throwSafeList =
				throwSafeList == null?
					method.getControllerClass().getAnnotation(ResponseErrors.class) :
					method.getAnnotation(ResponseErrors.class);
		
		ResponseError throwSafe = method.getAnnotation(ResponseError.class);
		
		if (throwSafeList != null && throwSafeList.exceptions().length != 0) {
			list.addAll(
				WebAnnotationUtil.toList(
					WebAnnotationUtil.toList(throwSafeList)));
		}

		if (throwSafe != null)
			list.add(WebAnnotationUtil.toEntry(throwSafe));

		Class<?>[] exs = method.getExceptionTypes();

		if (exs != null) {
			for (Class<?> ex : exs) {
				ThrowableEntry entry = 
					new WebThrowableEntry(
						throwSafeList, 
						(Class<? extends Throwable>) ex);

				if (!list.contains(entry)) {
					list.add(entry);
				}
			}
		}

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
	
}
