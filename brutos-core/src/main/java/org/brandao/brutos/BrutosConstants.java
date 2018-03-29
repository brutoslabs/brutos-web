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

package org.brandao.brutos;

/**
 * 
 * @author Brandao
 */
public interface BrutosConstants {

	final String WEBFRAME = "Controller";

	final String CONTROLLER_PROPERTY = "Controller";
	
	final String COMMA = ",";

	final String APPLICATION_CONTEXT = "application-context";

	final String CUSTOM_TYPES = "customTypes";

	final String EXCEPTION = "brutos_exception";

	final String BUNDLE_NAME = "default_bundle";

	final String USER = "brutos_user";

	final String IOC_MANAGER = "ioc-manager";

	final String IOC_PROVIDER = "ioc-provider";

	final String WEBFRAME_MANAGER = "webframe-manager";

	final String INTERCEPTOR_MANAGER = "interceptor-manager";

	final String VIEW_PROVIDER = "view-provider";

	final String VALIDATOR_PROVIDER = "validator-provider";

	final String REDIRECT = ApplicationContext.class.getName()
			+ ".REDIRECT";

	final String EMPTY_ARGNAME = ".";
	
	final String EXCEPTION_DATA = ApplicationContext.class.getName()
			+ ".EXCEPTION_DATA";

	final String REQUEST_INSTRUMENT = ApplicationContext.class.getName()
			+ ".REQUEST_INSTRUMENT";
	
	final String ROOT_APPLICATION_CONTEXT_ATTRIBUTE = ApplicationContext.class
			.getName() + ".ROOT";

	final String LOGGER = ApplicationContext.class.getName() + ".LOGGER";

	final String DEFAULT_RETURN_NAME = "result";

	final String METHOD_RESOLVER = ApplicationContext.class.getName()
			+ ".METHOD_RESOLVER";

	final String CONTROLLER_RESOLVER = ApplicationContext.class
			.getName() + ".CONTROLLER_RESOLVER";

	final String CONTROLLER = ApplicationContext.class.getName()
			+ ".CONTROLLER";

	final String INVOKER = ApplicationContext.class.getName()
			+ ".INVOKER";

	final DispatcherType DEFAULT_DISPATCHERTYPE = DispatcherType.FORWARD;

	final String DEFAULT_DISPATCHERTYPE_NAME = "forward";

	final ScopeType DEFAULT_SCOPETYPE = ScopeType.PARAM;

	final String DEFAULT_EXCEPTION_NAME = "exception";

	final EnumerationType DEFAULT_ENUMERATIONTYPE = EnumerationType.ORDINAL;

	final String FETCH_TYPE = "org.brandao.brutos.fetch_type";
	
	final String ACTION_TYPE = "org.brandao.brutos.action_strategy";

	final String CONTROLLER_MANAGER_CLASS = "org.brandao.brutos.manager.controller";

	final String INTERCEPTOR_MANAGER_CLASS = "org.brandao.brutos.manager.interceptor";

	final String INVOKER_CLASS = "org.brandao.brutos.invoker";

	final String TYPE_MANAGER_CLASS = "org.brandao.brutos.type.manager";

	final String CDI_BEAN_MANAGER = "org.brandao.brutos.cdi.bean_manager";

	final String JNDI_CLASS = "org.brandao.brutos.jndi.class";

	final String JNDI_URL = "org.brandao.brutos.jndi.url";

	final String ACTION_RESOLVER = "org.brandao.brutos.controller.action_resolver";

	@Deprecated
	final String REQUEST_FACTORY = "org.brandao.brutos.controller.request_factory";

	@Deprecated
	final String RESPONSE_FACTORY = "org.brandao.brutos.controller.response_factory";

	final String CONTROLLER_RESOLVER_CLASS = "org.brandao.brutos.controller.class";

	final String OBJECT_FACTORY_CLASS = "org.brandao.brutos.object_factory";

	final String VALIDATOR_FACTORY_CLASS = "org.brandao.brutos.validator_factory";

	final String RENDER_VIEW_CLASS = "org.brandao.brutos.render_view";

	final String REQUEST_PARSER_LISTENER = "org.brandao.brutos.request.listener_factory";

	final String REQUEST_TYPE = "org.brandao.brutos.request.default_type";
	
	final String REQUEST_PARSER = "org.brandao.brutos.request.parser";

	final String RESPONSE_TYPE = "org.brandao.brutos.response.default_type";

	final String SCOPE_TYPE = "org.brandao.brutos.scope_type";

	final String ACTION_PARAMETER_NAME = "org.brandao.brutos.action.parameter_name";
	
	final String TEMPORAL_PROPERTY = "org.brandao.brutos.temporal_property";
	
	final String DISPATCHER_TYPE = "org.brandao.brutos.view.dispatcher_type";
	
	final String VIEW_RESOLVER = "org.brandao.brutos.view.resolver";
	
	@Deprecated
	final String UPLOAD_LISTENER_CLASS = "org.brandao.brutos.upload_listener_factory";

	final String CODE_GENERATOR_CLASS = "org.brandao.brutos.code_generator";

	final String ENUMERATION_TYPE = "org.brandao.brutos.enumeration_type";
	
	final String AUTO_VIEW_RESOLVER = "org.brandao.brutos.view.auto";

	final String VIEW_RESOLVER_PREFIX = "org.brandao.brutos.view.prefix";

	final String VIEW_RESOLVER_SUFFIX = "org.brandao.brutos.view.suffix";

	final String VIEW_RESOLVER_INDEX = "org.brandao.brutos.view.index";

	final String VIEW_RESOLVER_SEPARATOR = "org.brandao.brutos.view.separator";

	final String DEFAULT_OBJECT_FACTORY_CLASS = "org.brandao.brutos.cdi.JSR299ObjectFactory";

	final String DEFAULT_PROXY_PROVIDER_CLASS = "org.brandao.brutos.codegenerator.JavassistCodeGeneratorProvider";

	final String DEFAULT_VIEW_PROVIDER_CLASS = "org.brandao.brutos.view.DefaultViewProvider";

	final String DEFAULT_INVOKER_CLASS = "org.brandao.brutos.Invoker";

	final String DEFAULT_TYPE_MANAGER_CLASS = "org.brandao.brutos.type.TypeManagerImp";

	final String DEFAULT_CDI_BEAN_MANAGER = "java:comp/BeanManager";

	final String DEFAULT_ACTION_TYPE_NAME = "parameter";

	final String DEFAULT_FETCH_TYPE_NAME = "eager";
	
	final String DEFAULT_ACTION_PARAMETER_NAME = "invoke";
	
	final String DEFAULT_SEPARATOR = "-";

	final String DEFAULT_ENUMERATION_TYPE = "auto";

	final String DEFAULT_KEY_NAME = "key";

	final String DEFAULT_ELEMENT_NAME = "element";

	final String DEFAULT_TEMPORALPROPERTY = "yyyy-MM-dd";

	final String DEFAULT_SUFFIX_VIEW = "";

	final String DEFAULT_PREFIX_VIEW = "views.";

	final String DEFAULT_INDEX_VIEW = "index";

	final String DEFAULT_SEPARATOR_VIEW = ".";

	final String DEFAULT_VIEW_RESOLVER = "true";

	final String INCLUDE = "include";

	final String EXCLUDE = "exclude";

	final String EXPRESSION = "expression";
	
	final int COLLECTION_MAX_ITENS = 256;
	
}
