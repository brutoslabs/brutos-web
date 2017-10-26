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

package org.brandao.brutos;

import org.brandao.brutos.mapping.DependencyBean;
import org.brandao.brutos.mapping.MappingBeanUtil;
import org.brandao.brutos.mapping.MappingException;
import org.brandao.brutos.mapping.MetaBean;
import org.brandao.brutos.mapping.StringUtil;
import org.brandao.brutos.type.DateTimeType;
import org.brandao.brutos.type.EnumType;
import org.brandao.brutos.type.Type;

/**
 * 
 * @author Brandao
 */
public class MetaBeanBuilder {

	private MetaBean metaBean;

	private EnumerationType enumProperty;

	private String temporalProperty;

	private ValidatorFactory validatorFactory;

	private ControllerBuilder controller;

	private String origin;

	public MetaBeanBuilder(MetaBean metaBean, String name, ScopeType scope,
			EnumerationType enumProperty, String temporalProperty,
			Class<?> classType, Type type, ValidatorFactory validatorFactory,
			ControllerBuilder controller, String origin) {
		this.metaBean = metaBean;
		this.enumProperty = enumProperty;
		this.temporalProperty = temporalProperty;
		this.validatorFactory = validatorFactory;
		this.controller = controller;

		if (type == null) {
			type = this.metaBean
					.getController()
					.getContext()
					.getTypeManager()
					.getType(classType, this.enumProperty,
							this.temporalProperty);
		}

		this.setScope(scope);
		this.setName(name);
		this.setClassType(classType);
		this.setType(type);
	}

	public MetaBeanBuilder addMetaValue(Object value, String mapping) {
		return this.addMetaValue(value,
				BrutosConstants.DEFAULT_ENUMERATIONTYPE,
				BrutosConstants.DEFAULT_TEMPORALPROPERTY, mapping,
				BrutosConstants.DEFAULT_SCOPETYPE, null, null);
	}

	public BeanBuilder buildMetaValue(Object value, Class<?> clazz) {
		String beanName = this.origin + "#" + this.metaBean.getName() + "#"
				+ this.metaBean.getSize();

		BeanBuilder beanBuilder = this.controller.buildMappingBean(beanName,
				clazz);

		this.addMetaValue(value, beanName);

		return beanBuilder;
	}

	public MetaBeanBuilder addMetaValue(Object value,
			EnumerationType enumProperty, String temporalProperty,
			String mapping, ScopeType scope, Type typeDef, Object type) {

		if (type == null && mapping == null)
			throw new MappingException("unknown bean type");

		DependencyBean dependency = MappingBeanUtil.createMetaBeanValue(
				enumProperty, temporalProperty, mapping, scope, null, false,
				false, typeDef, type, this.metaBean, FetchType.EAGER, this.validatorFactory,
				this.controller.controller);

		Object metaValue = this.metaBean.getType().convert(value);

		//if (metaValue == null)
		//	throw new MappingException("invalid meta value: " + value);

		this.metaBean.putMetaValue(metaValue, dependency);
		return this;
	}
	
	public MetaBeanBuilder removeMetaValue(String value) {
		this.metaBean.removeMetaValue(value);
		return this;
	}

	public MetaBeanBuilder setName(String name) {
		name = StringUtil.adjust(name);

		if (StringUtil.isEmpty(name))
			throw new IllegalArgumentException("meta bean name cannot be empty");

		this.metaBean.setName(name);

		return this;
	}

	public String getName() {
		return this.metaBean.getName();
	}

	public MetaBeanBuilder setScope(ScopeType scope) {

		if (scope == null)
			throw new NullPointerException("scope of meta bean cannot be null");

		this.metaBean.setScopeType(scope);

		return this;
	}

	public ScopeType getScope() {
		return this.metaBean.getScopeType();
	}

	public MetaBeanBuilder setEnumProperty(EnumerationType enumProperty) {
		Type type = this.metaBean.getType();

		if (type instanceof EnumType)
			((EnumType) type).setEnumerationType(enumProperty);
		else
			throw new MappingException("enumProperty not supported");

		return this;
	}

	public EnumerationType getEnumProperty(EnumerationType enumProperty) {
		Type type = this.metaBean.getType();
		this.enumProperty = enumProperty;

		if (type instanceof EnumType)
			return ((EnumType) type).getEnumerationType();
		else
			return null;
	}

	public MetaBeanBuilder setTemporalProperty(String pattern) {
		Type type = this.metaBean.getType();
		this.temporalProperty = pattern;

		if (type instanceof DateTimeType)
			((DateTimeType) type).setPattern(pattern);
		else
			throw new MappingException("temporalProperty not supported");

		return this;
	}

	public String getTemporalProperty() {
		Type type = this.metaBean.getType();

		if (type instanceof DateTimeType)
			return ((DateTimeType) type).getPattern();
		else
			return null;
	}

	public MetaBeanBuilder setClassType(Class<?> classType) {

		this.metaBean.setClassType(classType);

		return this;
	}

	public Class<?> getClassType() {
		return this.metaBean.getType().getClassType();
	}

	public MetaBeanBuilder setType(Type type) {
		this.metaBean.setType(type);
		return this;
	}

	public Type getType() {
		return this.metaBean.getType();
	}

}
