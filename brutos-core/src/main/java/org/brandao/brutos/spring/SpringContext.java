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

import java.util.HashMap;
import java.util.Map;
import org.brandao.brutos.BrutosException;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 
 * @author Brandao
 */
public class SpringContext implements ApplicationContextAware {

	public static final String DEFAULT_SPRING_CONTEXT = "DEFAULT_SPRING_CONTEXT";

	public static final String SPRING_CONTEXT_NAME = "org.brandao.brutos.spring.context_name";

	private static final Map currentApplicationCopntext;

	static {
		currentApplicationCopntext = new HashMap();
	}

	private String name;

	public SpringContext(String name) {
		this.name = name;
	}

	public SpringContext() {
		this.name = DEFAULT_SPRING_CONTEXT;
	}

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		registerApplicationContext(this.name, applicationContext);
	}

	private static synchronized void registerApplicationContext(String name,
			ApplicationContext applicationContext) throws BeansException {

		if (currentApplicationCopntext.containsKey(name)) {
			throw new IllegalStateException(
					"Multiple application context definitions has been detected.");
		} else
			currentApplicationCopntext.put(name, applicationContext);
	}

	public static ApplicationContext getApplicationContext(String name) {
		ApplicationContext applicationContext = (ApplicationContext) currentApplicationCopntext
				.get(name);

		if (applicationContext == null)
			throw new BrutosException("application context not found!");
		else
			return applicationContext;
	}

}
