^/*
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

package org.brandao.brutos;

import org.brandao.brutos.logger.Logger;
import org.brandao.brutos.logger.LoggerProvider;
import org.brandao.brutos.mapping.*;
import org.brandao.brutos.type.Type;

public class BeanBuilder {

	private Controller controller;

	private ControllerBuilder controllerBuilder;

	private Bean mappingBean;

	private ValidatorFactory validatorFactory;

	private ApplicationContext applicationContext;

	private ConstructorBuilder constructorBuilder;

	public BeanBuilder(Bean mappingBean, Controller controller,
			ControllerBuilder controllerBuilder,
			ValidatorFactory validatorFactory,
			ApplicationContext applicationContext) {

		this.controllerBuilder = controllerBuilder;
		this.mappingBean = mappingBean;
		this.controller = controller;
		this.validatorFactory = validatorFactory;
		this.applicationContext = applicationContext;
	}

	public BeanBuilder setFactory(String factory) {
		getLogger().info(
				String.format("%s defined factory %s",
						new Object[] { this.getPrefixLogger(), factory }));
		mappingBean.setFactory(factory);
		return this;
	}

	public BeanBuilder setMethodfactory(String methodFactory) {

		getLogger().info(
				String.format("%s defined method factory %s", new Object[] {
						this.getPrefixLogger(), methodFactory }));

		mappingBean.setMethodfactory(methodFactory);
		return this;
	}

	public BeanBuilder setSeparator(String separator) {

		getLogger().info(
				String.format("%s separator defined to %s",
						new Object[] { this.getPrefixLogger(), separator }));

		mappingBean.setSeparator(separator);
		return this;
	}

	public PropertyBuilder addProperty(String name, String propertyName,
			EnumerationType enumProperty) {
		return addProperty(name, propertyName, enumProperty, null, null,
				ScopeType.PARAM, null, false, null);
	}

	public PropertyBuilder addNullProperty(String propertyName) {
		return addProperty(null, propertyName, null, null, null,
				ScopeType.PARAM, null, true, null);
	}

	public PropertyBuilder addProperty(String name, String propertyName,
			String temporalProperty) {
		return addProperty(name, propertyName, EnumerationType.ORDINAL,
				temporalProperty, null, ScopeType.PARAM, null, false, null);
	}

	public PropertyBuilder addProperty(String name, String propertyName,
			Type type) {
		return addProperty(name, propertyName, EnumerationType.ORDINAL,
				"dd/MM/yyyy", null, ScopeType.PARAM, null, false, type);
	}

	public PropertyBuilder addMappedProperty(String name, String propertyName,
			String mapping) {
		return addProperty(name, propertyName, EnumerationType.ORDINAL,
				"dd/MM/yyyy", mapping, ScopeType.PARAM, null, false, null);
	}

	public PropertyBuilder addMappedProperty(String propertyName, String mapping) {
		return addProperty(null, propertyName, EnumerationType.ORDINAL,
				"dd/MM/yyyy", mapping, ScopeType.PARAM, null, false, null);
	}

	public KeyBuilder setMappedKey(String name, String ref) {
		return setKey(name, EnumerationType.ORDINAL, "dd/MM/yyyy", ref,
				ScopeType.PARAM, null, null, null);
	}

	public KeyBuilder setKey(String ref) {
		return setMappedKey(ref);
	}

	public KeyBuilder setMappedKey(String ref) {
		return setMappedKey(null, ref);
	}

	public KeyBuilder setKey(String name, EnumerationType enumProperty,
			Class classType) {
		return setKey(name, enumProperty, "dd/MM/yyyy", null, ScopeType.PARAM,
				null, null, classType);
	}

	public KeyBuilder setKey(String name, String temporalProperty,
			Class classType) {
		return setKey(name, EnumerationType.ORDINAL, temporalProperty, null,
				ScopeType.PARAM, null, null, classType);
	}

	public KeyBuilder setKey(String name, EnumerationType enumProperty,
			ScopeType scope, Class classType) {
		return setKey(name, enumProperty, "dd/MM/yyyy", null, scope, null,
				null, classType);
	}

	public KeyBuilder setKey(String name, String temporalProperty,
			ScopeType scope, Class classType) {
		return setKey(name, EnumerationType.ORDINAL, temporalProperty, null,
				scope, null, null, classType);
	}

	public KeyBuilder setKey(String name, ScopeType scope, Class classType) {
		return setKey(name, EnumerationType.ORDINAL, "dd/MM/yyyy", null, scope,
				null, null, classType);
	}

	public KeyBuilder setKey(String name, EnumerationType enumProperty,
			String temporalProperty, String mapping, ScopeType scope,
			Object value, Type typeDef, Class type) {
		return setKey(name, enumProperty, temporalProperty, mapping, scope,
				value, typeDef, (Object) type);
	}

	public KeyBuilder setGenericKey(String name, Class<?> classType) {
		return this.setKey(name, BrutosConstants.DEFAULT_ENUMERATIONTYPE,
				BrutosConstants.DEFAULT_TEMPORALPROPERTY, null,
				BrutosConstants.DEFAULT_SCOPETYPE, null, true, null,
				(Object) classType);
	}

	public KeyBuilder setKey(String name, EnumerationType enumProperty,
			String temporalProperty, String mapping, ScopeType scope,
			Object value, Type typeDef, Object type) {

		return this.setKey(name, enumProperty, temporalProperty, mapping,
				scope, value, false, typeDef, type);
	}

	public KeyBuilder setKey(String name, EnumerationType enumProperty,
			String temporalProperty, String mapping, ScopeType scope,
			Object value, boolean generic, Type typeDef, Object type) {

		name = StringUtil.adjust(name);
		name = StringUtil.isEmpty(name) ? "key" : name;

		if (type == null && mapping == null)
			throw new MappingException("unknown key type");

		if (!mappingBean.isMap())
			throw new BrutosException(String.format(
					"is not allowed for this type: %s",
					new Object[] { this.mappingBean.getClassType() }));

		DependencyBean key = MappingBeanUtil.createDependencyBean(name,
				enumProperty, temporalProperty, mapping, scope, value, false,
				generic, typeDef, type, this.mappingBean,
				this.validatorFactory, this.controller);

		((MapBean) mappingBean).setKey(key);
		return new KeyBuilder(key, this, this.validatorFactory);
	}

	public BeanBuilder buildKey(Class type) {
		return buildKey(null, type);
	}

	public BeanBuilder buildKey(String name, Class type) {

		if (!this.mappingBean.isMap())
			throw new BrutosException(String.format(
					"is not allowed for this type: %s",
					new Object[] { this.mappingBean.getClassType() }));

		String beanName = mappingBean.getName() + "#key";
		BeanBuilder bb = controllerBuilder.buildMappingBean(beanName,
				this.mappingBean.getName(), type);

		setMappedKey(name, beanName);
		return bb;
	}

	public BeanBuilder buildElement(Class type) {
		return buildElement(null, type);
	}

	public BeanBuilder buildElement(String name, Class type) {

		String beanName = mappingBean.getName() + "#bean";
		BeanBuilder bb = controllerBuilder.buildMappingBean(beanName,
				this.mappingBean.getName(), type);

		setMappedElement(name, beanName);

		return bb;
	}

	public ElementBuilder setMappedElement(String ref) {
		return setMappedElement(null, ref);
	}

	public ElementBuilder setMappedElement(String name, String ref) {
		return setElement(name, EnumerationType.ORDINAL, "dd/MM/yyyy", ref,
				ScopeType.PARAM, null, false, null, null);
	}

	public ElementBuilder setMappedElement(String name, String ref,
			Class classType) {
		return setElement(name, EnumerationType.ORDINAL, "dd/MM/yyyy", ref,
				ScopeType.PARAM, null, false, null, classType);
	}

	public ElementBuilder setElement(String name, EnumerationType enumProperty,
			Class classType) {
		return setElement(name, enumProperty, "dd/MM/yyyy", null,
				ScopeType.PARAM, null, false, null, classType);
	}

	public ElementBuilder setElement(String name, String temporalProperty,
			Class classType) {
		return setElement(name, EnumerationType.ORDINAL, temporalProperty,
				null, ScopeType.PARAM, null, false, null, classType);
	}

	public ElementBuilder setElement(String name, EnumerationType enumProperty,
			ScopeType scope, Class classType) {
		return setElement(name, enumProperty, "dd/MM/yyyy", null, scope, null,
				false, null, classType);
	}

	public ElementBuilder setElement(String name, String temporalProperty,
			ScopeType scope, Class classType) {
		return setElement(name, EnumerationType.ORDINAL, temporalProperty,
				null, scope, null, false, null, classType);
	}

	public ElementBuilder setElement(String name, ScopeType scope,
			Class classType) {
		return setElement(name, EnumerationType.ORDINAL, "dd/MM/yyyy", null,
				scope, null, false, null, classType);
	}

	public ElementBuilder setElement(String name, EnumerationType enumProperty,
			String temporalProperty, String mapping, ScopeType scope,
			Object value, boolean nullable, Type typeDef, Class type) {
		return setElement(name, enumProperty, temporalProperty, mapping, scope,
				value, nullable, typeDef, (Object) type);
	}

	public ElementBuilder setGenericElement(String name, Class<?> classType) {
		return this.setElement(name, BrutosConstants.DEFAULT_ENUMERATIONTYPE,
				BrutosConstants.DEFAULT_TEMPORALPROPERTY, null,
				BrutosConstants.DEFAULT_SCOPETYPE, null, false, true, null,
				(Object) classType);
	}

	public ElementBuilder setElement(String name, EnumerationType enumProperty,
			String temporalProperty, String mapping, ScopeType scope,
			Object value, boolean nullable, Type typeDef, Object type) {
		return this.setElement(name, enumProperty, temporalProperty, mapping,
				scope, value, nullable, false, typeDef, type);
	}

	public ElementBuilder setElement(String name, EnumerationType enumProperty,
			String temporalProperty, String mapping, ScopeType scope,
			Object value, boolean nullable, boolean generic, Type typeDef,
			Object type) {

		name = StringUtil.adjust(name);
		name = StringUtil.isEmpty(name) ? "element" : name;

		if (type == null && mapping == null)
			throw new MappingException("unknown element type");

		if (!mappingBean.isCollection() && !mappingBean.isMap())
			throw new MappingException(String.format(
					"is not allowed for this type: %s",
					new Object[] { this.mappingBean.getClassType() }));

		DependencyBean collection = MappingBeanUtil.createDependencyBean(name,
				enumProperty, temporalProperty, mapping, scope, value,
				nullable, generic, typeDef, type, this.mappingBean,
				this.validatorFactory, this.controller);

		((CollectionBean) mappingBean).setCollection(collection);
		return new ElementBuilder(collection, this, this.validatorFactory);
	}

	public BeanBuilder setIndexFormat(String indexFormat) {
		indexFormat = StringUtil.adjust(indexFormat);

		if (indexFormat == null)
			throw new IllegalArgumentException();

		if (indexFormat.indexOf("$index") == -1)
			throw new IllegalArgumentException("$index not found");

		mappingBean.setIndexFormat(indexFormat);
		return this;
	}

	public RestrictionBuilder setElement(String ref) {
		return setMappedElement(null, ref);

	}

	public BeanBuilder buildProperty(String propertyName, Class target) {
		return buildProperty(null, propertyName, target);
	}

	public BeanBuilder buildProperty(String name, String propertyName,
			Class target) {

		name = StringUtil.adjust(name);

		String beanName = this.mappingBean.getName() + "#" + propertyName;

		BeanBuilder beanBuilder = this.controllerBuilder.buildMappingBean(
				beanName, this.mappingBean.getName(), target);

		this.addMappedProperty(name, propertyName, beanName);

		return beanBuilder;
	}

	public PropertyBuilder addProperty(String name, String propertyName) {
		return addProperty(name, propertyName, EnumerationType.ORDINAL,
				"dd/MM/yyyy", null, ScopeType.PARAM, null, false, null);
	}

	public PropertyBuilder addProperty(String name, String propertyName,
			ScopeType scope) {
		return addProperty(name, propertyName, EnumerationType.ORDINAL,
				"dd/MM/yyyy", null, scope, null, false, null);
	}

	public PropertyBuilder addStaticProperty(String name, String propertyName,
			Object value) {
		return addProperty(null, propertyName, EnumerationType.ORDINAL,
				"dd/MM/yyyy", null, ScopeType.PARAM, value, false, null);
	}

	public PropertyBuilder addProperty(String name, String propertyName,
			EnumerationType enumProperty, String temporalProperty,
			String mapping, ScopeType scope, Object value, boolean nullable,
			Type type) {
		return addProperty(name, propertyName, enumProperty, temporalProperty,
				mapping, scope, value, nullable, null, type);
	}

	public PropertyBuilder addGenericProperty(String name, String propertyName,
			Class<?> classType) {
		return this.addProperty(name, propertyName,
				BrutosConstants.DEFAULT_ENUMERATIONTYPE,
				BrutosConstants.DEFAULT_TEMPORALPROPERTY, null,
				BrutosConstants.DEFAULT_SCOPETYPE, null, false, true,
				classType, null);
	}

	public PropertyBuilder addGenericProperty(String name, String propertyName) {
		return this.addProperty(name, propertyName,
				BrutosConstants.DEFAULT_ENUMERATIONTYPE,
				BrutosConstants.DEFAULT_TEMPORALPROPERTY, null,
				BrutosConstants.DEFAULT_SCOPETYPE, null, false, true, null,
				null);
	}

	public PropertyBuilder addProperty(String name, String propertyName,
			EnumerationType enumProperty, String temporalProperty,
			String mapping, ScopeType scope, Object value, boolean nullable,
			Object classType, Type type) {
		return this.addProperty(name, propertyName, enumProperty,
				temporalProperty, mapping, scope, value, nullable, false,
				classType, type);
	}

	public PropertyBuilder addProperty(String name, String propertyName,
			EnumerationType enumProperty, String temporalProperty,
			String mapping, ScopeType scope, Object value, boolean nullable,
			boolean generic, Object classType, Type type) {

		name = StringUtil.adjust(name);
		name = StringUtil.isEmpty(name) ? propertyName : name;

		PropertyBean propertyBean = (PropertyBean) MappingBeanUtil
				.createProperty(name, propertyName, enumProperty,
						temporalProperty, mapping, scope, value, nullable,
						generic, type, classType, this.mappingBean,
						this.validatorFactory, this.controller);

		getLogger().info(
				String.format("%s added property %s",
						new Object[] { this.getPrefixLogger(), propertyName }));

		Configuration validatorConfig = new Configuration();
		propertyBean.setValidator(this.validatorFactory
				.getValidator(validatorConfig));
		propertyBean.setBeanProperty(mappingBean.getBeanInstance().getProperty(
				propertyName));
		this.mappingBean.getFields().put(propertyName, propertyBean);

		return new PropertyBuilder(propertyBean, this, this.validatorFactory);
	}

	public ConstructorBuilder buildConstructor() {
		this.constructorBuilder = new ConstructorBuilder(mappingBean, this,
				validatorFactory, controller);
		return this.constructorBuilder;
	}

	public ControllerBuilder getControllerBuilder() {
		return this.controllerBuilder;
	}

	public PropertyBuilder getProperty(String name) {
		PropertyBean property = (PropertyBean) mappingBean.getFields()
				.get(name);
		return property == null ? null : new PropertyBuilder(property, this,
				this.validatorFactory);
	}

	public ConstructorArgBuilder getConstructorArg(int index) {
		ConstructorArgBean arg = mappingBean.getConstructor()
				.getConstructorArg(index);
		return new ConstructorArgBuilder(arg, this.constructorBuilder,
				this.validatorFactory);
	}

	public String getName() {
		return mappingBean.getName();
	}

	public int getConstructorArgSize() {
		return mappingBean.getConstructor().size();
	}

	public Class getClassType() {
		return mappingBean.getClassType();
	}

	public boolean isMap() {
		return this.mappingBean.isMap();
	}

	public boolean isCollection() {
		return this.mappingBean.isCollection();
	}

	protected String getPrefixLogger() {
		return this.mappingBean.getName() + ":";
	}

	protected Logger getLogger() {
		return LoggerProvider.getCurrentLoggerProvider().getLogger(
				ControllerBuilder.class);
	}

}
