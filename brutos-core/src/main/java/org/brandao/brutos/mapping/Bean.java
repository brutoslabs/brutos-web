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

import org.brandao.brutos.bean.BeanInstance;

/**
 * 
 * @author Brandao
 */
public class Bean {

	protected Bean parent;

	protected Controller controller;

	protected String name;

	protected Class<?> classType;

	protected Map<String, PropertyBean> fields;

	protected boolean hierarchy;

	protected String separator;

	protected ConstructorBean constructor;

	protected String factory;

	protected String indexFormat;

	protected BeanInstance beanInstance;

	protected boolean isAbstract;
	
	public Bean(Controller controller) {
		this(controller, null);
	}

	public Bean(Controller controller, Bean parent) {
		this.fields 		= new HashMap<String, PropertyBean>();
		this.controller 	= controller;
		this.hierarchy 		= true;
		this.separator 		= ".";
		this.indexFormat 	= "[$index]";
		this.parent 		= parent;
		this.constructor 	= new ConstructorBean(this);
		this.isAbstract 	= false;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Class<?> getClassType() {
		return classType;
	}

	public void setClassType(Class<?> classType) {
		this.classType = classType;
		if (classType != null)
			this.beanInstance = new BeanInstance(null, classType);
		else
			this.beanInstance = null;
	}

	public Map<String, PropertyBean> getFields() {
		return fields;
	}

	public void setFields(Map<String, PropertyBean> fields) {
		this.fields = fields;
	}
	
	public boolean isBean() {
		return true;
	}

	public boolean isCollection() {
		return false;
	}

	public boolean isMap() {
		return false;
	}

	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	public void setAbstract(boolean value){
		this.isAbstract = value;
	}

	public boolean isAbstract(){
		return this.isAbstract;
	}
	
	public boolean isHierarchy() {
		return hierarchy;
	}

	public void setMethodfactory(String methodFactory) {
		getConstructor().setMethodFactory(methodFactory);
	}

	public String getMethodfactory() {
		return getConstructor().getMethodFactory();
	}

	public void setHierarchy(boolean hierarchy) {
		this.hierarchy = hierarchy;
	}

	public String getSeparator() {
		return separator;
	}

	public void setSeparator(String separator) {
		this.separator = separator;
	}

	public ConstructorBean getConstructor() {
		return constructor;
	}

	public void setConstructor(ConstructorBean constructor) {
		this.constructor = constructor;
	}

	public String getFactory() {
		return factory;
	}

	public void setFactory(String factory) {
		this.factory = factory;
	}

	public String getIndexFormat() {
		return indexFormat;
	}

	public void setIndexFormat(String indexFormat) {
		this.indexFormat = indexFormat;
	}

	public PropertyBean getProperty(String name) {
		return (PropertyBean) this.fields.get(name);
	}

	public BeanInstance getBeanInstance() {
		return beanInstance;
	}

	public void setBeanInstance(BeanInstance beanInstance) {
		this.beanInstance = beanInstance;
	}

	public Bean getParent() {
		return parent;
	}

	public void setParent(Bean parent) {
		this.parent = parent;
	}

}
