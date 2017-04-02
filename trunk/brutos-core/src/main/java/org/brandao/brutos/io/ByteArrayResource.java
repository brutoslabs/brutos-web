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

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * 
 * @author Brandao
 */
public class ByteArrayResource extends AbstractResource {

	private byte[] byteArray;

	public ByteArrayResource(byte[] byteArray) {
		this.byteArray = byteArray;
	}

	public URL getURL() throws IOException {
		throw new FileNotFoundException(" URL does not exist");
	}

	public Resource getRelativeResource(String relativePath) throws IOException {
		throw new FileNotFoundException("Cannot create a relative resource: "
				+ relativePath);
	}

	public boolean exists() {
		return true;
	}

	public InputStream getInputStream() throws IOException {
		return new ByteArrayInputStream(byteArray);
	}

	public boolean equals(Object e) {
		return e instanceof ByteArrayResource ? ((ByteArrayResource) e).byteArray
				.hashCode() == this.byteArray.hashCode() : false;
	}

	public String getName() {
		return "Byte array";
	}

}
