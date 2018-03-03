package org.brandao.brutos.web;

import org.brandao.brutos.ActionType;
import org.brandao.brutos.ComponentRegistry;
import org.brandao.brutos.DispatcherType;
import org.brandao.brutos.xml.XMLComponentDefinitionReader;
import org.w3c.dom.Element;

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

		
		if (defaultAction != null){
			controllerBuilder.setDefaultAction(defaultAction);
		}

		this.loadControllerDependencies(controller, controllerBuilder);

	}
	
}
