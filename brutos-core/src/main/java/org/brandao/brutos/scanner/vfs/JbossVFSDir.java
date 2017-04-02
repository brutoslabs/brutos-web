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

import java.util.jar.JarFile;

/**
 * 
 * @author Brandao
 */
public class JbossVFSDir implements Dir {

	private Dir path;

	public JbossVFSDir(java.io.File file) {

		try {
			if (file != null && file.exists() && file.canRead()) {
				if (file.isDirectory())
					this.path = new SystemPath(file);
				else
					this.path = new ZipDir(null, new JarFile(file));
			} else
				throw new VfsException("can't open path: " + file);
		} catch (VfsException e) {
			throw e;
		} catch (Exception e) {
			throw new VfsException(e);
		}
	}

	public File[] getFiles() {
		return path.getFiles();
	}

	public String getPath() {
		return path.getPath();
	}

}
