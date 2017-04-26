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

import org.brandao.brutos.BrutosException;
import org.brandao.brutos.EnumerationType;
import org.brandao.brutos.ScopeType;
import org.brandao.brutos.Scopes;
import org.brandao.brutos.scope.Scope;
import org.brandao.brutos.type.Type;
import org.brandao.brutos.validator.Validator;
import org.brandao.brutos.validator.ValidatorException;

/**
 * 
 * @author Brandao
 */
public abstract class DependencyBean {

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

	public DependencyBean(Bean parent) {
		this.parent = parent;
		this.controller = parent.getController();
	}

	public String getParameterName() {
		return parameterName;
	}

	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
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

	public Class getClassType() {
		return this.mapping != null ? controller.getBean(mapping)
				.getClassType() : this.type == null ? null : this.type
				.getClassType();
	}

	public Object getValue(String prefix, long index,
			ValidatorException exceptionHandler, Object source) {
		return getValue(prefix, index, exceptionHandler, source, null);
	}

	public Object getValue(String prefix, long index, 
			ValidatorException exceptionHandler, Object source, Object value) {

		try {
			return getValue0(prefix, index, exceptionHandler, source, value);
		} catch (ValidatorException e) {
			throw e;
		} catch (Exception e) {
			throw new DependencyException(String.format(
					"problem to resolve dependency: %s",
					new Object[] { this.getParameterName() }), e);
		}

	}

	private Object getValue0(String prefix, long index, 
			ValidatorException exceptionHandler, Object source, Object value) {

		Object result;

		if (this.mapping != null) {
			Bean dependencyBean = controller.getBean(mapping);

			if (dependencyBean == null)
				throw new BrutosException("mapping not found: " + mapping);

			String newPrefix = null;
			
			if (parent.isHierarchy()) {
				String parameter = getParameterName();
				
				if (parameter != null){
					newPrefix = parameter == null ? "" : parameter;
				}
			}

			if (newPrefix != null) {
				newPrefix += 
						index < 0 ? "" : 
						parent.getIndexFormat().replace("$index", String.valueOf(index));
				
				newPrefix += parent.getSeparator();
			}

			if (prefix != null) {
				if (newPrefix != null)
					newPrefix = prefix + newPrefix;
				else
					newPrefix = prefix;
			}

			result = dependencyBean.getValue(value, newPrefix, -1,
					exceptionHandler, false);
		}
		else
		if (this.metaBean == null) {
			if (isStatic()){
				result = getValue();
			}
			else
			if(this.isNullable()){
				result = null;
			}
			else {
				String pre = prefix != null ? prefix : "";
				String param = this.getParameterName() == null? "" : this.getParameterName();
				String idx = 
						index < 0 ? "" : 
						parent.getIndexFormat().replace("$index", String.valueOf(index));
				String key = pre + param + idx;

				result = getScope().get(key);
				result = type.convert(result);
			}

			//result = isNullable() ? null : type.convert(result);
		}
		else {
			String pre   = prefix != null ? prefix : "";
			String param = this.getParameterName() == null? "" : this.getParameterName();
			String idx   = 
					index < 0 ? "" : 
					parent.getIndexFormat().replace("$index", String.valueOf(index));
			String key   = pre + param + idx + parent.getSeparator();

			result = this.metaBean.getValue(key);
			result = type.convert(result);
		}

		try {
			if (validator != null)
				this.validate(source, result);
		} catch (ValidatorException vex) {
			if (exceptionHandler == null)
				throw vex;
			else {
				exceptionHandler.addCause(vex);
				return null;
			}
		}

		return result;
	}

	public Object convert(Object value){
		return this.type.convert(value);
	}
	
	protected abstract void validate(Object source, Object value);

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
