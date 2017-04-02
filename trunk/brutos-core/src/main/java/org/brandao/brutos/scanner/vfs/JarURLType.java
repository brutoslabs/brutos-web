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

package org.brandao.brutos.scanner.vfs;

import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * 
 * @author Brandao
 */
public class JarURLType implements URLType {

	public Dir toDir(URL url) throws Exception {
		URLConnection urlConnection = url.openConnection();
		if (urlConnection instanceof JarURLConnection) {
			return new ZipDir(Vfs.getRelativePath(url),
					((JarURLConnection) urlConnection).getJarFile());
		} else
			return null;
	}

	public boolean matches(URL url) throws Exception {
		return "jar".equals(url.getProtocol());
	}

}
