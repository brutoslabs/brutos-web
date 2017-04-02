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

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.brandao.brutos.BrutosException;

/**
 * 
 * @author Brandao
 */
public class EnumUtil {

	private Class enumClass;

	private Map indexMap;

	private Map nameMap;

	public EnumUtil(Class enumClass) {
		this.enumClass = enumClass;
		this.indexMap = new HashMap();
		this.nameMap = new HashMap();

		int maxIndex = this.getEnumConstantLength();
		for (int i = 0; i < maxIndex; i++) {
			Enum e = (Enum) this.getEnumConstant(new Integer(i));
			this.indexMap.put(String.valueOf(e.ordinal()), e);
			this.nameMap.put(e.name(), e);
		}
	}

	public Object getEnumConstants() {
		return getEnumConstants(this.enumClass);
	}

	public static Object getEnumConstants(Class clazz) {
		try {
			Method m = Class.class
					.getMethod("getEnumConstants", new Class[] {});
			return m.invoke(clazz, new Object[] {});
		} catch (Exception e) {
			throw new BrutosException(e);
		}

	}

	public Object valueOf(String value) {
		return valueOf(this.enumClass, value);
	}

	public Object valueByName(String name) {
		return this.nameMap.get(name);
	}

	public Object valueByIndex(String index) {
		return this.indexMap.get(index);
	}

	public int getEnumConstantLength() {
		return getEnumConstantLength(this.enumClass);
	}

	public Object getEnumConstant(Integer index) {
		return getEnumConstant(this.enumClass, index);
	}

	public static int getEnumConstantLength(Class enumClazz) {
		Object cons = getEnumConstants(enumClazz);
		return Array.getLength(cons);
	}

	public static Object getEnumConstant(Class enumClazz, Integer index) {
		Object cons = getEnumConstants(enumClazz);
		return Array.get(cons, index.intValue());
	}

	public static Object valueOf(Class enumClazz, String value) {
		try {
			Class clazz = Class.forName("java.lang.Enum");
			Method m = clazz.getMethod("valueOf", new Class[] { Class.class,
					String.class });
			return m.invoke(clazz, new Object[] { enumClazz, value });
		} catch (Exception e) {
			throw new BrutosException(e);
		}

	}

}
