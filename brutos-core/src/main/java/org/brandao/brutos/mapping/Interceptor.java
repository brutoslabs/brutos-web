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

import java.util.Map;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.ObjectFactory;

/**
 * 
 * @author Brandao
 */
public class Interceptor {

	private String name;

	private Class<?> type;

	private Map<String, Object> properties;

	boolean def;

	private Interceptor parent;

	public Interceptor(Interceptor parent) {
		this.parent = parent;
	}

	public Interceptor() {
	}

	public Object getInstance(ObjectFactory objectFactory) {
		Object instance = getName() == null ? null : objectFactory
				.getBean(getName());
		instance = instance == null ? objectFactory.getBean(getType())
				: instance;

		if (instance == null)
			throw new BrutosException("can't get instance " + getName() + ":"
					+ getType());
		else
			return instance;
	}

	public String getName() {
		return parent == null ? name : parent.getName();
	}

	public void setName(String name) {
		this.name = name;
	}

	public Class<?> getType() {
		return parent == null ? type : parent.getType();
	}

	public void setType(Class<?> type) {
		this.type = type;
	}

	public Map<String, Object> getProperties() {
		return properties;
	}

	public void setProperty(String name, Object value) {
		checkProperty(name, this);
		properties.put(name, value);
	}

	public void setProperties(Map<String, Object> properties) {
		this.properties = properties;
	}

	public Object getProperty(String name) {
		Object value = properties.get(name);
		value = value == null && parent != null ? parent.getProperty(name)
				: value;
		return value;
	}

	public void setDefault(boolean value) {
		this.def = value;
	}

	public boolean isDefault() {
		return parent == null ? this.def : parent.isDefault();
	}

	protected void checkProperty(String name, Interceptor stack) {
		if (name == null)
			throw new BrutosException("parameter name must be informed");

		if (!name.matches("([a-zA-Z0-9_]+)"))
			throw new BrutosException("invalid parameter name: " + name);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((getName() == null) ? 0 : getName().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Interceptor other = (Interceptor) obj;
		if (getName() == null) {
			if (other.getName() != null)
				return false;
		} else if (!getName().equals(other.getName()))
			return false;
		return true;
	}

}
