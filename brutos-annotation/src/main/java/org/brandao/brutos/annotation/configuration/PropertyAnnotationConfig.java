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

package org.brandao.brutos.annotation.configuration;

import org.brandao.brutos.EnumerationType;
import org.brandao.brutos.*;
import org.brandao.brutos.ScopeType;
import org.brandao.brutos.annotation.*;
import org.brandao.brutos.annotation.bean.BeanPropertyAnnotation;
import org.brandao.brutos.mapping.StringUtil;

/**
 *
 * @author Brandao
 */
// @Stereotype(target=Property.class,executeAfter={Controller.class,
// Bean.class})
@Deprecated
public class PropertyAnnotationConfig extends AbstractAnnotationConfig {

	public boolean isApplicable(Object source) {
		return source instanceof BeanPropertyAnnotation
				&& !((BeanPropertyAnnotation) source)
						.isAnnotationPresent(Transient.class);
	}

	public Object applyConfiguration(Object source, Object builder,
			ComponentRegistry componentRegistry) {

		BeanPropertyAnnotation property = (BeanPropertyAnnotation) source;

		PropertyBuilder propertyBuilder;
		if (!componentRegistry.isStandardType(property.getType()))
			propertyBuilder = buildProperty((BeanBuilder) builder, property,
					componentRegistry);
		else
			propertyBuilder = addProperty(property, builder, componentRegistry);

		super.applyInternalConfiguration(property, propertyBuilder,
				componentRegistry);

		return builder;
	}

	protected PropertyBuilder addProperty(BeanPropertyAnnotation property,
			Object builder, ComponentRegistry componentRegistry) {

		Property propertyAnnotation = (Property) property
				.getAnnotation(Property.class);
		String propertyName = getPropertyName(property);
		String name = getBeanName(property, propertyAnnotation);
		ScopeType scope = getScope(propertyAnnotation);
		EnumerationType enumProperty = getEnumerationType(property);
		String temporalProperty = getTemporalProperty(property);
		org.brandao.brutos.type.Type type = getType(property);

		PropertyBuilder propertyBuilder;
		if (builder instanceof BeanBuilder) {
			propertyBuilder = addProperty((BeanBuilder) builder, property,
					propertyName, name, scope, enumProperty, temporalProperty,
					type, componentRegistry);
		} else {
			propertyBuilder = addProperty((ControllerBuilder) builder,
					property, propertyName, name, scope, enumProperty,
					temporalProperty, type, componentRegistry);
		}

		return propertyBuilder;
	}

	protected PropertyBuilder addProperty(BeanBuilder beanBuilder,
			BeanPropertyAnnotation property, String propertyName, String name,
			ScopeType scope, EnumerationType enumProperty,
			String temporalProperty, org.brandao.brutos.type.Type type,
			ComponentRegistry componentRegistry) {

		PropertyBuilder builder = beanBuilder.addProperty(name, propertyName,
				enumProperty, temporalProperty, name, scope, null, false, type);

		return builder;
	}

	protected PropertyBuilder addProperty(ControllerBuilder controllerBuilder,
			BeanPropertyAnnotation property, String propertyName, String name,
			ScopeType scope, EnumerationType enumProperty,
			String temporalProperty, org.brandao.brutos.type.Type type,
			ComponentRegistry componentRegistry) {

		PropertyBuilder builder = controllerBuilder.addProperty(propertyName,
				name, scope, enumProperty, temporalProperty, null, null, false,
				type);

		return builder;
	}

	protected PropertyBuilder buildProperty(BeanBuilder beanBuilder,
			BeanPropertyAnnotation property, ComponentRegistry componentRegistry) {
		super.applyInternalConfiguration(new BeanEntryProperty(property),
				beanBuilder, componentRegistry);

		return beanBuilder.getProperty(property.getName());
	}

	private org.brandao.brutos.type.Type getType(BeanPropertyAnnotation property) {
		try {
			Type type = property.getAnnotation(Type.class);
			if (type != null) {
				Class typeClass = type.value();
				return (org.brandao.brutos.type.Type) ClassUtil
						.getInstance(typeClass);
			} else
				return null;

		} catch (Exception e) {
			throw new BrutosException(e);
		}
	}

	private String getTemporalProperty(BeanPropertyAnnotation property) {
		if (property.isAnnotationPresent(Temporal.class))
			return property.getAnnotation(Temporal.class).value();
		else
			return BrutosConstants.DEFAULT_TEMPORALPROPERTY;
	}

	private EnumerationType getEnumerationType(BeanPropertyAnnotation property) {
		if (property.isAnnotationPresent(Enumerated.class)) {
			return EnumerationType.valueOf(property
					.getAnnotation(Enumerated.class).value().name()
					.toLowerCase());
		} else
			return BrutosConstants.DEFAULT_ENUMERATIONTYPE;
	}

	private ScopeType getScope(Property propertyAnnotation) {

		if (propertyAnnotation != null) {
			String scope = StringUtil.adjust(propertyAnnotation.scope());
			if (!StringUtil.isEmpty(scope))
				return ScopeType.valueOf(propertyAnnotation.scope());
		}

		return BrutosConstants.DEFAULT_SCOPETYPE;
	}

	private String getBeanName(BeanPropertyAnnotation property,
			Property propertyAnnotation) {

		if (propertyAnnotation != null) {
			String bean = StringUtil.adjust(propertyAnnotation.bean());
			if (!StringUtil.isEmpty(bean))
				return propertyAnnotation.bean();
		}

		return property.getName();
	}

	private String getPropertyName(BeanPropertyAnnotation param) {
		return param.getName();
	}
}
