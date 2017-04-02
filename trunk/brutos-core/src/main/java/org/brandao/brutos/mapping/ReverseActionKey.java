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

import java.lang.reflect.Method;

/**
 * 
 * @author Brandao
 */
public class ReverseActionKey {

	private String methodName;

	private Class<?>[] parametersType;

	private final int hashCode;

	public ReverseActionKey(Method method) {
		this.methodName = method.getName();
		this.parametersType = method.getParameterTypes();
		this.hashCode = createHashCode();
	}

	private int createHashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.methodName.hashCode();

		int len = this.parametersType.length;
		for (int i = 0; i < len; i++)
			result = prime * result + this.parametersType[i].hashCode();

		return result;
	}

	public String getMethodName() {
		return methodName;
	}

	public Class<?>[] getParametersType() {
		return parametersType;
	}

	public int hashCode() {
		return this.hashCode;
	}

	public boolean equals(Object o) {

		if (!(o instanceof ReverseActionKey))
			return false;

		ReverseActionKey key = ((ReverseActionKey) o);

		if (!this.methodName.equals(key.getMethodName()))
			return false;

		Class<?>[] keyTypes = key.getParametersType();
		if (this.parametersType.length != keyTypes.length)
			return false;

		int len = this.parametersType.length;
		for (int i = 0; i < len; i++) {
			if (!this.parametersType[i].equals(keyTypes[i]))
				return false;
		}

		return true;
	}
}
