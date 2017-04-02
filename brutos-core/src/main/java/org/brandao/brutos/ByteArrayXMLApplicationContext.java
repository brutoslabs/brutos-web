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

import org.brandao.brutos.io.ByteArrayResource;
import org.brandao.brutos.io.Resource;

/**
 * 
 * @author Brandao
 */
public class ByteArrayXMLApplicationContext extends
		AbstractXMLApplicationContext {

	private Resource[] resources;

	public ByteArrayXMLApplicationContext(byte[][] array) {
		this(array, null);
	}

	public ByteArrayXMLApplicationContext(byte[] array) {
		this(new byte[][] { array }, null);
	}

	public ByteArrayXMLApplicationContext(byte[] array,
			AbstractApplicationContext parent) {
		this(new byte[][] { array }, parent);
	}

	public ByteArrayXMLApplicationContext(byte[][] arrays,
			AbstractApplicationContext parent) {
		super(parent);

		resources = new Resource[arrays.length];
		for (int i = 0; i < arrays.length; i++)
			resources[i] = new ByteArrayResource(arrays[i]);
	}

	protected Resource[] getContextResources() {
		return resources;
	}

}
