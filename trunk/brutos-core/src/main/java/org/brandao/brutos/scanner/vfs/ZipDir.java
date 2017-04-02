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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

/**
 * 
 * @author Brandao
 */
public class ZipDir implements CloseableDir {

	private java.util.zip.ZipFile file;
	private String prefix;

	public ZipDir(String prefix, JarFile file) {
		this.file = file;
		this.prefix = prefix;
	}

	public String getPrefixPath() {
		return this.prefix;
	}

	public void close() throws IOException {
		try {
			file.close();
		} catch (IOException e) {
		}
	}

	public File[] getFiles() {
		List result = new ArrayList();
		Enumeration entries = file.entries();
		while (entries.hasMoreElements()) {
			ZipEntry entry = (ZipEntry) entries.nextElement();
			if (!entry.isDirectory()) {
				if ((prefix == null || entry.getName().startsWith(prefix))) {
					result.add(new ZipFile(this, entry));
				}
			}
		}

		File[] files = new File[result.size()];

		for (int i = 0; i < files.length; i++)
			files[i] = (File) result.get(i);

		return files;
	}

	public String getPath() {
		return file.getName();
	}

	public java.util.zip.ZipFile getZipFile() {
		return file;
	}
}
