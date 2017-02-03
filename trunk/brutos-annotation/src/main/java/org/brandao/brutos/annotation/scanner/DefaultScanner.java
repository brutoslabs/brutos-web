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

package org.brandao.brutos.annotation.scanner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.ClassUtil;
import org.brandao.brutos.mapping.StringUtil;
import org.brandao.brutos.scanner.vfs.Dir;
import org.brandao.brutos.scanner.vfs.File;
import org.brandao.brutos.scanner.vfs.Vfs;

/**
 *
 * @author Afonso Brandao
 */
public class DefaultScanner extends AbstractScanner {

	public DefaultScanner() {
	}

	public void scan() {
		try {
			ClassLoader classLoader = getClassLoader();
			for (String basePackage : super.getBasePackage())
				scan(classLoader, basePackage);
		} catch (BrutosException e) {
			throw e;
		} catch (Exception e) {
			throw new BrutosException(e);
		}
	}

	public void scan(ClassLoader classLoader, String basePackage)
			throws IOException {

		URLClassLoader urls = (URLClassLoader) classLoader;

		Enumeration e = urls.getResources(Vfs.toResource(basePackage));

		while (e.hasMoreElements()) {
			URL url = (URL) e.nextElement();
			scan(url, classLoader, basePackage);
		}
	}

	private void scan(URL url, ClassLoader classLoader, String basePackage) {
		Dir dir = Vfs.getDir(url);
		File[] files = dir.getFiles();

		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			String path = file.getRelativePath();

			if (path.endsWith(".class")) {
				String tmp = (StringUtil.isEmpty(basePackage) ? ""
						: basePackage + "/") + path;

				checkClass(tmp);

			}
		}
	}

	public ClassLoader getClassLoader() throws IOException {

		ClassLoader classLoader = ClassLoader.getSystemClassLoader();

		Enumeration urls = classLoader.getResources("META-INF/MANIFEST.MF");

		Map urlMap = new HashMap();

		while (urls.hasMoreElements()) {
			URL url = (URL) urls.nextElement();
			InputStream in = url.openConnection().getInputStream();
			List listURL = manifest(in);

			for (int i = 0; i < listURL.size(); i++) {
				URL u = (URL) listURL.get(i);
				urlMap.put(u.toExternalForm(), u);
			}
		}

		Collection collectionUrls = urlMap.values();
		Iterator iterator = collectionUrls.iterator();

		URL[] arrayUrls = new URL[collectionUrls.size()];

		int index = 0;
		while (iterator.hasNext()) {
			arrayUrls[index++] = (URL) iterator.next();
		}

		return new URLClassLoader(arrayUrls, Thread.currentThread()
				.getContextClassLoader());

	}

	public List manifest(InputStream in) {
		try {
			java.io.BufferedReader reader = new BufferedReader(
					new InputStreamReader(in));
			String txt = "";
			String line;

			while ((line = reader.readLine()) != null) {
				if (line.startsWith("Class-Path: ")) {
					txt = line
							.substring("Class-Path: ".length(), line.length());
					while ((line = reader.readLine()) != null
							&& line.startsWith(" ")) {
						txt += line.substring(1, line.length());
					}
				}
			}

			StringTokenizer stok = new StringTokenizer(txt, " ", false);
			List urlList = new ArrayList();

			while (stok.hasMoreTokens()) {
				String dirName = System.getProperty("user.dir");
				String fileName = stok.nextToken();

				URL url;

				if (".".equals(fileName))
					url = new URL("file:/" + dirName);
				else if (fileName.matches("^[a-z]*\\:/.*"))
					url = new URL(fileName);
				else {
					fileName = dirName + "/" + fileName;
					url = new URL("file:/" + fileName);
				}

				urlList.add(url);
			}

			return urlList;
		} catch (Throwable e) {
			throw new BrutosException(e);
		}

	}

	private void checkClass(String resource) {
		try {
			if (accepts(resource))
				listClass.add(ClassUtil.get(Vfs.toClass(resource)));
		} catch (Exception e) {
			throw new BrutosException(e);
		}
	}

}