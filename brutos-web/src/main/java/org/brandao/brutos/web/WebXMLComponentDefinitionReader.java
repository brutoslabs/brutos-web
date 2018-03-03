package org.brandao.brutos.web;

import org.brandao.brutos.ActionBuilder;
import org.brandao.brutos.ActionType;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.ComponentRegistry;
import org.brandao.brutos.ControllerBuilder;
import org.brandao.brutos.DispatcherType;
import org.brandao.brutos.mapping.StringUtil;
import org.brandao.brutos.xml.XMLComponentDefinitionReader;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class WebXMLComponentDefinitionReader 
	extends XMLComponentDefinitionReader{

	public WebXMLComponentDefinitionReader(ComponentRegistry componenetRegistry) {
		super(componenetRegistry);
	}

	protected void loadController(Element controller) {

		WebControllerRegistry webControllerRegistry = (WebControllerRegistry)componentRegistry;
	
		String id 							= parseUtil.getAttribute(controller, "id");
		ActionType actionType 				= ActionType.valueOf(parseUtil.getAttribute(controller, "action-type"));
		DispatcherType dispatcher 			= DispatcherType.valueOf(parseUtil.getAttribute(controller, "dispatcher"));
		String requestMethodName   			= parseUtil.getAttribute(controller, "request-method");
		String view 						= parseUtil.getAttribute(controller, "view");
		boolean resolvedView				= Boolean.valueOf(parseUtil.getAttribute(controller, "resolved-view"));
		boolean renderedView				= Boolean.valueOf(parseUtil.getAttribute(controller, "rendered-view"));
		String name 						= parseUtil.getAttribute(controller, "name");
		String clazzName 					= parseUtil.getAttribute(controller, "class");
		String actionId 					= parseUtil.getAttribute(controller, "action-id");
		String defaultAction 				= parseUtil.getAttribute(controller, "default-action");
		int responseStatus					= Integer.parseInt(parseUtil.getAttribute(controller, "response-status"));
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
	
	protected void loadControllerDependencies(Element controller, ControllerBuilder controllerBuilder){
		
		loadAcceptRequestTypes(parseUtil.getElements(controller,
				WebXMLBrutosConstants.XML_BRUTOS_ACCEPT_REQUEST_TYPE), controllerBuilder);

		loadResponseTypes(parseUtil.getElements(controller,
				WebXMLBrutosConstants.XML_BRUTOS_RESPONSE_TYPE), controllerBuilder);
		
		loadResponseErrors(parseUtil.getElement(controller,
				WebXMLBrutosConstants.XML_BRUTOS_RESPONSE_ERRORS), controllerBuilder);
		
		super.loadControllerDependencies(controller, controllerBuilder);
		
	}
	
	protected void addAction(Element actionNode,
			ControllerBuilder controllerBuilder) {

		WebControllerBuilder webControllerBuilder = 
				(WebControllerBuilder)controllerBuilder;
		
		String id 							= parseUtil.getAttribute(actionNode, "id");
		String executor 					= parseUtil.getAttribute(actionNode, "executor");
		String result 						= parseUtil.getAttribute(actionNode, "result");
		boolean resultRendered 				= Boolean.valueOf(parseUtil.getAttribute(actionNode, "result-rendered"));
		String view 						= parseUtil.getAttribute(actionNode, "view");
		DispatcherType dispatcher 			= DispatcherType.valueOf(parseUtil.getAttribute(actionNode, "dispatcher"));
		String requestMethodName   			= parseUtil.getAttribute(actionNode, "request-method");
		boolean resolvedView				= Boolean.valueOf(parseUtil.getAttribute(actionNode, "resolved-view"));
		boolean renderedView				= Boolean.valueOf(parseUtil.getAttribute(actionNode, "rendered-view"));
		int responseStatus					= Integer.parseInt(parseUtil.getAttribute(actionNode, "response-status"));
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
	protected void loadActionDependencies(Element actionNode, ActionBuilder builder){
		
		loadAcceptRequestTypes(parseUtil.getElements(actionNode,
				WebXMLBrutosConstants.XML_BRUTOS_ACCEPT_REQUEST_TYPE), builder);

		loadResponseTypes(parseUtil.getElements(actionNode,
				WebXMLBrutosConstants.XML_BRUTOS_RESPONSE_TYPE), builder);
		
		loadResponseErrors(parseUtil.getElement(actionNode,
				WebXMLBrutosConstants.XML_BRUTOS_RESPONSE_ERRORS), builder);
		
		super.loadActionDependencies(actionNode, builder);
	}
	
	protected void loadAcceptRequestTypes(NodeList nodeList,
			Object builder) {

		for (int i = 0; i < nodeList.getLength(); i++) {
			Element c = (Element) nodeList.item(i);
			loadAcceptRequestType(c, builder);
		}

	}

	protected void loadAcceptRequestType(Element element, Object builder) {
		
		String name 			= StringUtil.adjust(parseUtil.getAttribute(element, "name"));
		String text 			= StringUtil.adjust(element.getTextContent());
		String mediaTypeName	= name == null? text : name;
		MediaType mediaType 	= MediaType.valueOf(mediaTypeName);
		
		if(mediaType == null){
			throw new BrutosException("invalid media type: " + mediaTypeName );
		}
		
		if(builder instanceof WebControllerBuilder){
			((WebControllerBuilder)builder).addRequestType(mediaType);
		}
		
		if(builder instanceof WebActionBuilder){
			((WebActionBuilder)builder).addRequestType(mediaType);
		}
		
	}

	protected void loadResponseTypes(NodeList nodeList,
			Object builder) {

		for (int i = 0; i < nodeList.getLength(); i++) {
			Element c = (Element) nodeList.item(i);
			loadResponseType(c, builder);
		}

	}

	protected void loadResponseType(Element element, Object builder) {
		
		String name 			= StringUtil.adjust(parseUtil.getAttribute(element, "name"));
		String text 			= StringUtil.adjust(element.getTextContent());
		String mediaTypeName	= name == null? text : name;
		MediaType mediaType 	= MediaType.valueOf(mediaTypeName);
		
		if(mediaType == null){
			throw new BrutosException("invalid media type: " + mediaTypeName );
		}
		
		if(builder instanceof WebControllerBuilder){
			((WebControllerBuilder)builder).addResponseType(mediaType);
		}
		
		if(builder instanceof WebActionBuilder){
			((WebActionBuilder)builder).addResponseType(mediaType);
		}
		
	}

	protected void loadResponseErrors(Element e,
			Object builder) {

		loadResponseError(e, builder);
		
		NodeList nodeList = 
				parseUtil.getElements(e, WebXMLBrutosConstants.XML_BRUTOS_RESPONSE_ERROR);
				
		for (int i = 0; i < nodeList.getLength(); i++) {
			Element c = (Element) nodeList.item(i);
			loadResponseError(c, builder);
		}

	}

	protected void loadResponseError(Element element, Object builder) {
		
		int code 					= Integer.parseInt(parseUtil.getAttribute(element, "code"));
		Class<?> target 			= this.getClass(parseUtil.getAttribute(element, "target"));
		String reason 				= parseUtil.getAttribute(element, "reason");
		String view 				= parseUtil.getAttribute(element, "view");
		String name 				= parseUtil.getAttribute(element, "name");
		DispatcherType dispatcher 	= DispatcherType.valueOf(parseUtil.getAttribute(element, "dispatcher"));
		boolean resolved 			= Boolean.valueOf(parseUtil.getAttribute(element, "resolved-view"));
		boolean rendered 			= Boolean.valueOf(parseUtil.getAttribute(element, "rendered-view"));
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
