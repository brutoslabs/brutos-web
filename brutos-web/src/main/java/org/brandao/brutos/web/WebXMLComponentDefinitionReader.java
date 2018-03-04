package org.brandao.brutos.web;

import org.brandao.brutos.ActionBuilder;
import org.brandao.brutos.ActionType;
import org.brandao.brutos.ComponentRegistry;
import org.brandao.brutos.ControllerBuilder;
import org.brandao.brutos.DispatcherType;
import org.brandao.brutos.xml.XMLComponentDefinitionReader;
import org.brandao.brutos.xml.XMLParseUtil;
import org.w3c.dom.Element;

public class WebXMLComponentDefinitionReader 
	extends XMLComponentDefinitionReader{

	protected final XMLParseUtil webParseUtil;
	
	public WebXMLComponentDefinitionReader(ComponentRegistry componenetRegistry) {
		super(componenetRegistry);
		this.webParseUtil = new XMLParseUtil(WebXMLBrutosConstants.XML_BRUTOS_WEB_NAMESPACE);
	}

	protected void loadController(Element controller) {

		WebControllerRegistry webControllerRegistry = (WebControllerRegistry)componentRegistry;
	
		String id 							= parseUtil.getAttribute(controller, "id");
		ActionType actionType 				= ActionType.valueOf(parseUtil.getAttribute(controller, "action-type"));
		DispatcherType dispatcher 			= DispatcherType.valueOf(parseUtil.getAttribute(controller, "dispatcher"));
		String requestMethodName   			= webParseUtil.getAttribute(controller, "request-method");
		String view 						= parseUtil.getAttribute(controller, "view");
		boolean resolvedView				= parseUtil.getBooleanAttribute(controller, "resolved-view");
		boolean renderedView				= parseUtil.getBooleanAttribute(controller, "rendered-view");
		String name 						= parseUtil.getAttribute(controller, "name");
		String clazzName 					= parseUtil.getAttribute(controller, "class");
		String actionId 					= parseUtil.getAttribute(controller, "action-id");
		String defaultAction 				= parseUtil.getAttribute(controller, "default-action");
		int responseStatus					= webParseUtil.getIntAttribute(controller, "response-status");
		Class<?> clazz 						= this.getClass(clazzName);
		RequestMethodType requestMethodType	= RequestMethodType.valueOf(requestMethodName);
		
		
		WebControllerBuilder controllerBuilder = 
				(WebControllerBuilder)webControllerRegistry
					.registerController(
							id, 
							requestMethodType, 
							renderedView? view : null, 
							renderedView? resolvedView : true, 
							dispatcher, 
							name, 
							clazz, 
							actionId, 
							actionType
					);

		
		controllerBuilder.setResponseStatus(responseStatus);
		
		if (defaultAction != null){
			controllerBuilder.setDefaultAction(defaultAction);
		}

		this.loadControllerDependencies(controller, controllerBuilder);

	}
	
	protected void addAction(Element actionNode,
			ControllerBuilder controllerBuilder) {

		WebControllerBuilder webControllerBuilder = 
				(WebControllerBuilder)controllerBuilder;
		
		String id 							= parseUtil.getAttribute(actionNode, "id");
		String executor 					= parseUtil.getAttribute(actionNode, "executor");
		String result 						= parseUtil.getAttribute(actionNode, "result");
		boolean resultRendered 				= parseUtil.getBooleanAttribute(actionNode, "result-rendered");
		String view 						= parseUtil.getAttribute(actionNode, "view");
		DispatcherType dispatcher 			= DispatcherType.valueOf(parseUtil.getAttribute(actionNode, "dispatcher"));
		String requestMethodName   			= webParseUtil.getAttribute(actionNode, "request-method");
		boolean resolvedView				= Boolean.valueOf(parseUtil.getAttribute(actionNode, "resolved-view"));
		boolean renderedView				= Boolean.valueOf(parseUtil.getAttribute(actionNode, "rendered-view"));
		int responseStatus					= webParseUtil.getIntAttribute(actionNode, "response-status");
		RequestMethodType requestMethodType	= RequestMethodType.valueOf(requestMethodName);
		
		WebActionBuilder actionBuilder = 
				(WebActionBuilder)webControllerBuilder.addAction(
						id, 
						requestMethodType, 
						result, 
						resultRendered, 
						renderedView? view : null, 
						dispatcher, 
						renderedView? resolvedView : true, 
						executor 
				);
		
		actionBuilder.setResponseStatus(responseStatus);
		
		this.loadActionDependencies(actionNode, actionBuilder);

	}

	@Override
	protected void addThrowSafe(Element throwSafeNode,
			ControllerBuilder builder) {
		this.innerAddThrowSafe(throwSafeNode, builder);
	}

	@Override
	protected void addThrowSafe(Element throwSafeNode,
			ActionBuilder builder) {
		this.innerAddThrowSafe(throwSafeNode, builder);
	}
	
	protected void innerAddThrowSafe(Element element, Object builder) {
		
		int code 					= webParseUtil.getIntAttribute(element, "code");
		Class<?> target 			= this.getClass(parseUtil.getAttribute(element, "target"));
		String reason 				= webParseUtil.getAttribute(element, "reason");
		String view 				= parseUtil.getAttribute(element, "view");
		String name 				= parseUtil.getAttribute(element, "name");
		DispatcherType dispatcher 	= DispatcherType.valueOf(parseUtil.getAttribute(element, "dispatcher"));
		boolean resolved 			= parseUtil.getBooleanAttribute(element, "resolved-view");
		boolean rendered 			= parseUtil.getBooleanAttribute(element, "rendered-view");
		//boolean enabled 			= Boolean.valueOf(parseUtil.getAttribute(element, "enabled"));
		
		if(builder instanceof WebControllerBuilder){
			((WebControllerBuilder)builder).addThrowable(
					code, 
					reason, 
					target == null? Throwable.class : target, 
					rendered? view : null, 
					name, 
					dispatcher, 
					rendered? resolved : true
			);
		}
		
		if(builder instanceof WebActionBuilder){
			((WebActionBuilder)builder).addThrowable(
					code, 
					reason, 
					target == null? Throwable.class : target, 
					rendered? view : null, 
					name, 
					dispatcher, 
					rendered? resolved : true
			);
		}
		
	}
	
}
