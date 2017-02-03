^/*
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

public interface BrutosConstants {

	public final String WEBFRAME = "Controller";

	public final String COMMA = ",";

	public final String APPLICATION_CONTEXT = "application-context";

	public final String CUSTOM_TYPES = "customTypes";

	public final String EXCEPTION = "brutos_exception";

	public final String BUNDLE_NAME = "default_bundle";

	public final String USER = "brutos_user";

	public final String IOC_MANAGER = "ioc-manager";

	public final String IOC_PROVIDER = "ioc-provider";

	public final String WEBFRAME_MANAGER = "webframe-manager";

	public final String INTERCEPTOR_MANAGER = "interceptor-manager";

	public final String VIEW_PROVIDER = "view-provider";

	public final String VALIDATOR_PROVIDER = "validator-provider";

	public final String REDIRECT = ApplicationContext.class.getName()
			+ ".REDIRECT";

	public final String UPLOAD_LISTENER_FACTORY = ApplicationContext.class
			.getName() + ".UPLOAD_LISTENER_FACTORY";

	public final String REQUEST_INSTRUMENT = ApplicationContext.class.getName()
			+ ".REQUEST_INSTRUMENT";

	public final String HTTP_REQUEST_PARSER_FACTORY = ApplicationContext.class
			.getName() + ".HTTP_REQUEST_PARSER_FACTORY";

	public final String HTTP_REQUEST_PARSER = ApplicationContext.class
			.getName() + ".HTTP_REQUEST_PARSER";

	public final String SESSION_UPLOAD_STATS = ApplicationContext.class
			.getName() + ".SESSION_UPLOAD_STATS";

	public final String EXCEPTION_DATA = ApplicationContext.class.getName()
			+ ".EXCEPTION_DATA";

	public final String ROOT_APPLICATION_CONTEXT_ATTRIBUTE = ApplicationContext.class
			.getName() + ".ROOT";

	public final String FLASH_INSTRUMENT = ApplicationContext.class.getName()
			+ ".FLASH_INSTRUMENT";

	public final String LOGGER = ApplicationContext.class.getName() + ".LOGGER";

	public final String DEFAULT_RETURN_NAME = "result";

	public final String METHOD_RESOLVER = ApplicationContext.class.getName()
			+ ".METHOD_RESOLVER";

	public final String CONTROLLER_RESOLVER = ApplicationContext.class
			.getName() + ".CONTROLLER_RESOLVER";

	public final String CONTROLLER = ApplicationContext.class.getName()
			+ ".CONTROLLER";

	public final String INVOKER = ApplicationContext.class.getName()
			+ ".INVOKER";

	public final String JSF_HANDLER = ApplicationContext.class.getName()
			+ ".JSF_Handler";

	public final String JSF_CONTEXT = ApplicationContext.class.getName()
			+ ".JSF_Context";

	public final String JSF_UI_VIEW_ROOT = ApplicationContext.class.getName()
			+ ".JSF_viewRoot";

	public final String JSF_ACTION_LISTENER = ApplicationContext.class
			.getName() + ".JSF_Action_Listener";

	public final DispatcherType DEFAULT_DISPATCHERTYPE = DispatcherType.FORWARD;

	public final String DEFAULT_DISPATCHERTYPE_NAME = "forward";

	public final ScopeType DEFAULT_SCOPETYPE = ScopeType.PARAM;

	public final String DEFAULT_EXCEPTION_NAME = "exception";

	public final EnumerationType DEFAULT_ENUMERATIONTYPE = EnumerationType.ORDINAL;

	public final String ACTION_TYPE = "org.brandao.brutos.action_strategy";

	public final String CONTROLLER_MANAGER_CLASS = "org.brandao.brutos.manager.controller";

	public final String INTERCEPTOR_MANAGER_CLASS = "org.brandao.brutos.manager.interceptor";

	public final String INVOKER_CLASS = "org.brandao.brutos.invoker";

	public final String TYPE_MANAGER_CLASS = "org.brandao.brutos.type.manager";

	public final String CDI_BEAN_MANAGER = "org.brandao.brutos.cdi.bean_manager";

	public final String JNDI_CLASS = "org.brandao.brutos.jndi.class";

	public final String JNDI_URL = "org.brandao.brutos.jndi.url";

	public final String ACTION_RESOLVER = "org.brandao.brutos.controller.action_resolver";

	public final String REQUEST_FACTORY = "org.brandao.brutos.controller.request_factory";

	public final String RESPONSE_FACTORY = "org.brandao.brutos.controller.response_factory";

	public final String CONTROLLER_RESOLVER_CLASS = "org.brandao.brutos.controller.class";

	public final String OBJECT_FACTORY_CLASS = "org.brandao.brutos.object_factory";

	public final String VALIDATOR_FACTORY_CLASS = "org.brandao.brutos.validator_factory";

	public final String RENDER_VIEW_CLASS = "org.brandao.brutos.render_view";

	public final String UPLOAD_LISTENER_CLASS = "org.brandao.brutos.web.upload_listener_factory";

	public final String REQUEST_PARSER_CLASS = "org.brandao.brutos.web.request_parser";

	public final String CODE_GENERATOR_CLASS = "org.brandao.brutos.code_generator";

	public final String VIEW_RESOLVER_AUTO = "org.brandao.brutos.view.auto";

	public final String VIEW_RESOLVER_PREFIX = "org.brandao.brutos.view.prefix";

	public final String VIEW_RESOLVER_SUFFIX = "org.brandao.brutos.view.suffix";

	public final String VIEW_RESOLVER_INDEX = "org.brandao.brutos.view.index";

	public final String VIEW_RESOLVER_SEPARATOR = "org.brandao.brutos.view.separator";

	public final String DEFAULT_OBJECT_FACTORY_CLASS = "org.brandao.brutos.cdi.JSR299ObjectFactory";

	public final String DEFAULT_PROXY_PROVIDER_CLASS = "org.brandao.brutos.codegenerator.JavassistCodeGeneratorProvider";

	public final String DEFAULT_REQUEST_PARSER = "org.brandao.brutos.web.http.HttpRequestParserImp";

	public final String DEFAULT_VIEW_PROVIDER_CLASS = "org.brandao.brutos.view.DefaultViewProvider";

	public final String DEFAULT_INVOKER_CLASS = "org.brandao.brutos.Invoker";

	public final String DEFAULT_TYPE_MANAGER_CLASS = "org.brandao.brutos.type.TypeManagerImp";

	public final String DEFAULT_CDI_BEAN_MANAGER = "java:comp/BeanManager";

	public final String DEFAULT_ACTION_TYPE_NAME = "parameter";

	public final String DEFAULT_SEPARATOR = "-";

	public final String DEFAULT_ENUMERATION_TYPE = "ordinal";

	public final String DEFAULT_KEY_NAME = "key";

	public final String DEFAULT_ELEMENT_NAME = "element";

	public final String DEFAULT_TEMPORALPROPERTY = "dd/MM/yyyy";

	public final String DEFAULT_SUFFIX_VIEW = "";

	public final String DEFAULT_PREFIX_VIEW = "views.";

	public final String DEFAULT_INDEX_VIEW = "index";

	public final String DEFAULT_SEPARATOR_VIEW = ".";

	public final String DEFAULT_ACTION_ID = "invoke";

	public final String DEFAULT_VIEW_RESOLVER = "true";

	public final String WEB_SEPARATOR = "/";

	public final String WEB_APPLICATION_CLASS = ApplicationContext.class
			.getName() + ".web.application";

	public final String INCLUDE = "include";

	public final String EXCLUDE = "exclude";

	public final String EXPRESSION = "expression";
}
