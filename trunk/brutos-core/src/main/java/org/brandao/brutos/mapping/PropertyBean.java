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

import java.lang.reflect.InvocationTargetException;
import org.brandao.brutos.bean.BeanProperty;

/**
 * 
 * @author Brandao
 */
public class PropertyBean extends DependencyBean {

	private String name;

	private BeanProperty beanProperty;

	public PropertyBean(Bean mappingBean) {
		super(mappingBean);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParameterName() {
		return super.parameterName == null ? this.name : super.parameterName;
	}

	protected void validate(Object source, Object value) {
		if (this.validator != null)
			this.validator.validate(this, source, value);
	}

	public BeanProperty getBeanProperty() {
		return beanProperty;
	}

	public void setBeanProperty(BeanProperty beanProperty) {
		this.beanProperty = beanProperty;
	}

	public boolean canGet(){
		return this.beanProperty.canGet();
	}
	
	public Object getValueFromSource(Object source)
			throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		return this.beanProperty.get(source);
	}

	public boolean canSet(){
		return this.beanProperty.canSet();
	}
	
	public void setValueInSource(Object source, Object value)
			throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		this.beanProperty.set(source, value);
	}

}
