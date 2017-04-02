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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 
 * @author Brandao
 */
public class SetterProperty {

	private Field field;
	private Object object;
	private Method method;

	public SetterProperty(Field field, Object object) {
		this.field = field;
		this.object = object;
	}

	public SetterProperty(Method method, Object object) {
		this.method = method;
		this.object = object;
	}

	public void set(Object value) throws IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		set(object, value);
	}

	public void set(Object o, Object value) throws IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		if (field == null)
			method.invoke(o, new Object[] { value });
		else
			field.set(o, value);
	}

	public Method getMethod() {
		return method;
	}

}
