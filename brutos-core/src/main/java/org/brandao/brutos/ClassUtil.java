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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.ConcurrentMap;

import org.brandao.brutos.type.TypeUtil;

/**
 * 
 * @author Brandao
 */
public final class ClassUtil {

	private final static Map<Object, Type> primitiveType;

	static {
		primitiveType = new HashMap<Object, Type>();
		primitiveType.put("boolean", java.lang.Boolean.TYPE);
		primitiveType.put("byte", java.lang.Byte.TYPE);
		primitiveType.put("char", java.lang.Character.TYPE);
		primitiveType.put("double", java.lang.Double.TYPE);
		primitiveType.put("float", java.lang.Float.TYPE);
		primitiveType.put("int", java.lang.Integer.TYPE);
		primitiveType.put("long", java.lang.Long.TYPE);
		primitiveType.put("short", java.lang.Short.TYPE);
		primitiveType.put("void", java.lang.Void.TYPE);

		primitiveType.put(boolean.class, java.lang.Boolean.class);
		primitiveType.put(byte.class, java.lang.Byte.class);
		primitiveType.put(char.class, java.lang.Character.class);
		primitiveType.put(double.class, java.lang.Double.class);
		primitiveType.put(float.class, java.lang.Float.class);
		primitiveType.put(int.class, java.lang.Integer.class);
		primitiveType.put(long.class, java.lang.Long.class);
		primitiveType.put(short.class, java.lang.Short.class);
		primitiveType.put(void.class, java.lang.Void.class);
	}

	public static Class<?> getWrapper(Class<?> clazz) {
		Class<?> classe = (Class<?>) primitiveType.get(clazz);

		return classe == null ? clazz : classe;

	}

	public static Class<?> get(String name) throws ClassNotFoundException {
		Class<?> classe = (Class<?>) primitiveType.get(name);

		return classe == null ? getClasse(name) : classe;
	}

	private static Class<?> getClasse(String name) throws ClassNotFoundException {
		return getClasse(name, true);
	}

	private static Class<?> getClasse(String name, boolean initialize)
			throws ClassNotFoundException {
		return Class.forName(name, initialize, Thread.currentThread()
				.getContextClassLoader());
	}

	public static Object getInstance(Class<?> clazz, Class<?>[] params,
			Object[] values) throws NoSuchMethodException,
			InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		Constructor<?> cons = clazz.getConstructor(params);
		return cons.newInstance(values);
	}

	public static <T> T getInstance(Class<T> clazz)
			throws InstantiationException, IllegalAccessException {
		return clazz.newInstance();
	}

	public static Object getInstance(String name)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		return getClasse(name).newInstance();
	}

	public static List<?> getListInstance() throws ClassNotFoundException,
			InstantiationException, IllegalAccessException {
		return (List<?>) getInstance(TypeUtil.getDefaultListType());
	}

	public static ConcurrentMap<?,?> getConcurrentMapInstance() throws ClassNotFoundException,
		InstantiationException, IllegalAccessException {
		return (ConcurrentMap<?,?>) getInstance(TypeUtil.getDefaultConcurrentMapType());
	}

	public static Map<?,?> getMapInstance() throws ClassNotFoundException,
			InstantiationException, IllegalAccessException {
		return (Map<?,?>) getInstance(TypeUtil.getDefaultMapType());
	}

	public static Set<?> getSetInstance() throws ClassNotFoundException,
			InstantiationException, IllegalAccessException {
		return (Set<?>) getInstance(TypeUtil.getDefaultSetType());
	}

	public static Class<?> getInstantiableClass(Class<?> clazz) {

		if (clazz == ConcurrentMap.class)
			return TypeUtil.getDefaultConcurrentMapType();
		else
		if (clazz == Map.class)
			return TypeUtil.getDefaultMapType();
		else if (clazz == List.class)
			return TypeUtil.getDefaultListType();
		else if (clazz == Set.class)
			return TypeUtil.getDefaultSetType();
		else
			return clazz;
	}

	public static boolean existClass(String className) {
		try {
			Class.forName(className, false, Thread.currentThread()
					.getContextClassLoader());
			return true;
		} catch (ClassNotFoundException e) {
			return false;
		}
	}
}
