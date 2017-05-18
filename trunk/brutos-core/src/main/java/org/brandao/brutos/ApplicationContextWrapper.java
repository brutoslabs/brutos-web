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
public class ApplicationContextWrapper extends AbstractApplicationContext {

	protected ConfigurableApplicationContext applicationContext;

	public ApplicationContextWrapper(ConfigurableApplicationContext app) {
		this.applicationContext = app;
	}

	public void destroy() {
		this.applicationContext.destroy();
	}

	public Properties getConfiguration() {
		return this.applicationContext.getConfiguration();
	}

	public MvcResponse getMvcResponse() {
		return this.applicationContext.getMvcResponse();
	}

	public MvcRequest getMvcRequest() {
		return this.applicationContext.getMvcRequest();
	}

	public Scopes getScopes() {
		return this.applicationContext.getScopes();
	}

	public Object getController(Class<?> clazz) {
		return this.applicationContext.getController(clazz);
	}

	protected void loadDefinitions(ComponentRegistry registry) {
		throw new UnsupportedOperationException();
	}

}
