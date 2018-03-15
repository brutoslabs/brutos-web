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
public class PropertyController extends UseBeanData {

	private boolean request;

	private boolean response;

	private boolean persistenceContext;

	private String propertyName;

	private BeanProperty beanProperty;

	private Controller controller;

	public PropertyController() {
	}

	public boolean isRequest() {
		return request;
	}

	public void setRequest(boolean request) {
		this.request = request;
	}

	public boolean isResponse() {
		return response;
	}

	public void setResponse(boolean response) {
		this.response = response;
	}

	public boolean isPersistenceContext() {
		return persistenceContext;
	}

	public void setPersistenceContext(boolean persistenceContext) {
		this.persistenceContext = persistenceContext;
	}

	public boolean equals(Object o) {
		return o instanceof PropertyController ? ((PropertyController) o).propertyName
				.equals(propertyName) : false;
	}

	protected void validate(Object source, Object value) {
		this.validate.validate(this, source, value);
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

	public void setValueInSource(Object source, Object value)
			throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		this.beanProperty.set(source, value);
	}

	public boolean canSet(){
		return this.beanProperty.canSet();
	}
	
	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

}
