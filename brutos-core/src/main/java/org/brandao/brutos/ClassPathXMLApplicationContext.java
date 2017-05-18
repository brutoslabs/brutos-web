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

import org.brandao.brutos.io.ClassPathResource;
import org.brandao.brutos.io.Resource;

/**
 * 
 * @author Brandao
 */
public class ClassPathXMLApplicationContext extends
		AbstractXMLApplicationContext {

	private Resource[] resources;

	public ClassPathXMLApplicationContext(String[] locations) {
		this(locations, null, Thread.currentThread().getContextClassLoader(),
				null);
	}

	public ClassPathXMLApplicationContext(String location) {
		this(new String[] { location }, null, Thread.currentThread()
				.getContextClassLoader(), null);
	}

	public ClassPathXMLApplicationContext(String location,
			AbstractApplicationContext parent) {
		this(new String[] { location }, parent, Thread.currentThread()
				.getContextClassLoader(), null);
	}

	public ClassPathXMLApplicationContext(String[] locations, Class<?> clazz) {
		this(locations, null, null, clazz);
	}

	public ClassPathXMLApplicationContext(String[] locations,
			ClassLoader classLoader) {
		this(locations, null, classLoader, null);
	}

	protected Resource getContextResource(String path) {
		return new ClassPathResource(this.getClassloader(), path);
	}

	public ClassPathXMLApplicationContext(String[] locations,
			AbstractApplicationContext parent, ClassLoader classLoader,
			Class<?> clazz) {
		super(parent);

		resources = new Resource[locations.length];
		for (int i = 0; i < locations.length; i++) {

			if (clazz != null)
				resources[i] = new ClassPathResource(clazz, locations[i]);
			else if (classLoader != null)
				resources[i] = new ClassPathResource(classLoader, locations[i]);
		}
	}

	protected Resource[] getContextResources() {
		return resources;
	}

}
