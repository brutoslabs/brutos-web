package org.brandao.brutos.annotation.configuration.web;

import java.util.ArrayList;
import java.util.List;

import org.brandao.brutos.ActionType;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.ComponentRegistry;
import org.brandao.brutos.ControllerBuilder;
import org.brandao.brutos.annotation.ActionStrategy;
import org.brandao.brutos.annotation.Controller;
import org.brandao.brutos.annotation.Stereotype;
import org.brandao.brutos.annotation.View;
import org.brandao.brutos.annotation.configuration.ControllerAnnotationConfig;
import org.brandao.brutos.annotation.configuration.ThrowableEntry;
import org.brandao.brutos.annotation.web.ResponseError;
import org.brandao.brutos.annotation.web.ResponseErrors;
import org.brandao.brutos.annotation.web.ResponseStatus;
import org.brandao.brutos.mapping.StringUtil;
import org.brandao.brutos.web.WebActionType;
import org.brandao.brutos.web.WebControllerBuilder;

@Stereotype(
	target = Controller.class,
	minorVersion = 1
)
public class WebControllerAnnotationConfig 
	extends ControllerAnnotationConfig{

	public Object applyConfiguration0(Object arg0, Object arg1,
			ComponentRegistry componentRegistry) {

		Class<?> source                 = (Class<?>) arg0;
		Controller annotationController = (Controller) source.getAnnotation(Controller.class);
		View viewAnnotation             = (View) source.getAnnotation(View.class);
		ActionStrategy strategy         = (ActionStrategy)source.getAnnotation(ActionStrategy.class);
		String name                     = null;
		String actionID                 = null;
		String defaultActionName        = null;
		ActionType actionType           = strategy == null ? null : WebActionType.valueOf(strategy.value().name());
		String controllerID             = this.getControllerId(componentRegistry, annotationController, source);
		ResponseStatus responseStatus   = source.getAnnotation(ResponseStatus.class);
		boolean resolved                = viewAnnotation == null ? false : viewAnnotation.resolved();
		boolean rendered                = viewAnnotation == null ? true : viewAnnotation.rendered();
		
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
			(WebControllerBuilder)this.registerController(
					componentRegistry, 
					controllerID, 
					rendered ? getView((View) source.getAnnotation(View.class), componentRegistry) : null, 
					rendered ? resolved : true,	
					dispatcher, 
					name, 
					source, 
					actionID, 
					actionType);

		if(responseStatus != null){
			builder.setResponseStatus(responseStatus.value());
		}
		
		if (annotationController != null && annotationController.value().length > 1) {
			String[] ids = annotationController.value();
			for (int i = 1; i < ids.length; i++) {
				if (!StringUtil.isEmpty(ids[i]))
					this.addAlias(builder, StringUtil.adjust(ids[i]));
				else
					throw new BrutosException("invalid controller id: "
							+ source.getName());
			}
		}

		super.applyInternalConfiguration(source, builder, componentRegistry);

		importBeans(builder, componentRegistry, builder.getClassType());
		
		throwsSafe(builder, source, componentRegistry);
		
		addProperties(builder, componentRegistry, source);
		
		addActions(builder, componentRegistry, source);

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
	
	protected ControllerBuilder addAlias(ControllerBuilder builder, String id) {
		return builder.addAlias(id);
	}
	
	protected String getControllerName(ComponentRegistry componentRegistry,
			Class<?> controllerClass) {
		return "/" + super.getControllerName(componentRegistry, controllerClass);
	}
	
}
