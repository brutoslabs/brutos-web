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
import org.brandao.brutos.web.WebDispatcherType;

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
		ActionType actionType               = strategy == null ? null : WebActionType.valueOf(strategy.value());
		
		ResponseStatus responseStatus       = source.getAnnotation(ResponseStatus.class);
		boolean resolved                    = viewAnnotation == null ? false : viewAnnotation.resolved();
		boolean rendered                    = viewAnnotation == null ? true : viewAnnotation.rendered();
		
		String[] controllerIDs = 
				annotationController == null || annotationController.value().length == 0? 
						null : 
						annotationController.value();
		
		String[] requestMethodTypes = 
				requestMethod == null || requestMethod.value().length == 0? 
						null : 
						requestMethod.value();
		
		String controllerID = 
				controllerIDs == null?
					null : 
					StringUtil.adjust(controllerIDs[0]);

		RequestMethodType requestMethodType = 
				requestMethodTypes == null? 
					null : 
					RequestMethodType.valueOf(StringUtil.adjust(requestMethodTypes[0]));
		
		if(controllerIDs != null){
			for(int i=0;i<controllerIDs.length;i++){
				controllerIDs[i] = StringUtil.adjust(annotationController.value()[i]);
			}
		}
		
		org.brandao.brutos.DispatcherType dispatcher = 
			viewAnnotation == null? 
				null : 
				WebDispatcherType.valueOf(StringUtil.adjust(viewAnnotation.dispatcher()));
		
		if (annotationController != null) {
			name = annotationController.name();
			actionID = annotationController.actionId();
			defaultActionName = annotationController.defaultActionName();
		}

		WebControllerBuilder builder = 
			(WebControllerBuilder)webComponentRegistry
				.registerController(
						controllerID, 
						requestMethodType,
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
		
		if(builder.getId() != null && requestMethodTypes != null){
			for(int k = 1; k < requestMethodTypes.length; k++){
				RequestMethodType rmt = 
						RequestMethodType.valueOf(
								StringUtil.adjust(requestMethodTypes[k]));
				
				if (rmt == null){
					throw new BrutosException("invalid request method type: "
							+ source.getName());
				}
				
				builder.addAlias(builder.getId(), rmt);
			}
		}
		
		if(controllerIDs != null){
			for (int i = 1; i < controllerIDs.length; i++) {
				
				controllerIDs[i] = StringUtil.adjust(controllerIDs[i]);
				
				if(requestMethodType == null){
					if (!StringUtil.isEmpty(controllerIDs[i]))
						this.addAlias(builder, StringUtil.adjust(controllerIDs[i]));
					else
						throw new BrutosException("invalid controller id: "
								+ source.getName());
				}
				else{
					for(int k = 1; k < requestMethodTypes.length; k++){
						
						if (StringUtil.isEmpty(controllerIDs[i])){
							throw new BrutosException("invalid controller id: "
									+ source.getName());
						}

						RequestMethodType rmt = 
								RequestMethodType.valueOf(
										StringUtil.adjust(requestMethodTypes[k]));
						
						if (rmt == null){
							throw new BrutosException("invalid request method type: "
									+ source.getName());
						}
						
						builder.addAlias(StringUtil.adjust(controllerIDs[i]), rmt);
						
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
		
		List<ThrowableEntry> list    = new ArrayList<ThrowableEntry>();
		ResponseErrors throwSafeList = clazz.getAnnotation(ResponseErrors.class);
		ResponseError throwSafe      = clazz.getAnnotation(ResponseError.class);

		if(throwSafeList != null){
			ThrowableEntry entry = new WebThrowableEntry(throwSafeList, Throwable.class);
			
			if (!list.contains(entry)) {
				list.add(entry);
			}
			
		}
		else
		if(!this.applicationContext.isAutomaticExceptionMapping()){
			//desabilita todos os throw não declarados.
			WebThrowableEntry entry = new WebThrowableEntry((ResponseErrors)null, Throwable.class);
			entry.setEnabled(true);
			entry.setRendered(false);
			entry.setView(null);
			entry.setResolved(true);
			
			if (!list.contains(entry)) {
				list.add(entry);
			}
		}
		
		if (throwSafeList != null && throwSafeList.exceptions().length != 0) {
			list.addAll(
				WebAnnotationUtil.toList(
					WebAnnotationUtil.toList(throwSafeList)));
		}

		if (throwSafe != null)
			list.addAll(WebAnnotationUtil.toEntry(throwSafe));

		for (ThrowableEntry entry : list){
			super.applyInternalConfiguration(entry, builder, componentRegistry);
		}		
	}
	
}
