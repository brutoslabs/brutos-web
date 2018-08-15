/*
 * Brutos Web MVC http://www.brutosframework.com.br/
 * Copyright (C) 2009-2017 Afonso Brandao. (afonso.rbn@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.brandao.brutos.web;

import org.brandao.brutos.ActionBuilder;
import org.brandao.brutos.ActionType;
import org.brandao.brutos.ComponentRegistry;
import org.brandao.brutos.ControllerBuilder;
import org.brandao.brutos.DispatcherType;
import org.brandao.brutos.io.Resource;
import org.brandao.brutos.io.ResourceLoader;
import org.brandao.brutos.xml.XMLBrutosConstants;
import org.brandao.brutos.xml.XMLComponentDefinitionReader;
import org.brandao.brutos.xml.XMLParseUtil;
import org.w3c.dom.Element;

/**
 * 
 * @author Brandao
 *
 */
public class WebXMLComponentDefinitionReader 
	extends XMLComponentDefinitionReader{

	protected final XMLParseUtil webParseUtil;
	
	public WebXMLComponentDefinitionReader(ComponentRegistry componenetRegistry) {
		super(componenetRegistry);
		this.webParseUtil = new XMLParseUtil(WebXMLBrutosConstants.XML_BRUTOS_WEB_NAMESPACE);
	}

	public void loadDefinitions(Resource resource) {
		Element document = 
			this.buildDocument(
				resource, 
				new String[] {
					ResourceLoader.CLASSPATH_URL_PREFIX + XMLBrutosConstants.XML_BRUTOS_CONTROLLER_SCHEMA,
					ResourceLoader.CLASSPATH_URL_PREFIX	+ XMLBrutosConstants.XML_BRUTOS_CONTEXT_SCHEMA, 
					ResourceLoader.CLASSPATH_URL_PREFIX	+ WebXMLBrutosConstants.XML_WEB_BRUTOS_SCHEMA
				}
			);
		this.buildComponents(document, resource);
	}
	
	protected void loadController(Element controller) {

		WebControllerRegistry webControllerRegistry = (WebControllerRegistry)componentRegistry;
	
		String id 							= parseUtil.getAttribute(controller, "id");
		ActionType actionType 				= ActionType.valueOf(parseUtil.getAttribute(controller, "action-strategy"));
		DispatcherType dispatcher 			= WebDispatcherType.valueOf(parseUtil.getAttribute(controller, "dispatcher"));
		String requestMethodName   			= webParseUtil.getAttribute(controller, "request-method", "get");
		String view 						= parseUtil.getAttribute(controller, "view");
		boolean resolvedView				= parseUtil.getBooleanAttribute(controller, "resolved-view");
		boolean renderedView				= parseUtil.getBooleanAttribute(controller, "rendered-view");
		String name 						= parseUtil.getAttribute(controller, "name");
		String clazzName 					= parseUtil.getAttribute(controller, "class");
		String actionId 					= parseUtil.getAttribute(controller, "action-id");
		String defaultAction 				= parseUtil.getAttribute(controller, "default-action");
		int responseStatus					= webParseUtil.getIntAttribute(controller, "response-status", 200);
		Class<?> clazz 						= this.getClass(clazzName);
		RequestMethodType requestMethodType	= requestMethodName == null? null : RequestMethodType.valueOf(requestMethodName.toUpperCase());
		
		
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
		DispatcherType dispatcher 			= WebDispatcherType.valueOf(parseUtil.getAttribute(actionNode, "dispatcher"));
		String requestMethodName   			= webParseUtil.getAttribute(actionNode, "request-method", "get");
		boolean resolvedView				= Boolean.valueOf(parseUtil.getAttribute(actionNode, "resolved-view"));
		boolean renderedView				= Boolean.valueOf(parseUtil.getAttribute(actionNode, "rendered-view"));
		int responseStatus					= webParseUtil.getIntAttribute(actionNode, "response-status", 200);
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
		
		int code 					= webParseUtil.getIntAttribute(element, "code", 500);
		Class<?> target 			= this.getClass(parseUtil.getAttribute(element, "target"));
		String reason 				= webParseUtil.getAttribute(element, "reason");
		String view 				= parseUtil.getAttribute(element, "view");
		String name 				= parseUtil.getAttribute(element, "name");
		DispatcherType dispatcher 	= WebDispatcherType.valueOf(parseUtil.getAttribute(element, "dispatcher"));
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
