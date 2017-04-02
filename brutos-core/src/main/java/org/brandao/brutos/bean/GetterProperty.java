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

package org.brandao.brutos.bean;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 
 * @author Brandao
 */
public class GetterProperty {

	private Field field;
	private Object object;
	private Method method;

	public GetterProperty(Field field, Method method, Object object) {

	}

	public GetterProperty(Field field, Object object) {
		this.field = field;
		this.object = object;
	}

	public GetterProperty(Method method, Object object) {
		this.method = method;
		this.object = object;
	}

	public Object get(Object o) throws Exception {
		return field != null ? field.get(o) : method.invoke(o, new Object[] {});
	}

	public Object get() throws Exception {
		return get(object);
	}

	public Method getMethod() {
		return method;
	}

	public Field getField() {
		return this.field;
	}

	public void setField(Field field) {
		this.field = field;
	}

	public void setMethod(Method method) {
		this.method = method;
	}
}
