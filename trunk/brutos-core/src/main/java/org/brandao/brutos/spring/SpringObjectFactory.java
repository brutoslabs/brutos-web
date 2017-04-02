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

package org.brandao.brutos.spring;

import org.brandao.brutos.ObjectFactory;
import java.util.Properties;
import org.springframework.context.ApplicationContext;

/**
 * 
 * @author Brandao
 */
public class SpringObjectFactory implements ObjectFactory {

	private String name;

	private boolean initialized;

	private ApplicationContext context;

	public SpringObjectFactory() {
		this.initialized = false;
	}

	public Object getBean(String name) {

		if (!this.initialized)
			this.init();

		return context.containsBeanDefinition(name) ? context.getBean(name)
				: null;
	}

	public Object getBean(Class clazz) {

		if (!this.initialized)
			this.init();

		return context.getBean(clazz);
	}

	private synchronized void init() {

		if (initialized)
			return;

		this.context = SpringContext.getApplicationContext(this.name);
		this.initialized = true;
	}

	public void configure(Properties properties) {
		this.name = properties.getProperty(SpringContext.SPRING_CONTEXT_NAME,
				SpringContext.DEFAULT_SPRING_CONTEXT);
	}

	public void destroy() {
	}

}
