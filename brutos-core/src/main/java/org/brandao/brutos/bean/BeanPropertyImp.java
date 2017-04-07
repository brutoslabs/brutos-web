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
public class BeanPropertyImp implements BeanProperty {

	private Field field;
	private Method set;
	private Method get;
	private String name;

	public BeanPropertyImp(Field field, Method set, Method get, String name) {
		this.field = field;
		this.set = set;
		this.get = get;
		this.name = name;
	}

	public boolean canSet(){
		return this.set != null || this.field != null;
	}
	
	public void set(Object o, Object value) throws IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		if (set != null)
			set.invoke(o, new Object[] { value });
		else {
			field.setAccessible(true);
			field.set(o, value);
		}
	}

	public boolean canGet(){
		return this.get != null || this.field != null;
	}
	
	public Object get(Object o) throws IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		if (get != null)
			return get.invoke(o, new Object[] {});
		else {
			field.setAccessible(true);
			return field.get(o);
		}
	}

	public Object getGenericType() {
		try {
			if (get != null)
				return getGenericReturnType(get);
			else
				return getGenericType(field);
		} catch (NoSuchMethodException e) {
			return this.getType();
		} catch (Exception e) {
			throw new BeanException(e);
		}

	}

	private Object getGenericType(Field field) throws NoSuchMethodException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {

		Class fieldClass = field.getClass();
		Method getGenericReturnType = fieldClass.getMethod("getGenericType",
				new Class[] {});

		return getGenericReturnType.invoke(field, new Object[] {});
	}

	private Object getGenericReturnType(Method method)
			throws NoSuchMethodException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		Class methodClass = method.getClass();
		Method getGenericReturnType = methodClass.getMethod(
				"getGenericReturnType", new Class[] {});

		return getGenericReturnType.invoke(method, new Object[] {});
	}

	public Class getType() {

		if (get != null)
			return getReturnType(get);
		else
			return getType(field);

	}

	private Class getType(Field field) {
		return field.getType();
	}

	private Class getReturnType(Method method) {
		return method.getReturnType();
	}

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}

	public Method getSet() {
		return set;
	}

	public void setSet(Method set) {
		this.set = set;
	}

	public Method getGet() {
		return get;
	}

	public void setGet(Method get) {
		this.get = get;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object getDeclaredGenericType() {
		return this.getGenericType();
	}

	public Class getDeclaredType() {
		return this.getType();
	}

}
