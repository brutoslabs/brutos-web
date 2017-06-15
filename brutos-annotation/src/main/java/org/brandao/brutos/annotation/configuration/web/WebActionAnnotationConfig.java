package org.brandao.brutos.annotation.configuration.web;

import java.util.HashSet;
import java.util.Set;

import org.brandao.brutos.ActionBuilder;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.ComponentRegistry;
import org.brandao.brutos.ControllerBuilder;
import org.brandao.brutos.DispatcherType;
import org.brandao.brutos.annotation.AcceptRequestType;
import org.brandao.brutos.annotation.Action;
import org.brandao.brutos.annotation.Controller;
import org.brandao.brutos.annotation.ResponseType;
import org.brandao.brutos.annotation.Result;
import org.brandao.brutos.annotation.ResultView;
import org.brandao.brutos.annotation.Stereotype;
import org.brandao.brutos.annotation.View;
import org.brandao.brutos.annotation.configuration.ActionAnnotationConfig;
import org.brandao.brutos.annotation.configuration.ActionEntry;
import org.brandao.brutos.annotation.configuration.ThrowableEntry;
import org.brandao.brutos.annotation.web.RequestMethod;
import org.brandao.brutos.annotation.web.ResponseError;
import org.brandao.brutos.annotation.web.ResponseErrors;
import org.brandao.brutos.annotation.web.ResponseStatus;
import org.brandao.brutos.mapping.MappingException;
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
	
	protected Object innerApplyConfiguration(Object source, Object builder,
			ComponentRegistry componentRegistry) {

		ActionEntry method                  = (ActionEntry) source;
		ControllerBuilder controllerBuilder = (ControllerBuilder) builder;
		Action action                       = (Action) method.getAnnotation(Action.class);
		View viewAnnotation                 = method.getAnnotation(View.class);
		ResultView resultView               = method.getAnnotation(ResultView.class);
		AcceptRequestType acceptRequestType = method.getAnnotation(AcceptRequestType.class);
		ResponseType responseType           = method.getAnnotation(ResponseType.class);

		RequestMethod requestMethod = 
				method.isAnnotationPresent(RequestMethod.class)?
					method.getAnnotation(RequestMethod.class) :
					method.getControllerClass().getAnnotation(RequestMethod.class);
					
		String[] actionIDs = 
				action == null? null : action.value();
					
		String[] requestMethodTypes = 
				requestMethod == null || requestMethod.value().length == 0? 
						null : 
						requestMethod.value();

		if(actionIDs != null){
			for(int i=0;i<actionIDs.length;i++){
				actionIDs[i] = StringUtil.adjust(action.value()[i]);
			}
		}
		
		String actionID = 
				actionIDs == null?
					null : 
					StringUtil.adjust(actionIDs[0]);

		RequestMethodType requestMethodType = 
				requestMethodTypes == null? 
					null : 
					RequestMethodType.valueOf(StringUtil.adjust(requestMethodTypes[0]));
		
		Result resultAnnotation = method.getAnnotation(Result.class);
		String result           = resultAnnotation == null ? null : resultAnnotation.value();

		org.brandao.brutos.DispatcherType dispatcher = 
				viewAnnotation == null? 
					null : 
					org.brandao.brutos.DispatcherType.valueOf(StringUtil.adjust(viewAnnotation.dispatcher()));

		ResponseStatus responseStatus = 
				method.isAnnotationPresent(ResponseStatus.class)?
					method.getAnnotation(ResponseStatus.class) :
					method.getControllerClass().getAnnotation(ResponseStatus.class);
		
		boolean resultRendered = resultView == null ? false : resultView.rendered();
		boolean rendered = viewAnnotation == null ? true : viewAnnotation.rendered();
		boolean resolved = viewAnnotation == null ? false : viewAnnotation.resolved();
		resolved = rendered ? resolved : true;

		String executor = method.isAbstractAction() ? null : method.getName();
		String view = getView(method, viewAnnotation, componentRegistry);

		if (!StringUtil.isEmpty(view) && StringUtil.isEmpty(executor)
				&& !rendered){
			throw new MappingException(
					"view must be rendered in abstract actions: " + actionID);
		}

		if (method.getReturnType() == void.class) {
			if (resultAnnotation != null || resultView != null)
				throw new MappingException("the action not return any value: "
						+ method.getName());
		}

		WebActionBuilder actionBuilder = 
				(WebActionBuilder) controllerBuilder.addAction(actionID, result,
				resultRendered, view, resolved, dispatcher, executor);

		if(acceptRequestType != null){
			String[] values = acceptRequestType.value();
			for(String v: values){
				actionBuilder.addRequestType(MediaType.valueOf(v));
			}
		}
	
		if(responseType != null){
			String[] values = responseType.value();
			for(String v: values){
				actionBuilder.addResponseType(MediaType.valueOf(v));
			}
		}
					
		if(responseStatus != null){
			actionBuilder.setResponseStatus(responseStatus.value());
		}
		
		if(actionIDs != null){
			for (int i = 1; i < actionIDs.length; i++) {
				
				actionIDs[i] = StringUtil.adjust(actionIDs[i]);
				
				if(requestMethodType == null){
					actionBuilder.addAlias(StringUtil.adjust(actionIDs[i]));
				}
				else{
					for(int k = 1; k < requestMethodTypes.length; k++){
						
						if (StringUtil.isEmpty(actionIDs[i])){
							throw new BrutosException("invalid action id: "
									+ method.getControllerClass().getName() + "."
									+ method.getName());
						}

						RequestMethodType rmt = 
								RequestMethodType.valueOf(
										StringUtil.adjust(requestMethodTypes[k]));
						
						if (rmt == null){
							throw new BrutosException("invalid request method type: "
									+ method.getControllerClass().getName() + "."
									+ method.getName());
						}
						
						return actionBuilder.addAlias(StringUtil.adjust(actionIDs[i]), rmt);
						
					}
				}
			}
		}
		
		throwsSafe(actionBuilder, method, componentRegistry);

		addParameters(actionBuilder, method, componentRegistry);

		addResultAction(actionBuilder, method.getResultAction(), componentRegistry);
		
		return actionBuilder;
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
