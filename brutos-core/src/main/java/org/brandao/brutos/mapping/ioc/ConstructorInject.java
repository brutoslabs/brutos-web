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

package org.brandao.brutos.mapping.ioc;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.ClassUtil;

/**
 * 
 * @author Brandao
 */
public class ConstructorInject {

	private List args;

	private Constructor contructor;

	private Method method;

	private String methodFactory;

	private Injectable inject;

	public ConstructorInject(Injectable inject) {
		this.args = new ArrayList();
		this.inject = inject;
	}

	public ConstructorInject(Constructor constructor, Injectable[] args) {
		this.contructor = constructor;
		this.args = args.length == 0 ? new ArrayList() : Arrays.asList(args);
	}

	public void setMethodFactory(String method) {
		this.methodFactory = method;
	}

	public String getMethodFactory() {
		return this.methodFactory;
	}

	public void addArg(Injectable arg) {
		args.add(arg);
	}

	public void removeArg(Injectable arg) {
		args.remove(arg);
	}

	public Injectable getArg(int index) {
		return (Injectable) args.get(index);
	}

	public int length() {
		return args.size();
	}

	public List getArgs() {
		return args;
	}

	public void setArgs(List args) {
		this.args = args;
	}

	public boolean isConstructor() {
		return methodFactory == null;
	}

	public boolean isMethodFactory() {
		return methodFactory != null;
	}

	public Constructor getContructor() {
		if (contructor == null)
			contructor = getContructor(inject.getTarget());
		return contructor;
	}

	public void setContructor(Constructor contructor) {
		this.contructor = contructor;
	}

	public Method getMethod(Object factory) {
		if (method == null) {
			Class clazz = factory == null ? inject.getTarget() : factory
					.getClass();

			method = getMethod(methodFactory, clazz);
			if (method.getReturnType() == void.class)
				throw new BrutosException("invalid return: "
						+ method.toString());
		}
		return method;
	}

	private Constructor getContructor(Class clazz) {
		Class[] classArgs = new Class[args.size()];

		// int i=0;
		// for( Injectable arg: args ){
		for (int i = 0; i < args.size(); i++) {
			Injectable arg = (Injectable) args.get(i);
			if (arg.getTarget() != null)
				classArgs[i] = arg.getTarget();
			// i++;
		}

		// for( Constructor con: clazz.getConstructors() ){
		Constructor[] cons = clazz.getConstructors();
		for (int i = 0; i < cons.length; i++) {
			Constructor con = cons[i];
			if (isCompatible(con, classArgs))
				return con;
		}

		String msg = "not found: " + clazz.getName() + "( ";

		for (int i = 0; i < classArgs.length; i++) {
			Class arg = classArgs[i];
			msg += i != 0 ? ", " : "";
			msg += arg == null ? "?" : arg.getName();
		}
		msg += " )";

		throw new BrutosException(msg);
	}

	private Method getMethod(String name, Class clazz) {
		Class[] classArgs = new Class[args.size()];

		// int i=0;
		// for( Injectable arg: args ){
		for (int i = 0; i < args.size(); i++) {
			Injectable arg = (Injectable) args.get(i);
			if (arg.getTarget() != null)
				classArgs[i] = arg.getTarget();
			// i++;
		}

		Method[] methods = clazz.getDeclaredMethods();
		// for( Method m: clazz.getDeclaredMethods() ){
		for (int i = 0; i < methods.length; i++) {
			Method m = methods[i];
			if (m.getName().equals(name) &&

			isCompatible(m, classArgs))
				return m;
		}

		String msg = "not found: " + clazz.getName() + "( ";

		for (int i = 0; i < classArgs.length; i++) {
			Class arg = classArgs[i];
			msg += i != 0 ? ", " : "";
			msg += arg == null ? "?" : arg.getName();
		}
		msg += " )";

		throw new BrutosException(msg);
	}

	private boolean isCompatible(Constructor m, Class[] classArgs) {
		Class[] params = m.getParameterTypes();
		if (params.length == classArgs.length) {
			for (int i = 0; i < params.length; i++) {
				if (classArgs[i] != null
						&& !params[i].isAssignableFrom(classArgs[i]))
					// if( classArgs[i] != null && !ClassType.getWrapper(
					// params[i] ).isAssignableFrom( ClassType.getWrapper(
					// classArgs[i] ) ) )
					return false;
			}
			return true;
		} else
			return false;

	}

	private boolean isCompatible(Method m, Class[] classArgs) {
		Class[] params = m.getParameterTypes();
		if (params.length == classArgs.length) {
			for (int i = 0; i < params.length; i++) {
				// if( classArgs[i] != null && !params[i].isAssignableFrom(
				// classArgs[i] ) )
				if (classArgs[i] != null
						&& !ClassUtil.getWrapper(params[i]).isAssignableFrom(
								ClassUtil.getWrapper(classArgs[i])))
					return false;
			}
			return true;
		} else
			return false;

	}

}
