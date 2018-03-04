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

/**
 * 
 * @author Brandao
 */
public interface XMLBrutosConstants {

	public static final String JAXP_SCHEMA_SOURCE 				= "http://java.sun.com/xml/jaxp/properties/schemaSource";
	
	public static final String XML_BRUTOS_SCHEMA 				= "brutos_1_1.xsd";
	
	public static final String XML_BRUTOS_CONTEXT_SCHEMA 		= "brutos-context-1.1.xsd";
	
	public static final String XML_BRUTOS_CONTEXT_NAMESPACE 	= "http://www.brutosframework.com.br/schema/context";
	
	public static final String XML_BRUTOS_CONTROLLER_SCHEMA 	= "brutos-controllers-1.1.xsd";
	
	public static final String XML_BRUTOS_CONTROLLER_NAMESPACE 	= "http://www.brutosframework.com.br/schema/controllers";
	
	public static final String JAXP_SCHEMA_LANGUAGE 			= "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
	
	public static final String W3C_XML_SCHEMA 					= "http://www.w3.org/2001/XMLSchema";
	
	public static final String XML_BRUTOS 						= "brutos-configuration";
	
	public static final String XML_BRUTOS_VERSION 				= "version";
	
	// public static final String XML_BRUTOS_IMPORTERS = "imports";
	
	public static final String XML_BRUTOS_IMPORTER 				= "import";
	
	public static final String XML_BRUTOS_CONTEXT_PARAMS 		= "params";
	
	public static final String XML_BRUTOS_ANNOTATION_CONFIG 	= "annotation-config";
	
	public static final String XML_BRUTOS_COMPONENT_SCAN 		= "component-scan";
	
	public static final String XML_BRUTOS_CUSTOM_FILTER 		= "custom";
	
	public static final String XML_BRUTOS_CONTEXT_PARAM 		= "param";

	public static final String XML_BRUTOS_WEB_FRAMES 			= "web-frames";

	public static final String XML_BRUTOS_WEB_FRAME 			= "web-frame";

	public static final String XML_BRUTOS_ACCEPT_REQUEST_TYPE 	= "accept-request-type";

	public static final String XML_BRUTOS_RESPONSE_TYPE 		= "response-type";
	
	public static final String XML_BRUTOS_CONTROLLERS 			= "controllers";
	
	public static final String XML_BRUTOS_CONTROLLER 			= "controller";
	
	public static final String XML_BRUTOS_MAPPING 				= "mapping";
	
	public static final String XML_BRUTOS_THROWS 				= "throw-safe";
	
	public static final String XML_BRUTOS_ALIAS 				= "alias";
	
	public static final String XML_BRUTOS_PROPERTY_MAPPING 		= "property-mapping";
	
	public static final String XML_BRUTOS_MAPPING_COLLECTION 	= "mapping-collection";
	
	public static final String XML_BRUTOS_MAPPING_MAP 			= "mapping-map";
	
	public static final String XML_BRUTOS_MAPPING_MAP_KEY 		= "mapping-key";
	
	public static final String XML_BRUTOS_MAPPING_REF 			= "mapping-ref";
	
	public static final String XML_BRUTOS_PROPERTY 				= "property-webframe";
	
	public static final String XML_BRUTOS_CONTROLLER_PROPERTY 	= "property";
	
	public static final String XML_BRUTOS_METHOD 				= "method";
	
	public static final String XML_BRUTOS_METHOD_PARAM 			= "method-param";
	
	public static final String XML_BRUTOS_BEAN_VALUE 			= "value";
	
	public static final String XML_BRUTOS_BEAN_VALUE_NULL 		= "null";
	
	public static final String XML_BRUTOS_BEAN_REF 				= "ref";
	
	public static final String XML_BRUTOS_BEAN 					= "bean";
	
	public static final String XML_BRUTOS_BEANS 				= "beans";
	
	// public static final String XML_BRUTOS_BEAN_CONSTRUCTOR = "constructor";
	
	public static final String XML_BRUTOS_BEAN_CONSTRUCTOR_ARG 	= "constructor-arg";
	
	public static final String XML_BRUTOS_BEAN_PROPERY 			= "property";

	public static final String XML_BRUTOS_PROPERTIES 			= "properties";

	public static final String XML_BRUTOS_PROPERTIES_PROP 		= "prop";

	public static final String XML_BRUTOS_PARAMETER 			= "parameter";
	
	public static final String XML_BRUTOS_ACTION 				= "action";
	
	public static final String XML_BRUTOS_PROPS 				= "props";
	
	public static final String XML_BRUTOS_PROPS_PROP 			= "prop";
	
	public static final String XML_BRUTOS_MAP 					= "map";
	
	public static final String XML_BRUTOS_MAP_ENTRY 			= "entry";
	
	public static final String XML_BRUTOS_MAP_KEY 				= "key";
	
	public static final String XML_BRUTOS_COLLECTION_ELEMENT 	= "element";
	
	public static final String XML_BRUTOS_LIST 					= "list";
	
	public static final String XML_BRUTOS_SET 					= "set";
	
	public static final String XML_BRUTOS_EXTEND_CONFIGURATION 	= "extend-configuration";
	
	public static final String XML_BRUTOS_PROVIDER 				= "provider";
	
	public static final String XML_BRUTOS_INTERCEPTORS 			= "interceptors";
	
	public static final String XML_BRUTOS_INTERCEPTOR 			= "interceptor";
	
	public static final String XML_BRUTOS_INTERCEPTOR_STACK 	= "interceptor-stack";
	
	public static final String XML_BRUTOS_INTERCEPTOR_REF 		= "interceptor-ref";
	
	public static final String XML_BRUTOS_PARAM 				= "param";
	
	public static final String XML_BRUTOS_TYPES 				= "types";
	
	public static final String XML_BRUTOS_TYPE 					= "type";
	
	public static final String XML_BRUTOS_EXTENDED_SCOPES 		= "extended-scopes";
	
	public static final String XML_BRUTOS_EXTENDED_SCOPE 		= "extended-scope";
	
	public static final String XML_BRUTOS_VALIDATOR_RULE 		= "rule";
	
	public static final String XML_BRUTOS_VALIDATOR 			= "validator";

	public static final String[] XML_SUPPORTED_VERSION 			= new String[] { "1.0", "1.1" };

}
