package org.brandao.brutos.web;

import org.brandao.brutos.ActionType;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.ComponentRegistry;
import org.brandao.brutos.ControllerBuilder;
import org.brandao.brutos.DispatcherType;
import org.brandao.brutos.mapping.StringUtil;
import org.brandao.brutos.xml.XMLBrutosConstants;
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
		boolean renderedView				= Boolean.valueOf(parseUtil.getAttribute(controller, "resolved-view"));
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
				WebXMLBrutosConstants.XML_BRUTOS_ACCEPT_REQUEST_TYPES), controllerBuilder);
		
		super.loadControllerDependencies(controller, controllerBuilder);
		
	}
	
	protected void loadAcceptRequestTypes(NodeList nodeList,
			ControllerBuilder controllerBuilder) {

		for (int i = 0; i < nodeList.getLength(); i++) {
			Element c = (Element) nodeList.item(i);
			loadAcceptRequestType(c, controllerBuilder);
		}

	}

	protected void loadAcceptRequestType(Element element, ControllerBuilder controllerBuilder) {
		
		String name 			= StringUtil.adjust(parseUtil.getAttribute(element, "name"));
		String text 			= StringUtil.adjust(element.getTextContent());
		String mediaTypeName	= name == null? text : name;
		MediaType mediaType 	= MediaType.valueOf(mediaTypeName);
		
		if(mediaType == null){
			throw new BrutosException("invalid media type: " + mediaTypeName );
		}
		
		controllerBuilder.addRequestType(mediaType);
		
	}
	
}
