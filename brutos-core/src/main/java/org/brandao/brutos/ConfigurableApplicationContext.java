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

import java.util.Properties;
import org.brandao.brutos.TypeManager;

/**
 * 
 * @author Brandao
 */
public interface ConfigurableApplicationContext extends ApplicationContext {

	@Deprecated
	MvcRequestFactory getRequestFactory();

	@Deprecated
	MvcResponseFactory getResponseFactory();

	void setInterceptorManager(InterceptorManager interceptorManager);

	void setRenderView(RenderView renderView);

	RenderView getRenderView();

	ValidatorFactory getValidatorFactory();

	Invoker getInvoker();

	void setInvoker(Invoker value);

	void setConfiguration(Properties config);

	Properties getConfiguration();

	void setObjectFactory(ObjectFactory objectFactory);

	InterceptorManager getInterceptorManager();

	ControllerManager getControllerManager();

	ObjectFactory getObjectFactory();

	@Deprecated
	ControllerResolver getControllerResolver();

	ActionResolver getActionResolver();

	CodeGenerator getCodeGenerator();

	void setCodeGenerator(CodeGenerator codeGenerator);

	void setViewResolver(ViewResolver viewResolver);

	ViewResolver getViewResolver();

	TypeManager getTypeManager();

	void setParent(ApplicationContext applicationContext);

	ApplicationContext getParent();

	void flush();

}
