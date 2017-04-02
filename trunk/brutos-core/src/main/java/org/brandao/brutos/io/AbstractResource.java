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

/**
 * 
 * @author Brandao
 */
public abstract class AbstractResource implements Resource {

	protected String createRelativePath(String path, String relativePath) {

		path = cleanPath(path);
		// path = path.endsWith("/")? path.substring(0,path.length()-1) : path;

		relativePath = this.cleanPath(relativePath);

		int index = path.lastIndexOf("/");

		if (index != -1) {
			String newPath = path.substring(0, index);
			if (!relativePath.startsWith("/"))
				newPath += "/";
			return newPath + relativePath;
		} else
			return relativePath;

	}

	protected String cleanPath(String path) {
		path = path.replace("\\", "/");
		path = path.replaceAll("/+", "/");
		return path;
	}

	public boolean isOpen() {
		return false;
	}

	public String toString() {
		return getName();
	}
}
