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
import java.util.Iterator;
import java.util.Map;

import org.brandao.brutos.bean.BeanInstance;
import org.brandao.brutos.logger.Logger;
import org.brandao.brutos.logger.LoggerProvider;
import org.brandao.brutos.validator.ValidatorException;

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

	public Object getValue() {
		return getValue(null);
	}

	public Object getValue(boolean force) {
		return getValue(null, null, -1, null, force);
	}

	public Object getValue(Object instance) {
		return getValue(instance, null, -1, null, false);
	}

	public Object getValue(String prefix) {
		return getValue(null, prefix, -1, null, false);
	}

	public Object getValue(Object instance, String prefix,
			ValidatorException exceptionHandler) {
		return getValue(instance, prefix, -1, exceptionHandler, false);
	}

	public Object getValue(Object instance, String prefix, long index,
			ValidatorException exceptionHandler, boolean force) {

		if (getLogger().isDebugEnabled())
			getLogger()
					.debug(String
							.format("creating instance of bean %s: %s",
									new Object[] { this.name,
											this.classType.getName() }));

		ValidatorException vex = new ValidatorException();
		Object obj;

		try {
			obj = instance == null ? this.constructor.getInstance(prefix,
					index, this.controller, vex, force) : instance;

			if (obj == null)
				return null;

			boolean exist = instance != null
					|| this.getConstructor().size() != 0
					|| (this.getConstructor().size() == 0 && fields.isEmpty())
					|| this.getConstructor().isMethodFactory();

			Iterator<PropertyBean> fds = fields.values().iterator();

			while (fds.hasNext()) {
				PropertyBean fb = fds.next();

				boolean existProperty = resolveAndSetProperty(fb, obj, prefix,
						index, vex);

				if (!exist && (existProperty || fb.isNullable()))
					exist = true;

			}

			if (exist || force) {
				if (exceptionHandler == null) {
					if (!vex.getCauses().isEmpty())
						throw vex;
					else
						return obj;
				} else {
					exceptionHandler.addCauses(vex.getCauses());
					return obj;
				}
			} else
				return null;

		} catch (ValidatorException e) {
			throw e;
		} catch (Throwable e) {
			throw new MappingException(String.format(
					"problem to create new instance of bean %s",
					new Object[] { this.getName() }), e);
		}
	}

	private boolean resolveAndSetProperty(PropertyBean fb, Object instance,
			String prefix, long index, ValidatorException vex) {

		try{
			
			if(!fb.canSet()){
				return false;
			}
			
			Object property = fb.getValueFromSource(instance);
			Object value = fb.getValue(prefix, index, vex, instance, property);

			if (getLogger().isDebugEnabled())
				getLogger().debug(
						String.format("binding %s to property: %s",
								new Object[] { value, fb.getName() }));

			fb.setValueInSource(instance, value);
			return value != null;
		}
		catch (DependencyException e) {
			throw e;
		}
		catch (Throwable e) {
			throw new DependencyException(String.format(
					"problem to resolve dependency: %s",
					new Object[] { fb.getParameterName() }), e);
		}
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

	private Logger getLogger() {
		return LoggerProvider.getCurrentLoggerProvider().getLogger(Bean.class);
	}

	public Bean getParent() {
		return parent;
	}

	public void setParent(Bean parent) {
		this.parent = parent;
	}

}
