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
public interface BeanProperty {

	boolean canSet();
	
	void set(Object o, Object value) throws IllegalAccessException,
			IllegalArgumentException, InvocationTargetException;

	boolean canGet();
	
	Object get(Object o) throws IllegalAccessException,
			IllegalArgumentException, InvocationTargetException;

	Object getDeclaredGenericType();

	Class<?> getDeclaredType();

	Object getGenericType();

	Class<?> getType();

	Field getField();

	void setField(Field field);

	Method getSet();

	void setSet(Method set);

	Method getGet();

	void setGet(Method get);

	String getName();

	void setName(String name);

}
