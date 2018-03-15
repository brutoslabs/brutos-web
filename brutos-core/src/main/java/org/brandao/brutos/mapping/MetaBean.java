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

import java.util.HashMap;
import java.util.Map;

import org.brandao.brutos.ScopeType;
import org.brandao.brutos.Scopes;
import org.brandao.brutos.scope.Scope;
import org.brandao.brutos.type.Type;

/**
 * 
 * @author Brandao
 */
public class MetaBean extends Bean {

	private Map<Object, DependencyBean> metaValues;

	private Type type;

	private ScopeType scopeType;

	public MetaBean(Controller controller) {
		super(controller);
		this.metaValues = new HashMap<Object, DependencyBean>();
	}

	public void putMetaValue(String value, DependencyBean bean) {
		Object metaValue = this.type.convert(value);

		if (metaValue == null)
			throw new MappingException("invalid meta value: " + value);

		this.putMetaValue(metaValue, bean);
	}

	public void putMetaValue(Object value, DependencyBean bean) {
		if (this.metaValues.put(value, bean) != null)
			throw new MappingException("duplicate meta value: " + value);
	}

	public void removeMetaValue(Object value) {
		this.metaValues.remove(value);
	}

	public void removeMetaValue(String value) {
		Object metaValue = this.type.convert(value);
		this.metaValues.remove(metaValue);
	}

	public Map<Object, DependencyBean> getMetaValues() {
		return metaValues;
	}

	public Scope getScope() {
		return Scopes.getCurrentScope(scopeType);
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public ScopeType getScopeType() {
		return scopeType;
	}

	public void setScopeType(ScopeType scopeType) {
		this.scopeType = scopeType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSize() {
		return this.metaValues.size();
	}

}
