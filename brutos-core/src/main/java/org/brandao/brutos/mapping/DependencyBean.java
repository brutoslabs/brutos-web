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

import org.brandao.brutos.EnumerationType;
import org.brandao.brutos.FetchType;
import org.brandao.brutos.ScopeType;
import org.brandao.brutos.Scopes;
import org.brandao.brutos.scope.Scope;
import org.brandao.brutos.type.Type;
import org.brandao.brutos.validator.Validator;

/**
 * 
 * @author Brandao
 */
public abstract class DependencyBean {

	protected String realName;
	
	protected String parameterName;

	protected Type type;

	protected String mapping;

	protected MetaBean metaBean;

	protected EnumerationType enumProperty;

	protected String temporalType;

	private ScopeType scopeType;

	protected Validator validator;

	protected Object value;

	protected Bean parent;

	protected boolean nullable;

	protected Controller controller;

	protected FetchType fetchType;
	
	public DependencyBean(Bean parent) {
		this.parent = parent;
		this.controller = parent.getController();
		this.fetchType = FetchType.EAGER;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getParameterName() {
		return parameterName;
	}

	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}

	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	public FetchType getFetchType() {
		return fetchType;
	}

	public void setFetchType(FetchType fetchType) {
		this.fetchType = fetchType;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getMapping() {
		return mapping;
	}

	public void setMapping(String mapping) {
		this.mapping = mapping;
	}

	public EnumerationType getEnumProperty() {
		return enumProperty;
	}

	public void setEnumProperty(EnumerationType enumProperty) {
		this.enumProperty = enumProperty;
	}

	public String getTemporalType() {
		return temporalType;
	}

	public void setTemporalType(String temporalType) {
		this.temporalType = temporalType;
	}

	public Validator getValidator() {
		return validator;
	}

	public void setValidator(Validator validator) {
		this.validator = validator;
	}

	public boolean isStatic() {
		return getValue() != null;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public Bean getParent() {
		return parent;
	}

	public void setParent(Bean parent) {
		this.parent = parent;
	}

	public Scope getScope() {
		return Scopes.getCurrentScope(scopeType);
	}

	public Class<?> getClassType() {
		return this.mapping != null ? controller.getBean(mapping)
				.getClassType() : this.type == null ? null : this.type
				.getClassType();
	}

	public Object convert(Object value){
		return this.type.convert(value);
	}
	
	public boolean isNullable() {
		return nullable;
	}

	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}

	public Bean getBean() {
		return controller.getBean(mapping);
	}

	public ScopeType getScopeType() {
		return scopeType;
	}

	public void setScopeType(ScopeType scopeType) {
		this.scopeType = scopeType;
	}

	public MetaBean getMetaBean() {
		return metaBean;
	}

	public void setMetaBean(MetaBean metaBean) {
		this.metaBean = metaBean;
	}

}
