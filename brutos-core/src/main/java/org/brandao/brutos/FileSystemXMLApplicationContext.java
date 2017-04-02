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

import org.brandao.brutos.io.FileSystemResource;
import org.brandao.brutos.io.Resource;

/**
 * 
 * @author Brandao
 */
public class FileSystemXMLApplicationContext extends
		AbstractXMLApplicationContext {

	private Resource[] resources;

	public FileSystemXMLApplicationContext(String[] locations,
			AbstractApplicationContext parent) {
		super(parent);

		resources = new Resource[locations.length];
		for (int i = 0; i < locations.length; i++)
			resources[i] = new FileSystemResource(locations[i]);
	}

	public FileSystemXMLApplicationContext(String[] locations) {
		this(locations, null);
	}

	public FileSystemXMLApplicationContext(String location) {
		this(new String[] { location }, null);
	}

	protected Resource getContextResource(String path) {
		return new FileSystemResource(path);
	}

	protected Resource[] getContextResources() {
		return resources;
	}

}
