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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * 
 * @author Brandao
 */
public class FileSystemResource extends AbstractResource {

	private String path;

	private File file;

	public FileSystemResource(String path) {
		this.path = path;
		this.file = new File(path);
	}

	public URL getURL() throws IOException {
		return new URL(ResourceLoader.FILE_URL_PREFIX
				+ this.file.getAbsolutePath());
	}

	public Resource getRelativeResource(String relativePath) throws IOException {
		return new FileSystemResource(this.createRelativePath(this.path,
				relativePath));
	}

	public InputStream getInputStream() throws IOException {
		return new FileInputStream(this.file);
	}

	public boolean exists() {
		return this.file.exists();
	}

	public boolean equals(Object e) {
		return e instanceof FileSystemResource ? ((FileSystemResource) e).path
				.equals(this.path) : false;
	}

	public String getName() {
		return this.path;
	}

}
