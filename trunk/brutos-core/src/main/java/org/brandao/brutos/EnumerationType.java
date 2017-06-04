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

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Brandao
 */
public class EnumerationType {

	public static final EnumerationType AUTO    = new EnumerationType("auto");

	public static final EnumerationType ORDINAL = new EnumerationType("ordinal");

	public static final EnumerationType STRING  = new EnumerationType("string");

	private final static Map<String, EnumerationType> defaultTypes = new HashMap<String, EnumerationType>();

	static {
		defaultTypes.put(AUTO.toString(),    AUTO);
		defaultTypes.put(ORDINAL.toString(), ORDINAL);
		defaultTypes.put(STRING.toString(),  STRING);
	}

	private String name;

	public EnumerationType(String name) {
		this.name = name;
	}

	public String toString() {
		return name;
	}

	public static EnumerationType valueOf(String value) {
		return (EnumerationType) defaultTypes.get(value);
	}

}
