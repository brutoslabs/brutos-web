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

package org.brandao.brutos.annotation.configuration;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 *
 * @author Brandao
 */
public class ActionEntry {

	private String name;

	private Annotation[] annotation;

	private Class<?>[] exceptionTypes;

	private Class<?> controllerClass;

	private Type[] genericParameterTypes;

	private Class<?>[] parameterTypes;

	private Annotation[][] parameterAnnotations;

	private Class<?> returnType;

	@Deprecated
	private String view;

	@Deprecated
	private String dispatcher;

	private boolean abstractAction;

	private ResultActionEntry resultAction;
	
	public ActionEntry(Method method) {
		this(method.getName(), method.getAnnotations(), method
				.getDeclaringClass(), method.getExceptionTypes(), method
				.getGenericParameterTypes(), method.getParameterTypes(), method
				.getReturnType(), method.getParameterAnnotations(), null, null,
				new ResultActionEntry(method),
				false);
	}

	public ActionEntry(String name, Annotation[] annotation,
			Class<?> controllerClass, Class<?>[] exceptionTypes,
			Type[] genericParameterTypes, Class<?>[] parameterTypes,
			Class<?> returnType, Annotation[][] parameterAnnotations,
			String view, String dispatcher, ResultActionEntry resulrAction,
			boolean abstractAction) {
		this.name = name;
		this.annotation = annotation;
		this.exceptionTypes = exceptionTypes;
		this.controllerClass = controllerClass;
		this.genericParameterTypes = genericParameterTypes;
		this.parameterTypes = parameterTypes;
		this.parameterAnnotations = parameterAnnotations;
		this.abstractAction = abstractAction;
		this.dispatcher = dispatcher;
		this.view = view;
		this.returnType = returnType;
		this.resultAction = resulrAction;
	}

	public boolean isAnnotationPresent(Class<? extends Annotation> annotation) {
		if (this.annotation == null)
			return false;

		for (Annotation a : this.annotation) {
			if (a.annotationType().isAssignableFrom(annotation))
				return true;
		}

		return false;
	}

	@SuppressWarnings("unchecked")
	public <T> T getAnnotation(Class<T> annotation) {
		if (this.annotation == null)
			return null;

		for (Annotation a : this.annotation) {
			if (a.annotationType().isAssignableFrom(annotation))
				return (T) a;
		}

		return null;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Annotation[] getAnnotation() {
		return annotation;
	}

	public void setAnnotation(Annotation[] annotation) {
		this.annotation = annotation;
	}

	public void setExceptionTypes(Class<?>[] exceptionTypes) {
		this.exceptionTypes = exceptionTypes;
	}

	public Class<?>[] getExceptionTypes() {
		return this.exceptionTypes;
	}

	public Class<?> getControllerClass() {
		return controllerClass;
	}

	public void setControllerClass(Class<?> controllerClass) {
		this.controllerClass = controllerClass;
	}

	public Type[] getGenericParameterTypes() {
		return genericParameterTypes;
	}

	public void setGenericParameterTypes(Type[] genericTypes) {
		this.genericParameterTypes = genericTypes;
	}

	public Class<?>[] getParameterTypes() {
		return parameterTypes;
	}

	public void setParameterTypes(Class<?>[] parameterTypes) {
		this.parameterTypes = parameterTypes;
	}

	public Annotation[][] getParameterAnnotations() {
		return parameterAnnotations;
	}

	public void setParameterAnnotations(Annotation[][] parameterAnnotations) {
		this.parameterAnnotations = parameterAnnotations;
	}

	@Deprecated
	public String getView() {
		return view;
	}

	@Deprecated
	public void setView(String view) {
		this.view = view;
	}

	@Deprecated
	public String getDispatcher() {
		return dispatcher;
	}

	@Deprecated
	public void setDispatcher(String dispatcher) {
		this.dispatcher = dispatcher;
	}

	public boolean isAbstractAction() {
		return abstractAction;
	}

	public void setAbstractAction(boolean abstractAction) {
		this.abstractAction = abstractAction;
	}

	public Class<?> getReturnType() {
		return returnType;
	}

	public void setReturnType(Class<?> returnType) {
		this.returnType = returnType;
	}

	public ResultActionEntry getResultAction() {
		return resultAction;
	}

	public void setResultAction(ResultActionEntry resultAction) {
		this.resultAction = resultAction;
	}

}
