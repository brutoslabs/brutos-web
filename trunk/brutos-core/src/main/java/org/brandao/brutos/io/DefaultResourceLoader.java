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

package org.brandao.brutos.io;

import java.net.MalformedURLException;
import java.net.URL;
import org.brandao.brutos.BrutosException;

/**
 * 
 * @author Brandao
 */
public class DefaultResourceLoader implements ResourceLoader {

	private ClassLoader classLoader;

	public DefaultResourceLoader() {
		this(Thread.currentThread().getContextClassLoader());
	}

	public DefaultResourceLoader(ClassLoader classLoader) {
		this.classLoader = classLoader;
	}

	public Resource getResource(String path) {
		if (path.startsWith(CLASSPATH_URL_PREFIX)) {
			return new ClassPathResource(getClassloader(),
					path.substring(CLASSPATH_URL_PREFIX.length()));
		} else {
			try {
				URL url = new URL(path);
				return new UrlResource(url);
			} catch (MalformedURLException e) {
				return getContextResource(path);
			}
		}
	}

	protected Resource getContextResource(String path) {
		throw new BrutosException("not found: " + path);
	}

	public ClassLoader getClassloader() {
		return this.classLoader;
	}

}
