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

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Brandao
 */
public class SystemPath implements Dir {

	private java.io.File root;

	public SystemPath(java.io.File root) {
		if (!isValid(root))
			throw new VfsException("can't open path: " + root);
		this.root = root;
	}

	private boolean isValid(java.io.File root) {
		return root != null && root.exists() && root.isDirectory()
				&& root.canRead();

	}

	public File[] getFiles() {
		List vFiles = new ArrayList();

		listFiles(vFiles, root);

		File[] result = new File[vFiles.size()];

		for (int i = 0; i < result.length; i++)
			result[i] = (File) vFiles.get(i);

		return result;
	}

	public void listFiles(List vFiles, java.io.File file) {

		java.io.File[] files = file.listFiles();

		for (int i = 0; i < files.length; i++) {

			java.io.File child = files[i];

			if (child.isDirectory())
				listFiles(vFiles, child);
			else
				vFiles.add(new SystemFile(this, child));
		}

	}

	public String getPath() {
		return root.getPath().replace("\\", "/").replaceAll("/+", "/");
	}

}
