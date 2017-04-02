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

import java.io.InputStream;
import java.util.zip.ZipEntry;

/**
 * 
 * @author Brandao
 */
public class ZipFile implements File {

	private final ZipDir root;
	private final ZipEntry entry;

	public ZipFile(ZipDir root, ZipEntry entry) {
		this.root = root;
		this.entry = entry;
	}

	public String getRelativePath() {
		if (root.getPrefixPath() != null)
			return entry.getName().substring(root.getPrefixPath().length());
		else
			return entry.getName();
	}

	public String getName() {
		String tmp = entry.getName();
		return tmp.substring(tmp.lastIndexOf("/") + 1);
	}

	public InputStream openInputStream() throws VfsException {
		try {
			return root.getZipFile().getInputStream(entry);
		} catch (Exception e) {
			throw new VfsException(e);
		}
	}

}
