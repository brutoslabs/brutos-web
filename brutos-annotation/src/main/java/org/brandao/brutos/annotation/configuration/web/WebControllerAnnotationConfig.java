package org.brandao.brutos.annotation.configuration.web;

import java.util.ArrayList;
import java.util.List;

import org.brandao.brutos.ActionType;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.ComponentRegistry;
import org.brandao.brutos.ControllerBuilder;
import org.brandao.brutos.annotation.AcceptRequestType;
import org.brandao.brutos.annotation.ActionStrategy;
import org.brandao.brutos.annotation.Controller;
import org.brandao.brutos.annotation.ResponseType;
import org.brandao.brutos.annotation.Stereotype;
import org.brandao.brutos.annotation.View;
import org.brandao.brutos.annotation.configuration.ControllerAnnotationConfig;
import org.brandao.brutos.annotation.configuration.ThrowableEntry;
import org.brandao.brutos.annotation.web.RequestMethod;
import org.brandao.brutos.annotation.web.ResponseError;
import org.brandao.brutos.annotation.web.ResponseErrors;
import org.brandao.brutos.annotation.web.ResponseStatus;
import org.brandao.brutos.mapping.StringUtil;
import org.brandao.brutos.web.MediaType;
import org.brandao.brutos.web.RequestMethodType;
import org.brandao.brutos.web.WebActionType;
import org.brandao.brutos.web.WebComponentRegistry;
import org.brandao.brutos.web.WebControllerBuilder;

@Stereotype(
	target = Controller.class,
	minorVersion = 1
)
public class WebControllerAnnotationConfig 
	extends ControllerAnnotationConfig{

	public Object applyConfiguration0(Object arg0, Object arg1,
			ComponentRegistry componentRegistry) {

		WebComponentRegistry webComponentRegistry = (WebComponentRegistry)componentRegistry; 
		Class<?> source                     = (Class<?>) arg0;
		Controller annotationController     = source.getAnnotation(Controller.class);
		AcceptRequestType acceptRequestType = source.getAnnotation(AcceptRequestType.class);
		ResponseType responseType           = source.getAnnotation(ResponseType.class);
		View viewAnnotation                 = source.getAnnotation(View.class);
		ActionStrategy strategy             = source.getAnnotation(ActionStrategy.class);
		RequestMethod requestMethod         = source.getAnnotation(RequestMethod.class);
		String name                         = null;
		String actionID                     = null;
		String defaultActionName            = null;
		ActionType actionType               = strategy == null ? null : WebActionType.valueOf(strategy.value().name());
		
		ResponseStatus responseStatus       = source.getAnnotation(ResponseStatus.class);
		boolean resolved                    = viewAnnotation == null ? false : viewAnnotation.resolved();
		boolean rendered                    = viewAnnotation == null ? true : viewAnnotation.rendered();

		String[] controllerIDs = 
				annotationController == null || annotationController.value().length == 0? 
					null : 
						new String[annotationController.value().length];
		
		if(controllerIDs != null){
			for(int i=0;i<controllerIDs.length;i++){
				controllerIDs[i] = StringUtil.adjust(annotationController.value()[i]);
			}
		}
		
		RequestMethodType[] requestMethodType = 
				requestMethod == null? 
					null : 
					new RequestMethodType[requestMethod.value().length];
		
		if(requestMethodType != null){
			for(int i=0;i<requestMethodType.length;i++){
				requestMethodType[i] = RequestMethodType.valueOf(StringUtil.adjust(requestMethod.value()[i]));
			}
		}
		
		org.brandao.brutos.DispatcherType dispatcher = 
			viewAnnotation == null? 
				null : 
				org.brandao.brutos.DispatcherType.valueOf(StringUtil.adjust(viewAnnotation.dispatcher()));
		
		if (annotationController != null) {
			name = annotationController.name();
			actionID = annotationController.actionId();
			defaultActionName = annotationController.defaultActionName();
		}

		WebControllerBuilder builder = 
			(WebControllerBuilder)webComponentRegistry
				.registerController(
						controllerIDs == null? null : controllerIDs[0], 
						requestMethodType == null? null : requestMethodType[0],
						rendered ? getView((View) source.getAnnotation(View.class), webComponentRegistry) : null, 
						rendered ? resolved : true,	
						dispatcher, 
						name, 
						source, 
						actionID, 
						actionType);
				
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
		
		if(controllerIDs != null){
			for (int i = 1; i < controllerIDs.length; i++) {
				
				if(requestMethodType == null){
					if (!StringUtil.isEmpty(controllerIDs[i]))
						this.addAlias(builder, StringUtil.adjust(controllerIDs[i]));
					else
						throw new BrutosException("invalid controller id: "
								+ source.getName());
				}
				else{
					for(RequestMethodType rmt: requestMethodType){
						if (StringUtil.isEmpty(controllerIDs[i])){
							throw new BrutosException("invalid controller id: "
									+ source.getName());
						}

						if (rmt == null){
							throw new BrutosException("invalid request method type: "
									+ source.getName());
						}
						
						return builder.addAlias(StringUtil.adjust(controllerIDs[i]), rmt);
						
					}
				}
			}
		}

		super.applyInternalConfiguration(source, builder, webComponentRegistry);

		importBeans(builder, webComponentRegistry, builder.getClassType());
		
		throwsSafe(builder, source, webComponentRegistry);
		
		addProperties(builder, webComponentRegistry, source);
		
		addActions(builder, webComponentRegistry, source);

		if (!StringUtil.isEmpty(defaultActionName)){
			builder.setDefaultAction(defaultActionName);
		}
		
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
	
}
