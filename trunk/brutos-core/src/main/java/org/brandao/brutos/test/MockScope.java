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

package org.brandao.brutos.test;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.brandao.brutos.scope.Scope;

/**
 * 
 * @author Brandao
 */
public class MockScope implements Scope {

	private final ConcurrentMap values;

	public MockScope() {
		this.values = new ConcurrentHashMap();
	}

	public void put(String name, Object value) {
		this.values.put(name, value);
	}

	public Object get(String name) {
		return this.values.get(name);
	}

	public Object getCollection(String name) {
		return this.values.get(name);
	}

	public void remove(String name) {
		this.values.remove(name);
	}

	public List<String> getNamesStartsWith(String value) {
		return null;
	}

}
