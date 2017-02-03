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

package org.brandao.brutos;

import java.io.OutputStream;
import java.util.Locale;
import java.util.Map;
import javax.swing.JOptionPane;

public class DefaultMvcResponse implements MvcResponse {

	public void process(Object object) {
		JOptionPane.showMessageDialog(null, String.valueOf(object));
	}

	public OutputStream processStream() {
		return null;
	}

	public void process(Object object, Map config, Map info) {
	}

	public OutputStream processStream(Map config, Map info) {
		return null;
	}

	public void setInfo(String name, String value) {
	}

	public String getType() {
		return null;
	}

	public int getLength() {
		return -1;
	}

	public String getCharacterEncoding() {
		return null;
	}

	public Locale getLocale() {
		return null;
	}

	public void setLocale(Locale value) {
	}

	public void setType(String value) {
	}

	public void setLength(int value) {
	}

	public void setCharacterEncoding(String value) {
	}

}
