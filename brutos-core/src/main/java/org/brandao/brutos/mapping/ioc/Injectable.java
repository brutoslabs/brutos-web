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

import java.util.ArrayList;
import java.util.List;
import org.brandao.brutos.ScopeType;

/**
 * 
 * @author Brandao
 */
public class Injectable {

	private Class target;

	private String name;

	private ScopeType scope;

	private boolean singleton;

	private ConstructorInject constructor;

	private List properties;

	private String factory;

	public Injectable() {
	}

	public Injectable(Class target, String name, ScopeType scope,
			boolean singleton, String factory) {
		this.target = target;
		this.name = name;
		this.scope = scope;
		this.singleton = singleton;
		this.properties = new ArrayList();
		this.factory = factory;

		this.constructor = new ConstructorInject(this);
	}

	public Class getTarget() {
		return target;
	}

	public void setTarget(Class target) {
		this.target = target;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ScopeType getScope() {
		return scope;
	}

	public void setScope(ScopeType scope) {
		this.scope = scope;
	}

	public boolean isSingleton() {
		return singleton;
	}

	public void setSingleton(boolean singleton) {
		this.singleton = singleton;
	}

	public ConstructorInject getConstructor() {
		return constructor;
	}

	public void setConstructor(ConstructorInject constructor) {
		this.constructor = constructor;
	}

	public List getProperties() {
		return properties;
	}

	public void setProperties(List properties) {
		this.properties = properties;
	}

	public boolean equals(Object o) {
		return o instanceof Injectable ? this.name
				.equals(((Injectable) o).name) : false;
	}

	public String getFactory() {
		return factory;
	}

	public void setFactory(String factory) {
		this.factory = factory;
	}
}
