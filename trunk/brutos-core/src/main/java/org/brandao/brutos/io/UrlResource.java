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

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class UrlResource extends AbstractResource {

	private String path;

	private URL url;

	public UrlResource(String path) throws MalformedURLException {
		this.path = path;
		this.url = new URL(path);
	}

	public UrlResource(URL url) throws MalformedURLException {
		this.url = url;
		this.path = url.toString();
	}

	public URL getURL() throws IOException {
		return new URL(this.path);
	}

	public Resource getRelativeResource(String relativePath) throws IOException {
		return new UrlResource(new URL(this.url, relativePath));
	}

	public boolean exists() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public InputStream getInputStream() throws IOException {
		URLConnection con = this.url.openConnection();
		con.setUseCaches(false);
		return con.getInputStream();
	}

	public boolean equals(Object e) {
		return e instanceof UrlResource ? ((UrlResource) e).path
				.equals(this.path) : false;
	}

	public String getName() {
		return this.path;
	}

}
