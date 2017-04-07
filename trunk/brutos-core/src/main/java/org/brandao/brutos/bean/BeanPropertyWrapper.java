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
public class BeanPropertyWrapper implements BeanProperty {

	protected BeanProperty beanProperty;

	public BeanPropertyWrapper(BeanProperty beanProperty) {
		this.beanProperty = beanProperty;
	}

	public boolean canSet() {
		return this.beanProperty.canSet();
	}
	
	public void set(Object o, Object value) throws IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		this.beanProperty.set(o, value);
	}

	public boolean canGet() {
		return this.beanProperty.canGet();
	}
	
	public Object get(Object o) throws IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		return this.beanProperty.get(o);
	}

	public Object getGenericType() {
		return this.beanProperty.getGenericType();
	}

	public Class getType() {
		return this.beanProperty.getType();
	}

	public Field getField() {
		return this.beanProperty.getField();
	}

	public void setField(Field field) {
		this.beanProperty.setField(field);
	}

	public Method getSet() {
		return this.beanProperty.getSet();
	}

	public void setSet(Method set) {
		this.beanProperty.setSet(set);
	}

	public Method getGet() {
		return this.beanProperty.getGet();
	}

	public void setGet(Method get) {
		this.beanProperty.setGet(get);
	}

	public String getName() {
		return this.beanProperty.getName();
	}

	public void setName(String name) {
		this.beanProperty.setName(name);
	}

	public Object getDeclaredGenericType() {
		return this.beanProperty.getGenericType();
	}

	public Class getDeclaredType() {
		return this.beanProperty.getType();
	}

}
