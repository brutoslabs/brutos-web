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
public class ObjectFactoryWrapper implements ObjectFactory {

	private ObjectFactory objectFactory;

	public ObjectFactoryWrapper(ObjectFactory objectFactory) {
		this.objectFactory = objectFactory;
	}

	public Object getBean(String name) {
		return objectFactory.getBean(name);
	}

	public void configure(Properties properties) {
		objectFactory.configure(properties);
	}

	public void destroy() {
		objectFactory.destroy();
	}

	public Object getBean(Class clazz) {
		return objectFactory.getBean(clazz);
	}

}
