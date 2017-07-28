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

	void setRenderView(ConfigurableRenderView renderView);

    void setActionParameterName(String name);

    void setActionType(ActionType value);
    
	void setAutomaticViewResolver(boolean value);
	
	void setRequestParser(ConfigurableRequestParser value);

    void setRequestType(DataType value);

    void setResponseType(DataType value);
    
    void setScopeType(ScopeType value);
    
    void setDispatcherType(DispatcherType value);
    
    void setEnumerationType(EnumerationType value);
    
    void setTemporalProperty(String value);
    
	void setViewPrefix(String value);
	
	void setViewSuffix(String value);
	
	void setViewIndex(String value);
	
	void setSeparator(String value);
    
	void setFetchType(FetchType value);
	
	void setInvoker(Invoker value);

	void setConfiguration(Properties config);

	void setObjectFactory(ObjectFactory objectFactory);

	void setCodeGenerator(CodeGenerator codeGenerator);

	void setViewResolver(ConfigurableViewResolver viewResolver);

	void setParent(ApplicationContext applicationContext);

	void destroy();
	
	void flush();

}
