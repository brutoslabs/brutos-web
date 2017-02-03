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

import java.util.List;

import org.brandao.brutos.ClassUtil;
import org.brandao.brutos.ComponentRegistry;
import org.brandao.brutos.ControllerBuilder;
import org.brandao.brutos.EnumerationType;
import org.brandao.brutos.KeyBuilder;
import org.brandao.brutos.MetaBeanBuilder;
import org.brandao.brutos.ScopeType;
import org.brandao.brutos.annotation.AnyKeyCollection;
import org.brandao.brutos.annotation.Basic;
import org.brandao.brutos.annotation.MetaValue;
import org.brandao.brutos.mapping.MappingException;
import org.brandao.brutos.mapping.StringUtil;

@Deprecated
// @Stereotype(target = AnyKeyCollection.class, executeAfter =
// KeyCollection.class)
public class AnyKeyCollectionAnnotationConfig extends AbstractAnnotationConfig {

	public boolean isApplicable(Object source) {
		return source instanceof KeyEntry
				&& ((KeyEntry) source)
						.isAnnotationPresent(AnyKeyCollection.class);
	}

	public Object applyConfiguration(Object source, Object builder,
			ComponentRegistry componentRegistry) {

		try {
			return applyConfiguration0(source, builder, componentRegistry);
		} catch (Exception e) {
			throw new MappingException("can't create key of collection: "
					+ ((KeyEntry) source).getName(), e);
		}

	}

	public Object applyConfiguration0(Object source, Object builder,
			ComponentRegistry componentRegistry) throws InstantiationException,
			IllegalAccessException {

		KeyEntry keyEntry = (KeyEntry) source;
		KeyBuilder keyBuilder = (KeyBuilder) builder;

		AnyKeyCollection anyKeyCollection = keyEntry
				.getAnnotation(AnyKeyCollection.class);

		Basic basic = anyKeyCollection.metaBean();
		Class<?> metaType = anyKeyCollection.metaType();

		String key = StringUtil.isEmpty(basic.bean()) ? keyEntry.getName()
				: basic.bean();

		EnumerationType enumType = keyEntry.getEnumerated();
		String tempType = keyEntry.getTemporal();
		ScopeType scope = AnnotationUtil.getScope(basic);
		org.brandao.brutos.type.Type type = keyEntry.getType() == null ? null
				: AnnotationUtil.getTypeInstance(keyEntry.getType());

		MetaBeanBuilder metaBeanBuilder = keyBuilder.buildMetaBean(key, scope,
				enumType, tempType, metaType, type);

		this.buildMetaValues(anyKeyCollection, metaBeanBuilder, keyBuilder
				.getBeanBuilder().getControllerBuilder(), componentRegistry);

		super.applyInternalConfiguration(key, keyBuilder, componentRegistry);

		return builder;
	}

	private void buildMetaValues(AnyKeyCollection any,
			MetaBeanBuilder metaBeanBuilder,
			ControllerBuilder controllerBuilder,
			ComponentRegistry componentRegistry) throws InstantiationException,
			IllegalAccessException {

		if (any.metaValuesDefinition() == MetaValuesDefinition.class) {

			if (any.metaValues().length == 0)
				throw new MappingException("meta values is required");

			for (MetaValue value : any.metaValues()) {
				super.applyInternalConfiguration(
						new ImportBeanEntry(value.target()), controllerBuilder,
						componentRegistry);
				metaBeanBuilder.addMetaValue(value.name(),
						AnnotationUtil.getBeanName(value.target()));
			}
		} else {
			Class<? extends MetaValuesDefinition> metaClassDefinition = any
					.metaValuesDefinition();
			MetaValuesDefinition metaValuesDefinition = (MetaValuesDefinition) ClassUtil
					.getInstance(metaClassDefinition);

			List<MetaValueDefinition> list = metaValuesDefinition
					.getMetaValues();

			if (list == null || list.isEmpty())
				throw new MappingException("meta values cannot be empty");

			for (MetaValueDefinition value : list) {
				super.applyInternalConfiguration(
						new ImportBeanEntry(value.getTarget()),
						controllerBuilder, componentRegistry);
				metaBeanBuilder.addMetaValue(value.getName(),
						AnnotationUtil.getBeanName(value.getTarget()));
			}
		}
	}

}