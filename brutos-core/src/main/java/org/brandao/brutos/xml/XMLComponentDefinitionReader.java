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

package org.brandao.brutos.xml;

import java.util.ArrayList;
import java.util.List;

import org.brandao.brutos.*;
import org.brandao.brutos.io.Resource;
import org.brandao.brutos.io.ResourceLoader;
import org.brandao.brutos.mapping.StringUtil;
import org.brandao.brutos.type.Type;
import org.brandao.brutos.validator.RestrictionRules;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * 
 * @author Brandao
 */
public class XMLComponentDefinitionReader extends ContextDefinitionReader {

	protected final List<Resource> blackList;

	protected final XMLParseUtil parseUtil;

	public XMLComponentDefinitionReader(ComponentRegistry componenetRegistry) {
		super(componenetRegistry);
		this.parseUtil = new XMLParseUtil(
				XMLBrutosConstants.XML_BRUTOS_CONTROLLER_NAMESPACE);
		this.blackList = new ArrayList<Resource>();
	}

	public void loadDefinitions(Resource resource) {
		Element document = this.buildDocument(resource, new String[] {
				ResourceLoader.CLASSPATH_URL_PREFIX
						+ XMLBrutosConstants.XML_BRUTOS_CONTROLLER_SCHEMA,
				ResourceLoader.CLASSPATH_URL_PREFIX
						+ XMLBrutosConstants.XML_BRUTOS_CONTEXT_SCHEMA });
		this.buildComponents(document, resource);
	}

	protected void buildComponents(Element document, Resource resource) {
		super.buildComponents(document, resource);

		loadInterceptors(parseUtil.getElement(document,
				XMLBrutosConstants.XML_BRUTOS_INTERCEPTORS));

		loadControllers(parseUtil.getElement(document,
				XMLBrutosConstants.XML_BRUTOS_CONTROLLERS));

		loadControllers(parseUtil.getElements(document,
				XMLBrutosConstants.XML_BRUTOS_CONTROLLER));

		loadImporters(parseUtil.getElements(document,
				XMLBrutosConstants.XML_BRUTOS_IMPORTER), resource);

	}

	protected void loadImporters(NodeList list, Resource resource) {

		for (int i = 0; i < list.getLength(); i++) {
			Element c = (Element) list.item(i);
			String dependencyName = parseUtil.getAttribute(c, "resource");

			if (dependencyName != null && dependencyName.length() != 0) {

				try {
					Resource dependencyResource = resource
							.getRelativeResource(dependencyName);

					if (blackList.contains(dependencyResource))
						continue;
					else
						blackList.add(dependencyResource);

					Element document = super
							.buildDocument(
									dependencyResource,
									new String[] { ResourceLoader.CLASSPATH_URL_PREFIX
											+ XMLBrutosConstants.XML_BRUTOS_CONTROLLER_SCHEMA });
					this.buildComponents(document, dependencyResource);
				} catch (BrutosException ex) {
					throw ex;
				} catch (Throwable ex) {
					throw new BrutosException(ex);
				}
			}
		}
	}

	protected void loadInterceptors(Element e) {

		if (e == null)
			return;

		NodeList list = parseUtil.getElements(e,
				XMLBrutosConstants.XML_BRUTOS_INTERCEPTOR);

		loadInterceptor(list);

		NodeList listStack = parseUtil.getElements(e,
				XMLBrutosConstants.XML_BRUTOS_INTERCEPTOR_STACK);

		loadInterceptorStack(listStack);

	}

	protected void loadInterceptor(NodeList list) {

		if (list == null)
			return;

		for (int i = 0; i < list.getLength(); i++) {
			Element c = (Element) list.item(i);

			String name = parseUtil.getAttribute(c, "name");
			String clazzName = parseUtil.getAttribute(c, "class");
			Boolean isDefault = Boolean.valueOf(parseUtil.getAttribute(c,
					"default"));
			Class<?> clazz;

			try {
				clazz = Class.forName(clazzName, true, Thread.currentThread()
						.getContextClassLoader());
			} catch (Exception ex) {
				throw new BrutosException(ex);
			}

			InterceptorBuilder interceptorBuilder = componentRegistry
					.registerInterceptor(name, clazz, isDefault.booleanValue());

			NodeList listParam = parseUtil.getElements(c,
					XMLBrutosConstants.XML_BRUTOS_PARAM);

			for (int k = 0; k < listParam.getLength(); k++) {
				Element paramNode = (Element) listParam.item(k);

				String paramName = parseUtil.getAttribute(paramNode, "name");
				String paramValue = parseUtil.getAttribute(paramNode, "value");

				paramValue = paramValue == null ? paramNode.getTextContent()
						: paramValue;

				interceptorBuilder.addParameter(paramName, paramValue);
			}

		}
	}

	protected void loadInterceptorStack(NodeList list) {

		if (list == null)
			return;

		for (int i = 0; i < list.getLength(); i++) {
			Element c = (Element) list.item(i);

			String name = parseUtil.getAttribute(c, "name");
			Boolean isDefault = Boolean.valueOf(parseUtil.getAttribute(c,
					"default"));

			InterceptorStackBuilder interceptorStackBuilder = componentRegistry
					.registerInterceptorStack(name, isDefault.booleanValue());

			NodeList listInterceptorRef = parseUtil.getElements(c,
					XMLBrutosConstants.XML_BRUTOS_INTERCEPTOR_REF);

			for (int j = 0; j < listInterceptorRef.getLength(); j++) {
				Element interceptorRefNode = (Element) listInterceptorRef
						.item(j);

				String interceptorRefName = parseUtil.getAttribute(
						interceptorRefNode, "name");

				interceptorStackBuilder.addInterceptor(interceptorRefName);

				NodeList listParam = parseUtil.getElements(interceptorRefNode,
						XMLBrutosConstants.XML_BRUTOS_PARAM);

				for (int k = 0; k < listParam.getLength(); k++) {
					Element paramNode = (Element) listParam.item(k);

					String paramName = parseUtil
							.getAttribute(paramNode, "name");
					String paramValue = parseUtil.getAttribute(paramNode,
							"value");

					paramValue = paramValue == null ? paramNode
							.getTextContent() : paramValue;

					interceptorStackBuilder.addParameter(interceptorRefName
							+ "." + paramName, paramValue);
				}

			}
		}
	}

	protected void loadControllers(Element controllersNode) {

		if (controllersNode == null)
			return;

		NodeList controllers = parseUtil.getElements(controllersNode,
				XMLBrutosConstants.XML_BRUTOS_CONTROLLER);

		loadControllers(controllers);
	}

	protected void loadControllers(NodeList controllers) {
		for (int i = 0; i < controllers.getLength(); i++) {
			Element controllerNode = (Element) controllers.item(i);
			loadController(controllerNode);
		}
	}

	protected void loadController(Element controller) {

		String id 							= parseUtil.getAttribute(controller, "id");
		ActionType actionType 				= ActionType.valueOf(parseUtil.getAttribute(controller, "action-strategy"));
		DispatcherType dispatcher 			= DispatcherType.valueOf(parseUtil.getAttribute(controller, "dispatcher"));
		String view 						= parseUtil.getAttribute(controller, "view");
		boolean resolvedView				= parseUtil.getBooleanAttribute(controller, "resolved-view");
		boolean renderedView				= parseUtil.getBooleanAttribute(controller, "rendered-view");
		String name 						= parseUtil.getAttribute(controller, "name");
		String clazzName 					= parseUtil.getAttribute(controller, "class");
		String actionId 					= parseUtil.getAttribute(controller, "action-id");
		String defaultAction 				= parseUtil.getAttribute(controller, "default-action");
		Class<?> clazz 						= this.getClass(clazzName);
		
		ControllerBuilder controllerBuilder = 
				componentRegistry.registerController(
						id, 
						renderedView? view : null, 
						dispatcher, 
						renderedView? resolvedView : true, 
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

	protected void loadControllerDependencies(Element controller, ControllerBuilder controllerBuilder){
		
		loadAliasController(parseUtil.getElements(controller,
				XMLBrutosConstants.XML_BRUTOS_ALIAS), controllerBuilder);

		addInterceptorController(parseUtil.getElements(controller,
				XMLBrutosConstants.XML_BRUTOS_INTERCEPTOR_REF),
				controllerBuilder);

		addBeans(parseUtil.getElements(controller,
				XMLBrutosConstants.XML_BRUTOS_BEAN), controllerBuilder);

		addProperties(parseUtil.getElements(controller,
				XMLBrutosConstants.XML_BRUTOS_CONTROLLER_PROPERTY),
				controllerBuilder);

		addActions(parseUtil.getElements(controller,
				XMLBrutosConstants.XML_BRUTOS_ACTION), controllerBuilder);

		loadAcceptRequestTypes(parseUtil.getElements(controller,
				XMLBrutosConstants.XML_BRUTOS_ACCEPT_REQUEST_TYPE), controllerBuilder);

		loadResponseTypes(parseUtil.getElements(controller,
				XMLBrutosConstants.XML_BRUTOS_RESPONSE_TYPE), controllerBuilder);
		
		addThrowSafe(parseUtil.getElements(controller,
				XMLBrutosConstants.XML_BRUTOS_THROWS), controllerBuilder);

	}
	
	protected void loadAliasController(NodeList aliasNode,
			ControllerBuilder controllerBuilder) {

		for (int i = 0; i < aliasNode.getLength(); i++) {
			Element c = (Element) aliasNode.item(i);
			controllerBuilder.addAlias(c.getTextContent());
		}

	}

	protected void addInterceptorController(NodeList interceptorList,
			ControllerBuilder controllerBuilder) {

		for (int j = 0; j < interceptorList.getLength(); j++) {
			Element interceptorRefNode = (Element) interceptorList.item(j);

			String interceptorRefName = parseUtil.getAttribute(
					interceptorRefNode, "name");

			InterceptorBuilder interceptorBuilder = controllerBuilder
					.addInterceptor(interceptorRefName);

			NodeList listParam = parseUtil.getElements(interceptorRefNode,
					XMLBrutosConstants.XML_BRUTOS_PARAM);

			for (int k = 0; k < listParam.getLength(); k++) {
				Element paramNode = (Element) listParam.item(k);

				String paramName = parseUtil.getAttribute(paramNode, "name");
				String paramValue = parseUtil.getAttribute(paramNode, "value");

				paramValue = paramValue == null ? paramNode.getTextContent()
						: paramValue;

				interceptorBuilder.addParameter(paramName, paramValue);
			}
		}
	}

	protected void addBeans(NodeList beanList, ControllerBuilder controllerBuilder) {

		for (int k = 0; k < beanList.getLength(); k++) {
			Element beanNode = (Element) beanList.item(k);
			addBean(beanNode, controllerBuilder, null);
		}

	}

	protected void addBean(Element beanNode, ControllerBuilder controllerBuilder,
			String propertyName) {

		String name = parseUtil.getAttribute(beanNode, "name");
		String separator = parseUtil.getAttribute(beanNode, "separator");
		String indexFormat = parseUtil.getAttribute(beanNode, "index-format");
		String factory = parseUtil.getAttribute(beanNode, "factory");
		String methodFactory = parseUtil.getAttribute(beanNode,
				"method-factory");
		String target = parseUtil.getAttribute(beanNode, "target");

		Class<?> clazz = null;
		try {
			clazz = Class.forName(target, true, Thread.currentThread()
					.getContextClassLoader());
		} catch (Exception ex) {
			throw new BrutosException(ex);
		}

		BeanBuilder beanBuilder = propertyName != null ? controllerBuilder
				.buildProperty(propertyName, clazz) : controllerBuilder
				.buildMappingBean(name, clazz);

		beanBuilder.setFactory(factory);
		beanBuilder.setMethodfactory(methodFactory);
		beanBuilder.setSeparator(separator);
		beanBuilder.setIndexFormat(indexFormat);

		buildBean(beanNode, beanBuilder);

	}

	protected void addBean(Element beanNode, BeanBuilder bean,
			ConstructorBuilder constructorBuilder, String name,
			String propertyName, boolean key, String keyName, boolean element,
			String elementName) {

		// String name = parseUtil.getAttribute(beanNode,"name" );
		String separator = parseUtil.getAttribute(beanNode, "separator");
		String indexFormat = parseUtil.getAttribute(beanNode, "index-format");
		int maxItens     = parseUtil.getIntAttribute(beanNode, "max-itens");
		String factory = parseUtil.getAttribute(beanNode, "factory");
		String methodFactory = parseUtil.getAttribute(beanNode,
				"method-factory");
		String target = parseUtil.getAttribute(beanNode, "target");

		Class<?> clazz = null;
		try {
			clazz = Class.forName(target, true, Thread.currentThread()
					.getContextClassLoader());
		} catch (Exception ex) {
			throw new BrutosException(ex);
		}

		BeanBuilder beanBuilder;

		if (key)
			beanBuilder = bean.buildKey(keyName, clazz);
		else if (element)
			beanBuilder = bean.buildElement(elementName, clazz);
		else
			beanBuilder = constructorBuilder != null ? constructorBuilder
					.buildConstructorArg(name, clazz) : bean.buildProperty(
					name, propertyName, clazz);

		beanBuilder.setFactory(factory);
		beanBuilder.setMethodfactory(methodFactory);
		beanBuilder.setSeparator(separator);
		beanBuilder.setIndexFormat(indexFormat);

		if(maxItens >= 0){
			beanBuilder.setMaxItens(maxItens);
		}
		
		buildBean(beanNode, beanBuilder);

	}

	protected void addBean(String name, Element beanNode,
			ParametersBuilder parametersBuilder, Class<?> paramType) {

		String separator = parseUtil.getAttribute(beanNode, "separator");
		int maxItens     = parseUtil.getIntAttribute(beanNode, "max-itens");
		String indexFormat = parseUtil.getAttribute(beanNode, "index-format");
		String factory = parseUtil.getAttribute(beanNode, "factory");
		String methodFactory = parseUtil.getAttribute(beanNode,
				"method-factory");
		String target = parseUtil.getAttribute(beanNode, "target");

		Class<?> clazz = null;
		try {
			clazz = ClassUtil.get(target);
		} catch (Throwable ex) {
			throw new BrutosException(ex);
		}

		BeanBuilder beanBuilder = parametersBuilder.buildParameter(name,
				paramType, clazz);

		beanBuilder.setFactory(factory);
		beanBuilder.setMethodfactory(methodFactory);
		beanBuilder.setSeparator(separator);
		beanBuilder.setIndexFormat(indexFormat);
		
		if(maxItens >= 0){
			beanBuilder.setMaxItens(maxItens);
		}
		
		buildBean(beanNode, beanBuilder);

	}

	protected void buildBean(Element beanNode, BeanBuilder beanBuilder) {
		buildConstructorBean(parseUtil.getElements(beanNode,
				XMLBrutosConstants.XML_BRUTOS_BEAN_CONSTRUCTOR_ARG),
				beanBuilder);

		buildPropertiesBean(parseUtil.getElements(beanNode,
				XMLBrutosConstants.XML_BRUTOS_BEAN_PROPERY), beanBuilder);

		Element keyNode = parseUtil.getElement(beanNode,
				XMLBrutosConstants.XML_BRUTOS_MAP_KEY);

		if (keyNode != null)
			buildKeyCollection(keyNode, beanBuilder);

		Element elementNode = parseUtil.getElement(beanNode,
				XMLBrutosConstants.XML_BRUTOS_COLLECTION_ELEMENT);

		if (elementNode != null)
			buildElementCollection(elementNode, beanBuilder);

	}

	protected void buildAny(Element anyNode, GenericBuilder builder) {

		String enumPropertyName = parseUtil.getAttribute(anyNode,
				"enum-property");
		EnumerationType enumProperty = EnumerationType
				.valueOf(enumPropertyName);
		String temporalProperty = parseUtil.getAttribute(anyNode,
				"temporal-property");
		String bean = parseUtil.getAttribute(anyNode, "bean");
		String scopeName = parseUtil.getAttribute(anyNode, "scope");
		ScopeType scope = ScopeType.valueOf(scopeName);
		String factoryName = parseUtil.getAttribute(anyNode, "type-def");
		String typeName = parseUtil.getAttribute(anyNode, "type");
		Type factory = null;
		Class<?> type = null;

		try {
			if (factoryName != null) {
				factory = (Type) ClassUtil.getInstance(ClassUtil
						.get(factoryName));
			}

			if (typeName != null)
				type = ClassUtil.get(typeName);

		} catch (Exception ex) {
			throw new BrutosException(ex);
		}

		MetaBeanBuilder metaBeanBuilder = builder.buildMetaBean(bean, scope,
				enumProperty, temporalProperty, type, factory);

		NodeList metaValueList = parseUtil.getElements(anyNode, "meta-value");

		for (int i = 0; i < metaValueList.getLength(); i++) {
			Element metaValue = (Element) metaValueList.item(i);

			buildMetaBean(metaValue, metaBeanBuilder);

		}

	}

	protected void buildMetaBean(Element metaValue,
			MetaBeanBuilder metaBeanBuilder) {

		String bean = parseUtil.getAttribute(metaValue, "bean");
		String value = parseUtil.getAttribute(metaValue, "value");
		Element mappingRef = parseUtil.getElement(metaValue, "ref");
		Element beanNode = parseUtil.getElement(metaValue, "bean");

		if (mappingRef != null) {
			bean = parseUtil.getAttribute(mappingRef, "bean");
			String enumPropertyName = parseUtil.getAttribute(mappingRef,
					"enum-property");
			EnumerationType enumProperty = EnumerationType
					.valueOf(enumPropertyName);
			String temporalProperty = parseUtil.getAttribute(mappingRef,
					"temporal-property");
			String scopeName = parseUtil.getAttribute(mappingRef, "scope");
			ScopeType scope = ScopeType.valueOf(scopeName);
			String factoryName = parseUtil.getAttribute(mappingRef, "type-def");
			String typeName = parseUtil.getAttribute(mappingRef, "type");
			Type factory = null;
			Class<?> type = null;

			try {
				if (factoryName != null) {
					factory = (Type) ClassUtil.getInstance(ClassUtil
							.get(factoryName));
				}

				if (typeName != null)
					type = ClassUtil.get(typeName);

			} catch (Exception ex) {
				throw new BrutosException(ex);
			}

			metaBeanBuilder.addMetaValue(value, enumProperty, temporalProperty,
					null, scope, factory, type);
		} else if (beanNode != null)
			this.addBean(value, beanNode, metaBeanBuilder);
		else
			metaBeanBuilder.addMetaValue(value, bean);

	}

	protected void addBean(Object value, Element beanNode,
			MetaBeanBuilder metaBeanBuilder) {

		String separator = parseUtil.getAttribute(beanNode, "separator");
		String indexFormat = parseUtil.getAttribute(beanNode, "index-format");
		String factory = parseUtil.getAttribute(beanNode, "factory");
		String methodFactory = parseUtil.getAttribute(beanNode,
				"method-factory");
		String target = parseUtil.getAttribute(beanNode, "target");

		Class<?> clazz = null;
		try {
			clazz = Class.forName(target, true, Thread.currentThread()
					.getContextClassLoader());
		} catch (Exception ex) {
			throw new BrutosException(ex);
		}

		BeanBuilder beanBuilder = metaBeanBuilder.buildMetaValue(value, clazz);

		beanBuilder.setFactory(factory);
		beanBuilder.setMethodfactory(methodFactory);
		beanBuilder.setSeparator(separator);
		beanBuilder.setIndexFormat(indexFormat);

		buildBean(beanNode, beanBuilder);
	}

	protected void buildConstructorBean(NodeList consList, BeanBuilder beanBuilder) {

		ConstructorBuilder constructor = beanBuilder.buildConstructor();

		for (int k = 0; k < consList.getLength(); k++) {
			Element conNode = (Element) consList.item(k);

			String enumPropertyName 		= parseUtil.getAttribute(conNode, "enum-property");
			EnumerationType enumProperty 	= EnumerationType.valueOf(enumPropertyName);
			String value 					= parseUtil.getAttribute(conNode, "value");
			String temporalProperty 		= parseUtil.getAttribute(conNode, "temporal-property");
			String bean 					= parseUtil.getAttribute(conNode, "bean");
			boolean mapping 				= Boolean.valueOf(parseUtil.getAttribute(conNode, "mapping"));

			String scopeName 				= parseUtil.getAttribute(conNode, "scope");
			ScopeType scope 				= ScopeType.valueOf(scopeName);
			String factoryName 				= parseUtil.getAttribute(conNode, "type-def");
			String typeName 				= parseUtil.getAttribute(conNode, "type");
			FetchType fetchType				= FetchType.valueOf(parseUtil.getAttribute(conNode, "fetch-type"));
			Type factory 					= null;
			boolean nullable 				= false;
			Class<?> type 					= null;

			Element anyNode 		= parseUtil.getElement(conNode, "any");
			Element mappingRef 		= parseUtil.getElement(conNode, "ref");
			Element beanNode 		= parseUtil.getElement(conNode, "bean");
			Element valueNode 		= parseUtil.getElement(conNode, "value");
			Element validatorNode 	= parseUtil.getElement(conNode, "validator");
			Element nullNode 		= parseUtil.getElement(conNode, "null");
			
			if (mappingRef != null) {
				enumProperty 		= EnumerationType.valueOf(parseUtil.getAttribute(mappingRef, "enum-property"));
				value 				= parseUtil.getAttribute(mappingRef, "value");
				temporalProperty 	= parseUtil.getAttribute(mappingRef, "temporal-property");
				bean 				= parseUtil.getAttribute(mappingRef, "bean");
				mapping 			= Boolean.valueOf(parseUtil.getAttribute(mappingRef, "mapping"));
				scope 				= ScopeType.valueOf(parseUtil.getAttribute(mappingRef, "scope"));
				factoryName 		= parseUtil.getAttribute(mappingRef, "type-def");
				validatorNode 		= parseUtil.getElement(mappingRef, "validator");
				
			}
			else
			if(beanNode != null){
				addBean(beanNode, beanBuilder, constructor, bean, null, false,
						null, false, null);
				continue;
			}
			else
			if(valueNode != null){
				value = valueNode.getTextContent();
			}
			else
			if(nullNode != null)
				nullable = true;

			try{
				if(factoryName != null) {
					factory = (Type) ClassUtil.getInstance(ClassUtil
							.get(factoryName));
				}

				if (typeName != null)
					type = ClassUtil.get(typeName);

			} catch (Exception ex) {
				throw new BrutosException(ex);
			}

			ConstructorArgBuilder constructorBuilder = constructor
					.addContructorArg(bean, enumProperty, temporalProperty,
							mapping ? bean : null, scope, value, nullable,
							anyNode != null, factory, type);

			constructorBuilder.setFetchType(fetchType);
			
			if (anyNode != null)
				this.buildAny(anyNode, constructorBuilder);

			addValidator(validatorNode, constructorBuilder);
		}

	}

	protected void buildPropertiesBean(NodeList consList, BeanBuilder beanBuilder) {

		for (int k = 0; k < consList.getLength(); k++) {
			Element propNode = (Element) consList.item(k);

			String propertyName 			= parseUtil.getAttribute(propNode, "name");
			EnumerationType enumProperty	= EnumerationType.valueOf(parseUtil.getAttribute(propNode, "enum-property"));
			String value 					= parseUtil.getAttribute(propNode, "value");
			String temporalProperty 		= parseUtil.getAttribute(propNode, "temporal-property");
			String bean 					= parseUtil.getAttribute(propNode, "bean");
			boolean mapping 				= Boolean.valueOf(parseUtil.getAttribute(propNode, "mapping"));
			ScopeType scope 				= ScopeType.valueOf(parseUtil.getAttribute(propNode, "scope"));
			String factoryName 				= parseUtil.getAttribute(propNode, "type-def");
			FetchType fetchType				= FetchType.valueOf(parseUtil.getAttribute(propNode, "fetch-type"));
			Type factory 					= null;
			boolean nullable 				= false;

			Element anyNode 		= parseUtil.getElement(propNode, "any");
			Element mappingRef		= parseUtil.getElement(propNode, "ref");
			Element beanNode 		= parseUtil.getElement(propNode, "bean");
			Element valueNode 		= parseUtil.getElement(propNode, "value");
			Element validatorNode	= parseUtil.getElement(propNode, "validator");
			Element nullNode 		= parseUtil.getElement(propNode, "null");
			
			if(mappingRef != null){
				enumProperty 		= EnumerationType.valueOf(parseUtil.getAttribute(mappingRef, "enum-property"));
				value				= parseUtil.getAttribute(mappingRef, "value");
				temporalProperty	= parseUtil.getAttribute(mappingRef, "temporal-property");
				bean 				= parseUtil.getAttribute(mappingRef, "bean");
				mapping 			= Boolean.valueOf(parseUtil.getAttribute(mappingRef, "mapping"));
				scope 				= ScopeType.valueOf(parseUtil.getAttribute(mappingRef, "scope"));
				factoryName 		= parseUtil.getAttribute(mappingRef, "type-def");
				validatorNode 		= parseUtil.getElement(mappingRef, "validator");
			}
			else
			if(beanNode != null){
				addBean(beanNode, beanBuilder, null, bean, propertyName, false,
						null, false, null);
				continue;
			}
			else
			if(valueNode != null){
				value = valueNode.getTextContent();
			}
			else
			if(nullNode != null)
				nullable = true;

			try{
				if (factoryName != null)
					factory = (Type) ClassUtil.getInstance(ClassUtil
							.get(factoryName));
			}
			catch (Exception ex) {
				throw new BrutosException(ex);
			}

			PropertyBuilder propertyBuilder = beanBuilder.addProperty(bean,
					propertyName, enumProperty, temporalProperty,
					mapping ? bean : null, scope, value, nullable,
					anyNode != null, null, factory);

			propertyBuilder.setFetchType(fetchType);
			
			if (anyNode != null)
				this.buildAny(anyNode, propertyBuilder);

			addValidator(validatorNode, propertyBuilder);
		}
	}

	protected void addProperties(NodeList properrties,
			ControllerBuilder controllerBuilder) {

		for (int k = 0; k < properrties.getLength(); k++) {
			Element propNode = (Element) properrties.item(k);
			buildPropertyController(propNode, controllerBuilder);
		}
	}

	protected void buildPropertyController(Element propNode,
			ControllerBuilder controllerBuilder) {

		String propertyName 			= parseUtil.getAttribute(propNode, "name");
		EnumerationType enumProperty	= EnumerationType.valueOf(parseUtil.getAttribute(propNode, "enum-property"));
		String value 					= parseUtil.getAttribute(propNode, "value");
		String temporalProperty 		= parseUtil.getAttribute(propNode, "temporal-property");
		String bean 					= parseUtil.getAttribute(propNode, "bean");
		boolean mapping 				= Boolean.valueOf(parseUtil.getAttribute(propNode, "mapping"));
		ScopeType scope 				= ScopeType.valueOf(parseUtil.getAttribute(propNode, "scope"));
		String factoryName 				= parseUtil.getAttribute(propNode, "type-def");
		FetchType fetchType				= FetchType.valueOf(parseUtil.getAttribute(propNode, "fetch-type"));
		Type factory 					= null;
		boolean nullable 				= false;

		Element anyNode 		= parseUtil.getElement(propNode, "any");
		Element mappingRef 		= parseUtil.getElement(propNode, "ref");
		Element beanNode 		= parseUtil.getElement(propNode, "bean");
		Element valueNode 		= parseUtil.getElement(propNode, "value");
		Element validatorNode 	= parseUtil.getElement(propNode, "validator");
		Element nullNode 		= parseUtil.getElement(propNode, "null");
		
		if (mappingRef != null) {
			enumProperty = EnumerationType.valueOf(parseUtil.getAttribute(mappingRef, "enum-property"));
			value = parseUtil.getAttribute(mappingRef, "value");
			temporalProperty = parseUtil.getAttribute(mappingRef,"temporal-property");
			bean = parseUtil.getAttribute(mappingRef, "bean");
			mapping = Boolean.valueOf(parseUtil.getAttribute(mappingRef, "mapping"));
			scope = ScopeType.valueOf(parseUtil.getAttribute(mappingRef, "scope"));
			factoryName = parseUtil.getAttribute(mappingRef, "type-def");
			validatorNode = parseUtil.getElement(mappingRef, "validator");
		}
		else
		if(beanNode != null){
			addBean(beanNode, controllerBuilder, propertyName);
			return;
		}
		else
		if(valueNode != null){
			value = valueNode.getTextContent();
		}
		else
		if (nullNode != null)
			nullable = true;

		try{
			if(factoryName != null)
				factory = (Type) ClassUtil.getInstance(ClassUtil.get(factoryName));
		}
		catch(Exception ex) {
			throw new BrutosException(ex);
		}

		PropertyBuilder propertyBuilder = controllerBuilder.addProperty(
				propertyName, bean, scope, enumProperty, temporalProperty,
				mapping ? bean : null, value, nullable, anyNode != null, null,
				factory);

		propertyBuilder.setFetchType(fetchType);
		
		if (anyNode != null){
			this.buildAny(anyNode, propertyBuilder);
		}

		addValidator(validatorNode, propertyBuilder);

	}

	protected void buildKeyCollection(Element conNode, BeanBuilder beanBuilder) {

		String enumPropertyName = parseUtil.getAttribute(conNode,
				"enum-property");
		EnumerationType enumProperty = EnumerationType
				.valueOf(enumPropertyName);
		String value = parseUtil.getAttribute(conNode, "value");
		String temporalProperty = parseUtil.getAttribute(conNode,
				"temporal-property");
		String bean = parseUtil.getAttribute(conNode, "bean");
		boolean mapping = Boolean.valueOf(
				parseUtil.getAttribute(conNode, "mapping")).booleanValue();

		String scopeName = parseUtil.getAttribute(conNode, "scope");
		ScopeType scope = ScopeType.valueOf(scopeName);
		String factoryName = parseUtil.getAttribute(conNode, "type-def");
		String typeName = parseUtil.getAttribute(conNode, "type");
		Type factory = null;
		// boolean nullable = false;
		Class<?> type = null;

		Element anyNode = parseUtil.getElement(conNode, "any");
		Element mappingRef = parseUtil.getElement(conNode, "ref");
		Element beanNode = parseUtil.getElement(conNode, "bean");
		Element valueNode = parseUtil.getElement(conNode, "value");
		Element validatorNode = parseUtil.getElement(conNode, "validator");
		// Element nullNode = parseUtil.getElement(conNode, "null");
		if (mappingRef != null) {
			enumProperty = EnumerationType.valueOf(parseUtil.getAttribute(
					mappingRef, "enum-property"));

			value = parseUtil.getAttribute(mappingRef, "value");
			temporalProperty = parseUtil.getAttribute(mappingRef,
					"temporal-property");
			bean = parseUtil.getAttribute(mappingRef, "bean");
			mapping = Boolean.valueOf(
					parseUtil.getAttribute(mappingRef, "mapping"))
					.booleanValue();
			scope = ScopeType.valueOf(parseUtil.getAttribute(mappingRef,
					"scope"));
			factoryName = parseUtil.getAttribute(mappingRef, "type-def");
			validatorNode = parseUtil.getElement(mappingRef, "validator");
		} else if (beanNode != null) {
			addBean(beanNode, beanBuilder, null, null, null, true, bean, false,
					null);
			return;
		} else if (valueNode != null) {
			value = valueNode.getTextContent();
		}
		// else
		// if( nullNode != null )
		// nullable = true;

		try {
			if (factoryName != null)
				factory = (Type) ClassUtil.getInstance(ClassUtil
						.get(factoryName));

			if (typeName != null)
				type = ClassUtil.get(typeName);

		} catch (Exception ex) {
			throw new BrutosException(ex);
		}

		KeyBuilder keyBuilder = beanBuilder.setKey(bean, enumProperty,
				temporalProperty, mapping ? bean : null, scope, value,
				anyNode != null, factory, type);

		if (anyNode != null)
			this.buildAny(anyNode, keyBuilder);

		addValidator(validatorNode, keyBuilder);

	}

	protected void buildElementCollection(Element conNode, BeanBuilder beanBuilder) {

		String enumPropertyName = parseUtil.getAttribute(conNode,
				"enum-property");
		EnumerationType enumProperty = EnumerationType
				.valueOf(enumPropertyName);
		String value = parseUtil.getAttribute(conNode, "value");
		String temporalProperty = parseUtil.getAttribute(conNode,
				"temporal-property");
		String bean = parseUtil.getAttribute(conNode, "bean");
		boolean mapping = Boolean.valueOf(
				parseUtil.getAttribute(conNode, "mapping")).booleanValue();

		String scopeName = parseUtil.getAttribute(conNode, "scope");
		ScopeType scope = ScopeType.valueOf(scopeName);
		String factoryName = parseUtil.getAttribute(conNode, "type-def");
		String typeName = parseUtil.getAttribute(conNode, "type");
		Type factory = null;
		boolean nullable = false;
		Class<?> type = null;

		Element anyNode = parseUtil.getElement(conNode, "any");
		Element mappingRef = parseUtil.getElement(conNode, "ref");
		Element beanNode = parseUtil.getElement(conNode, "bean");
		Element valueNode = parseUtil.getElement(conNode, "value");
		Element validatorNode = parseUtil.getElement(conNode, "validator");
		Element nullNode = parseUtil.getElement(conNode, "null");
		if (mappingRef != null) {
			enumProperty = EnumerationType.valueOf(parseUtil.getAttribute(
					mappingRef, "enum-property"));

			value = parseUtil.getAttribute(mappingRef, "value");
			temporalProperty = parseUtil.getAttribute(mappingRef,
					"temporal-property");
			bean = parseUtil.getAttribute(mappingRef, "bean");
			mapping = Boolean.valueOf(
					parseUtil.getAttribute(mappingRef, "mapping"))
					.booleanValue();
			scope = ScopeType.valueOf(parseUtil.getAttribute(mappingRef,
					"scope"));
			factoryName = parseUtil.getAttribute(mappingRef, "type-def");
			validatorNode = parseUtil.getElement(mappingRef, "validator");
		} else if (beanNode != null) {
			addBean(beanNode, beanBuilder, null, null, null, false, null, true,
					bean);
			return;
		} else if (valueNode != null) {
			value = valueNode.getTextContent();
		} else if (nullNode != null)
			nullable = true;

		try {
			if (factoryName != null) {
				factory = (Type) ClassUtil.getInstance(ClassUtil
						.get(factoryName));
			}

			if (typeName != null)
				type = ClassUtil.get(typeName);

		} catch (Exception ex) {
			throw new BrutosException(ex);
		}

		ElementBuilder elementBuilder = beanBuilder.setElement(bean,
				enumProperty, temporalProperty, mapping ? bean : null, scope,
				value, nullable, anyNode != null, factory, type);

		if (anyNode != null)
			this.buildAny(anyNode, elementBuilder);

		addValidator(validatorNode, elementBuilder);

	}

	protected void addActions(NodeList actionList,
			ControllerBuilder controllerBuilder) {

		for (int k = 0; k < actionList.getLength(); k++) {
			Element actionNodeNode = (Element) actionList.item(k);
			addAction(actionNodeNode, controllerBuilder);
		}
	}

	protected void addAction(Element actionNode,
			ControllerBuilder controllerBuilder) {

		String id 					= parseUtil.getAttribute(actionNode, "id");
		String executor 			= parseUtil.getAttribute(actionNode, "executor");
		String result 				= parseUtil.getAttribute(actionNode, "result");
		String resultRendered 		= parseUtil.getAttribute(actionNode, "result-rendered");
		String view 				= parseUtil.getAttribute(actionNode, "view");
		boolean resolvedView		= Boolean.valueOf(parseUtil.getAttribute(actionNode, "resolved-view"));
		boolean renderedView		= Boolean.valueOf(parseUtil.getAttribute(actionNode, "rendered-view"));
		DispatcherType dispatcher 	= DispatcherType.valueOf(parseUtil.getAttribute(actionNode, "dispatcher"));

		ActionBuilder actionBuilder = 
				controllerBuilder.addAction(
					id, 
					result,
					Boolean.parseBoolean(resultRendered), 
					renderedView? view : null, 
					dispatcher,
					renderedView? resolvedView : true, 
					executor
				);

		this.loadActionDependencies(actionNode, actionBuilder);

	}

	protected void loadActionDependencies(Element actionNode, ActionBuilder builder){
		
		addParametersAction(parseUtil.getElements(actionNode,
				XMLBrutosConstants.XML_BRUTOS_PARAMETER), builder);

		loadAcceptRequestTypes(parseUtil.getElements(actionNode,
				XMLBrutosConstants.XML_BRUTOS_ACCEPT_REQUEST_TYPE), builder);

		loadResponseTypes(parseUtil.getElements(actionNode,
				XMLBrutosConstants.XML_BRUTOS_RESPONSE_TYPE), builder);
		
		addThrowSafe(parseUtil.getElements(actionNode,
				XMLBrutosConstants.XML_BRUTOS_THROWS), builder);
		
	}
	
	protected void addParametersAction(NodeList params,
			ActionBuilder actionBuilder) {

		ParametersBuilder parametersBuilder = actionBuilder.buildParameters();

		for (int k = 0; k < params.getLength(); k++) {
			Element paramNode = (Element) params.item(k);
			addParameterAction(paramNode, parametersBuilder);
		}

	}

	protected void addParameterAction(Element paramNode,
			ParametersBuilder parametersBuilder) {

		EnumerationType enumProperty 	= EnumerationType.valueOf(parseUtil.getAttribute(paramNode, "enum-property"));
		String value 					= parseUtil.getAttribute(paramNode, "value");
		String temporalProperty 		= parseUtil.getAttribute(paramNode, "temporal-property");
		String bean 					= parseUtil.getAttribute(paramNode, "bean");
		boolean mapping 				= Boolean.valueOf(parseUtil.getAttribute(paramNode, "mapping"));
		ScopeType scope 				= ScopeType.valueOf(parseUtil.getAttribute(paramNode, "scope"));
		String factoryName 				= parseUtil.getAttribute(paramNode, "type-def");
		String type 					= parseUtil.getAttribute(paramNode, "type");
		FetchType fetchType				= FetchType.valueOf(parseUtil.getAttribute(paramNode, "fetch-type"));
		Type factory 					= null;
		Class<?> typeClass 				= null;
		boolean nullable 				= false;

		try{
			if(type != null)
				typeClass = ClassUtil.get(type);
		}
		catch(Exception ex) {
			throw new BrutosException(ex);
		}

		Element anyNode 		= parseUtil.getElement(paramNode, "any");
		Element mappingRef 		= parseUtil.getElement(paramNode, "ref");
		Element beanNode 		= parseUtil.getElement(paramNode, "bean");
		Element valueNode 		= parseUtil.getElement(paramNode, "value");
		Element validatorNode 	= parseUtil.getElement(paramNode, "validator");
		Element nullNode 		= parseUtil.getElement(paramNode, "null");
		
		if(mappingRef != null) {
			enumProperty 		= EnumerationType.valueOf(parseUtil.getAttribute(mappingRef, "enum-property"));
			value 				= parseUtil.getAttribute(mappingRef, "value");
			temporalProperty 	= parseUtil.getAttribute(mappingRef, "temporal-property");
			bean 				= parseUtil.getAttribute(mappingRef, "bean");
			mapping 			= Boolean.valueOf(parseUtil.getAttribute(mappingRef, "mapping"));
			scope 				= ScopeType.valueOf(parseUtil.getAttribute(mappingRef, "scope"));
			factoryName 		= parseUtil.getAttribute(mappingRef, "type-def");
			validatorNode 		= parseUtil.getElement(mappingRef, "validator");
		}
		else
		if(beanNode != null){
			addBean(bean, beanNode, parametersBuilder, typeClass);
			return;
		}
		else
		if(valueNode != null){
			value = valueNode.getTextContent();
		}
		else
		if(nullNode != null)
			nullable = true;

		try{
			if(factoryName != null)
				factory = (Type) ClassUtil.getInstance(ClassUtil
						.get(factoryName));
		}
		catch(BrutosException ex) {
			throw ex;
		}
		catch(Exception ex) {
			throw new BrutosException(ex);
		}

		ParameterBuilder parameterBuilder = parametersBuilder.addParameter(
				bean, scope, enumProperty, temporalProperty, mapping ? bean
						: null, factory, value, nullable, anyNode != null,
				typeClass);

		
		parameterBuilder.setFetchType(fetchType);
		
		if(anyNode != null)
			this.buildAny(anyNode, parameterBuilder);
		
		addValidator(validatorNode, parameterBuilder);

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
		DataType dataType 	    = DataType.valueOf(mediaTypeName);
		
		if(dataType == null){
			throw new BrutosException("invalid media type: " + mediaTypeName );
		}
		
		if(builder instanceof ControllerBuilder){
			((ControllerBuilder)builder).addRequestType(dataType);
		}
		
		if(builder instanceof ActionBuilder){
			((ActionBuilder)builder).addRequestType(dataType);
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
		DataType dataType 	    = DataType.valueOf(mediaTypeName);
		
		if(dataType == null){
			throw new BrutosException("invalid media type: " + mediaTypeName );
		}
		
		if(builder instanceof ControllerBuilder){
			((ControllerBuilder)builder).addResponseType(dataType);
		}
		
		if(builder instanceof ActionBuilder){
			((ActionBuilder)builder).addResponseType(dataType);
		}
		
	}	
	protected void addThrowSafe(NodeList throwSafeNodeList,
			ControllerBuilder controllerBuilder) {

		for (int i = 0; i < throwSafeNodeList.getLength(); i++) {
			Element throwSafeNode = (Element) throwSafeNodeList.item(i);
			addThrowSafe(throwSafeNode, controllerBuilder);
		}
	}

	protected void addThrowSafe(Element throwSafeNode,
			ControllerBuilder controllerBuilder) {

		String view 				= parseUtil.getAttribute(throwSafeNode, "view");
		boolean resolvedView		= Boolean.valueOf(parseUtil.getAttribute(throwSafeNode, "resolved-view"));
		boolean renderedView		= Boolean.valueOf(parseUtil.getAttribute(throwSafeNode, "rendered-view"));
		//boolean enabled				= Boolean.valueOf(parseUtil.getAttribute(throwSafeNode, "enabled"));
		String target 				= parseUtil.getAttribute(throwSafeNode, "target");
		String name 				= parseUtil.getAttribute(throwSafeNode, "name");
		DispatcherType dispatcher 	= DispatcherType.valueOf(parseUtil.getAttribute(throwSafeNode, "dispatcher"));
		Class<?> targetClass 		= this.getClass(target);
		
		controllerBuilder
			.addThrowable(
				targetClass, 
				renderedView? view : null, 
				name, 
				dispatcher,
				renderedView? resolvedView : true
			);
	}

	protected void addThrowSafe(NodeList throwSafeNodeList,
			ActionBuilder actionBuilder) {

		for (int i = 0; i < throwSafeNodeList.getLength(); i++) {
			Element throwSafeNode = (Element) throwSafeNodeList.item(i);
			addThrowSafe(throwSafeNode, actionBuilder);
		}
	}

	protected void addThrowSafe(Element throwSafeNode, ActionBuilder actionBuilder) {

		String view 				= parseUtil.getAttribute(throwSafeNode, "view");
		boolean resolvedView		= Boolean.valueOf(parseUtil.getAttribute(throwSafeNode, "resolved-view"));
		boolean renderedView		= Boolean.valueOf(parseUtil.getAttribute(throwSafeNode, "rendered-view"));
		//boolean enabled				= Boolean.valueOf(parseUtil.getAttribute(throwSafeNode, "enabled"));
		String target 				= parseUtil.getAttribute(throwSafeNode, "target");
		String name 				= parseUtil.getAttribute(throwSafeNode, "name");
		DispatcherType dispatcher 	= DispatcherType.valueOf(parseUtil.getAttribute(throwSafeNode, "dispatcher"));
		Class<?> targetClass 		= this.getClass(target);

		//if(enabled){
			actionBuilder
				.addThrowable(
					targetClass, 
					renderedView? view : null, 
					name, 
					dispatcher,
					renderedView? resolvedView : true
				);
		//}
	}

	protected void addValidator(Element validatorNode,
			RestrictionBuilder restrictionBuilder) {

		if (validatorNode == null)
			return;

		String msg = parseUtil.getAttribute(validatorNode, "message");

		restrictionBuilder.setMessage(msg);

		NodeList rules = parseUtil.getElements(validatorNode,
				XMLBrutosConstants.XML_BRUTOS_VALIDATOR_RULE);

		for (int i = 0; i < rules.getLength(); i++) {
			Element rule = (Element) rules.item(i);
			String name = parseUtil.getAttribute(rule, "name");
			String value = parseUtil.getAttribute(rule, "value");

			value = value == null ? rule.getTextContent() : value;

			RestrictionRules r = RestrictionRules.valueOf(name);

			if (r == null)
				throw new BrutosException("invalid restriction rule: " + name);

			restrictionBuilder.addRestriction(r, value);

		}

	}

	protected Class<?> getClass(String clazzName){
		try{
			return clazzName == null? null : ClassUtil.get(clazzName);
		} 
		catch (Exception ex) {
			throw new BrutosException(ex);
		}
	}
}
