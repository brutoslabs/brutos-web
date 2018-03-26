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

package org.brandao.brutos.mapping;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.brandao.brutos.ClassUtil;

/**
 * 
 * @author Brandao
 *
 */
public class ParameterNameResolver {

	private static Method getParametersNameMethodByMethod;

	private static Method getParametersNameMethodByCosntructor;
	
	private static Method getName;
	
	static{
		loadParametersNameMethodByMethod();
		loadParametersNameMethodByconstructor();
		loadGetNameMethod();
	}
	
	private static void loadParametersNameMethodByMethod(){
		try{
			getParametersNameMethodByMethod = Method.class.getMethod("getParameters");
		}
		catch(NoSuchMethodException e){
			getParametersNameMethodByMethod = null;
		}
		catch(Throwable e){
			throw new ExceptionInInitializerError(e);
		}
	}

	private static void loadParametersNameMethodByconstructor(){
		try{
			getParametersNameMethodByCosntructor = Constructor.class.getMethod("getParameters");
		}
		catch(NoSuchMethodException e){
			getParametersNameMethodByCosntructor = null;
		}
		catch(Throwable e){
			throw new ExceptionInInitializerError(e);
		}
	}
	
	private static void loadGetNameMethod(){
		try{
			Class<?> clazz = ClassUtil.get("java.lang.reflect.Parameter");
			getName        = clazz.getMethod("getName");
		}
		catch(ClassNotFoundException e){
			getName = null;
		}
		catch(Throwable e){
			throw new ExceptionInInitializerError(e);
		}
	}
	
	public static String getName(Method origin, int index){
		
		try{
			if(getParametersNameMethodByMethod == null){
				return "arg" + index;
			}
			
			Object parameters = getParameters(origin);
			Object parameter = getItem(parameters, index);
			
			return (String)getParameterName(parameter);
		}
		catch(InvocationTargetException e){
			throw new MappingException(e.getCause());
		}
		catch(Throwable e){
			throw new MappingException(e);
		}
		
	}
	
	public static String getName(Constructor<?> origin, int index){
		
		try{
			if(getParametersNameMethodByCosntructor == null){
				return "arg" + index;
			}
			
			Object parameters = getParameters(origin);
			Object parameter = getItem(parameters, index);
			
			return (String)getParameterName(parameter);
		}
		catch(InvocationTargetException e){
			throw new MappingException(e.getCause());
		}
		catch(Throwable e){
			throw new MappingException(e);
		}
	}
	
	private static Object getParameters(Method method) 
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		return getParametersNameMethodByMethod.invoke(method);
	}

	private static Object getParameters(Constructor<?> constructor) 
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		return getParametersNameMethodByCosntructor.invoke(constructor);
	}
	
	private static Object getItem(Object list, int index) 
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		return Array.get(list, index);
	}

	private static Object getParameterName(Object parameter) 
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		return (String)getName.invoke(parameter);
	}
	
}
