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

import java.net.URL;
import java.util.List;

import org.jboss.vfs.VirtualFile;

/**
 * 
 * @author Brandao
 */
public class JbossVFSURLType implements URLType {

	public Dir toDir(URL url) throws Exception {
		VirtualFile vf = null;
		try {
			vf = (VirtualFile) url.openConnection().getContent();
			List<VirtualFile> lvf = vf.getChildrenRecursively();

			for (VirtualFile v : lvf)
				v.getPhysicalFile();

			java.io.File file = new java.io.File(vf.getPhysicalFile()
					.getParentFile(), vf.getName());

			return new JbossVFSDir(file);
		} catch (VfsException e) {
			return new JbossVFSDir(vf.getPhysicalFile());
		} catch (Throwable e) {
			throw new VfsException("could not open VirtualFile: " + url, e);
		}

	}

	public boolean matches(URL url) throws Exception {
		return url.getProtocol().equals("vfs");
	}

}
