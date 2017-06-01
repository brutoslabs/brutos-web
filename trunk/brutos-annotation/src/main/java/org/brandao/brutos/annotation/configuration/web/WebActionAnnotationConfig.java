package org.brandao.brutos.annotation.configuration.web;

import java.util.HashSet;
import java.util.Set;

import org.brandao.brutos.ActionBuilder;
import org.brandao.brutos.ActionType;
import org.brandao.brutos.ComponentRegistry;
import org.brandao.brutos.ControllerBuilder;
import org.brandao.brutos.DispatcherType;
import org.brandao.brutos.annotation.Action;
import org.brandao.brutos.annotation.Controller;
import org.brandao.brutos.annotation.Stereotype;
import org.brandao.brutos.annotation.configuration.ActionAnnotationConfig;
import org.brandao.brutos.annotation.configuration.ActionEntry;
import org.brandao.brutos.annotation.configuration.ThrowableEntry;
import org.brandao.brutos.annotation.web.RequestMethod;
import org.brandao.brutos.annotation.web.ResponseError;
import org.brandao.brutos.annotation.web.ResponseErrors;
import org.brandao.brutos.annotation.web.ResponseStatus;
import org.brandao.brutos.mapping.StringUtil;
import org.brandao.brutos.web.BrutosWebConstants;
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
		
		RequestMethod requestMethod = 
			actionEntry.isAnnotationPresent(RequestMethod.class)?
				actionEntry.getAnnotation(RequestMethod.class) :
				actionEntry.getControllerClass().getAnnotation(RequestMethod.class);
		
		RequestMethodType requestMethodType = 
				requestMethod == null?
					BrutosWebConstants.DEFAULT_REQUEST_METHOD_TYPE :
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
		
		int responseStatusCode = 0;
		
		if(responseStatus != null){
			int code = responseStatus.code();
			
			if(code == BrutosWebConstants.DEFAULT_RESPONSE_STATUS){
				code = responseStatus.value();
			}
					
		}
		else{
			responseStatusCode = BrutosWebConstants.DEFAULT_RESPONSE_STATUS;
		}
		
		builder.setResponseStatus(responseStatusCode);
		
		return builder;
	}
	
	@SuppressWarnings("unchecked")
	protected void throwsSafe(ActionBuilder builder, ActionEntry method,
			ComponentRegistry componentRegistry) {

		Set<ThrowableEntry> list = new HashSet<ThrowableEntry>();

		ResponseErrors controllerThrowSafeList = 
				method.getControllerClass().getAnnotation(ResponseErrors.class);
		
		ResponseErrors throwSafeList = 
				method.getAnnotation(ResponseErrors.class);
		
		ResponseError controllerThrowSafe = method.getControllerClass().getAnnotation(ResponseError.class);
		ResponseError throwSafe = method.getAnnotation(ResponseError.class);
		
		if (controllerThrowSafeList != null && controllerThrowSafeList.exceptions().length != 0) {
			list.addAll(
				WebAnnotationUtil.toList(
					WebAnnotationUtil.toList(controllerThrowSafeList)));
		}
		
		if (controllerThrowSafe != null)
			list.add(WebAnnotationUtil.toEntry(controllerThrowSafe));
		
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
						throwSafeList == null? controllerThrowSafeList : throwSafeList, 
						(Class<? extends Throwable>) ex);

				if (!list.contains(entry)) {
					list.add(entry);
				}
			}
		}

		if(throwSafeList != null || controllerThrowSafeList != null){
			ThrowableEntry entry = 
					new WebThrowableEntry(
						throwSafeList == null? controllerThrowSafeList : throwSafeList, 
						Throwable.class);
			
			if (!list.contains(entry)) {
				list.add(entry);
			}
			
		}
		
		for (ThrowableEntry entry : list){
			this.addThrowSafe(method, entry, builder, componentRegistry);
		}
		
	}
	
	protected String getId(Action action, ActionEntry method,
			ControllerBuilder controllerBuilder,
			ComponentRegistry componentRegistry) {
		
		String id = super.getId(action, method, controllerBuilder, componentRegistry);
		
		boolean hasActionId = action != null && action.value().length > 0
				&& !StringUtil.isEmpty(action.value()[0]);
		
		if(!hasActionId && controllerBuilder.getActionType() != ActionType.PARAMETER){
			id = 
				id.startsWith("/") || id.startsWith("\\") ? 
					id : 
					"/" + id;
		}
		
		return id;
	}
	
}
