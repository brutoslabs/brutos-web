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

package org.brandao.brutos.scope;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 
 * @author Brandao
 */
public class ThreadScope implements Scope {

	private static final ThreadLocal<ConcurrentMap<String, Object>> threadLocal;

	static {
		threadLocal = new ThreadLocal<ConcurrentMap<String, Object>>();
	}

	public ThreadScope() {
	}

	public static boolean create() {

		if (threadLocal.get() == null) {
			threadLocal.set(new ConcurrentHashMap<String, Object>());
			return true;
		} else
			return false;

	}

	public static void destroy() {
		threadLocal.remove();
	}

	public void put(String name, Object value) {
		ConcurrentMap<String, Object> map = 
				threadLocal.get();
		map.put(name, value);
	}

	public Object get(String name) {
		ConcurrentMap<String, Object> map = 
				threadLocal.get();
		return map.get(name);
	}

	public Object getCollection(String name) {
		return get(name);
	}

	public void remove(String name) {
		ConcurrentMap<String, Object> map = 
				threadLocal.get();
		map.remove(name);
	}

	public List<String> getNamesStartsWith(String value) {
		ConcurrentMap<String, Object> map = 
				threadLocal.get();
		
		List<String> result = new ArrayList<String>();
		for(String k: map.keySet()){
			if(k.startsWith(value)){
				result.add(k);
			}
		}
		return result;
	}

}
