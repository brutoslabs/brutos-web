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

/**
 * 
 * @author Brandao
 */
public class ValueInject extends Injectable {

	private Object value;
	private boolean converted;

	public ValueInject() {
		this.converted = false;
	}

	public ValueInject(Class target, Object value) {
		super(target, null, null, false, null);
		this.value = value;
		this.converted = false;
	}

	public Object getValue() {
		return converted ? value : converter(value);
	}

	public void setValue(Object value) {
		this.value = value;
	}

	private Object converter(Object value) {

		this.converted = true;

		if (value instanceof String && getTarget() != String.class) {

			return null;
		} else
			return value;
	}

	public String toString() {
		return String.valueOf(value);
	}
}
