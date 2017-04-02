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

import java.util.Properties;

/**
 * 
 * @author Brandao
 */
public class Configuration extends Properties {

	public Configuration() {
		super();
	}

	public Configuration(Properties props) {
		super(props);
	}

	public String getProperty(String key, String defaultValue) {
		String value = super.getProperty(key, defaultValue);
		value = (value == null) ? System.getProperty(key) : value;

		if (value != null)
			value = getVars(value);

		return value;
	}

	public String getProperty(String key) {
		String value = super.getProperty(key);
		value = (value == null) ? System.getProperty(key) : value;
		if (value != null)
			value = getVars(value);

		return value;
	}

	public String getProperty(String key, boolean insert) {
		String value = super.getProperty(key);
		value = (value == null) ? System.getProperty(key) : value;
		if (value != null && insert)
			value = getVars(value);

		return value;
	}

	private String getVars(String value) {

		int index = value.indexOf("${");

		while (index != -1) {
			int end = value.indexOf("}", index);

			if (end != -1) {
				String key = value.substring(index + 2, end);
				String prop = getProperty(key, null);
				if (prop != null)
					value = value.replace("${" + key + "}", prop);
			}
			index = value.indexOf("${", end);
		}
		return value;
	}

}
