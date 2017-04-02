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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * 
 * @author Brandao
 */
public class SystemFile implements File {

	private Dir path;
	private java.io.File file;

	public SystemFile(Dir path, java.io.File file) {
		this.path = path;
		this.file = file;
	}

	public String getRelativePath() {
		String relativePath = file.getPath().replace("\\", "/")
				.replaceAll("/+", "/");
		String rootPath = path.getPath();
		if (relativePath.startsWith(rootPath))
			return relativePath.substring(rootPath.length() + 1);
		else
			throw new VfsException();
	}

	public String getName() {
		return file.getName();
	}

	public InputStream openInputStream() throws VfsException {
		try {
			return new FileInputStream(file);
		} catch (FileNotFoundException ex) {
			throw new VfsException(ex);
		}
	}

}
