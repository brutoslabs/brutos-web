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
public class ScopeType {

	public static final ScopeType REQUEST = new ScopeType("request");
	public static final ScopeType SINGLETON = new ScopeType("singleton");
	public static final ScopeType PARAM = new ScopeType("param");
	public static final ScopeType THREAD = new ScopeType("thread");
	public static final ScopeType IOC = new ScopeType("ioc");
	public static final ScopeType CONTROLLER = new ScopeType("controller");

	protected final static Map<String,ScopeType> defaultScopes = new HashMap<String,ScopeType>();

	static {
		defaultScopes.put(REQUEST.toString(),    REQUEST);
		defaultScopes.put(THREAD.toString(),     THREAD);
		defaultScopes.put(PARAM.toString(),      PARAM);
		defaultScopes.put(SINGLETON.toString(),  SINGLETON);
		defaultScopes.put(IOC.toString(),        IOC);
		defaultScopes.put(CONTROLLER.toString(), CONTROLLER);
	}

	private String name;

	public ScopeType(String name) {
		this.name = name;
	}

	public String toString() {
		return this.name;
	}

	public static ScopeType valueOf(String value) {
		if (defaultScopes.containsKey(value))
			return (ScopeType) defaultScopes.get(value);
		else
			return new ScopeType(value);
	}
}
