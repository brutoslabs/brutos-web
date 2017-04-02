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
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.brandao.brutos.ScopeType;

/**
 * 
 * @author Brandao
 */
public class ComplexObjectInject extends Injectable {

	private List props;
	private Class keyType;
	private Class valueType;
	private Class type;

	public ComplexObjectInject(String name, Class keyType, Class valueType,
			Class type, String factory, Property[] props) {
		super(type, name, ScopeType.valueOf("prototype"), false, factory);
		this.setKeyType(keyType == null ? String.class : keyType);
		this.setValueType(valueType == null ? String.class : valueType);
		setProps(props.length == 0 ? new ArrayList() : Arrays.asList(props));
		setSingleton(true);
	}

	public List getProps() {
		return props;
	}

	public void setProps(List props) {
		this.props = props;
	}

	public boolean isCollection() {
		return Collection.class.isAssignableFrom(getTarget());
	}

	public boolean isMap() {
		return Map.class.isAssignableFrom(getTarget());
	}

	public Class getKeyType() {
		return keyType;
	}

	public void setKeyType(Class keyType) {
		this.keyType = keyType;
	}

	public Class getValueType() {
		return valueType;
	}

	public void setValueType(Class valueType) {
		this.valueType = valueType;
	}

	public Class getType() {
		return type;
	}

	public void setType(Class type) {
		this.type = type;
	}

}
