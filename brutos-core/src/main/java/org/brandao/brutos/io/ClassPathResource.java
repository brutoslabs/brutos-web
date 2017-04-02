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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * 
 * @author Brandao
 */
public class ClassPathResource extends AbstractResource {

	private Class clazz;

	private ClassLoader classLoader;

	private String path;

	public ClassPathResource(ClassLoader classLoader, String path) {
		this(null, classLoader, path);
	}

	public ClassPathResource(Class clazz, String path) {
		this(clazz, null, path);
	}

	public ClassPathResource(Class clazz, ClassLoader classLoader, String path) {

		path = path.startsWith("/") ? path.substring(1, path.length()) : path;

		this.classLoader = classLoader != null ? classLoader : Thread
				.currentThread().getContextClassLoader();

		this.path = this.cleanPath(path);
	}

	public URL getURL() throws IOException {
		URL url = null;

		if (this.clazz != null)
			url = this.clazz.getResource(this.path);
		else
			url = this.classLoader.getResource(this.path);

		if (url == null)
			throw new FileNotFoundException(this.path + " URL does not exist");

		return url;
	}

	public Resource getRelativeResource(String relativePath) throws IOException {
		return new ClassPathResource(clazz, classLoader,
				this.createRelativePath(this.path, relativePath));
	}

	public InputStream getInputStream() throws IOException {
		InputStream input = null;

		if (this.clazz != null)
			input = this.clazz.getResourceAsStream(this.path);
		else if (this.classLoader != null)
			input = this.classLoader.getResourceAsStream(this.path);

		if (input == null)
			throw new FileNotFoundException(this.path + " does not exist");

		return input;
	}

	public boolean exists() {
		try {
			InputStream is = getInputStream();
			is.close();
			return true;
		} catch (IOException e) {
			return false;
		}

	}

	public boolean equals(Object e) {
		return e instanceof ClassPathResource ? ((ClassPathResource) e).path
				.equals(this.path) : false;
	}

	public String getName() {
		return this.path;
	}

}
