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

import java.lang.reflect.Modifier;
import java.util.List;

import org.brandao.brutos.BrutosException;
import org.brandao.brutos.ClassUtil;
import org.brandao.brutos.ComponentRegistry;
import org.brandao.brutos.ConstructorArgBuilder;
import org.brandao.brutos.ControllerBuilder;
import org.brandao.brutos.ElementBuilder;
import org.brandao.brutos.EnumerationType;
import org.brandao.brutos.KeyBuilder;
import org.brandao.brutos.MetaBeanBuilder;
import org.brandao.brutos.ParameterBuilder;
import org.brandao.brutos.PropertyBuilder;
import org.brandao.brutos.ScopeType;
import org.brandao.brutos.annotation.Any;
import org.brandao.brutos.annotation.Basic;
import org.brandao.brutos.annotation.ElementCollection;
import org.brandao.brutos.annotation.KeyCollection;
import org.brandao.brutos.annotation.MetaValue;
import org.brandao.brutos.annotation.Stereotype;
import org.brandao.brutos.annotation.Transient;
import org.brandao.brutos.annotation.bean.BeanPropertyAnnotation;
import org.brandao.brutos.mapping.MappingException;
import org.brandao.brutos.mapping.StringUtil;

@Stereotype(target = Any.class, executeAfter = { Basic.class,
		KeyCollection.class, ElementCollection.class })
public class AnyAnnotationConfig extends AbstractAnnotationConfig {

	public boolean isApplicable(Object source) {

		boolean applicable = source instanceof ActionParamEntry
				&& ((ActionParamEntry) source).isAnnotationPresent(Any.class);

		applicable = applicable
				|| (source instanceof BeanPropertyAnnotation
						&& !((BeanPropertyAnnotation) source)
								.isAnnotationPresent(Transient.class) && ((BeanPropertyAnnotation) source)
							.isAnnotationPresent(Any.class));

		applicable = applicable
				|| (source instanceof ConstructorArgEntry && ((ConstructorArgEntry) source)
						.isAnnotationPresent(Any.class));

		applicable = applicable
				|| (source instanceof KeyEntry && ((KeyEntry) source)
						.isAnnotationPresent(Any.class));

		applicable = applicable
				|| (source instanceof ElementEntry && ((ElementEntry) source)
						.isAnnotationPresent(Any.class));

		return applicable;
	}

	public Object applyConfiguration(Object source, Object builder,
			ComponentRegistry componentRegistry) {

		try {
			return applyConfiguration0(source, builder, componentRegistry);
		} catch (Exception e) {

			String type = "source";
			String name = "it is a bug";

			if (source instanceof ActionParamEntry) {
				type = "parameter";
				name = ((ActionParamEntry) source).getName();
			} else if (source instanceof BeanPropertyAnnotation) {
				type = "property";
				name = ((BeanPropertyAnnotation) source).getName();
			} else if (source instanceof ConstructorArgEntry) {
				type = "constructor arg";
				name = ((ConstructorArgEntry) source).getName();
			} else if (source instanceof KeyEntry) {
				type = "key";
				name = ((KeyEntry) source).getName();
			} else if (source instanceof ElementEntry) {
				type = "element";
				name = ((ElementEntry) source).getName();
			}

			throw new BrutosException("can't identify " + type + ": " + name, e);
		}

	}

	public Object applyConfiguration0(Object source, Object builder,
			ComponentRegistry componentRegistry) throws InstantiationException,
			IllegalAccessException {

		if (source instanceof ActionParamEntry)
			addIdentify((ActionParamEntry) source, (ParameterBuilder) builder,
					componentRegistry);
		else if (source instanceof BeanPropertyAnnotation)
			addIdentify((BeanPropertyAnnotation) source,
					(PropertyBuilder) builder, componentRegistry);
		else if (source instanceof ConstructorArgEntry)
			addIdentify((ConstructorArgEntry) source,
					(ConstructorArgBuilder) builder, componentRegistry);
		else if (source instanceof KeyEntry)
			addIdentify((KeyEntry) source, (KeyBuilder) builder,
					componentRegistry);
		else if (source instanceof ElementEntry)
			addIdentify((ElementEntry) source, (ElementBuilder) builder,
					componentRegistry);

		return source;
	}

	protected void addIdentify(ActionParamEntry source,
			ParameterBuilder paramBuilder, ComponentRegistry componentRegistry)
			throws InstantiationException, IllegalAccessException {

		Any any = source.getAnnotation(Any.class);
		Basic basic = any.metaBean();
		Class<?> classType = any.metaType();

		String name = StringUtil.adjust(basic.bean());
		ScopeType scope = AnnotationUtil.getScope(basic);
		EnumerationType enumProperty = AnnotationUtil.getEnumerationType(any);
		String temporalProperty = AnnotationUtil.getTemporalProperty(any);
		org.brandao.brutos.type.Type type = AnnotationUtil.getTypeInstance(any);

		MetaBeanBuilder builder = paramBuilder.buildMetaBean(name, scope,
				enumProperty, temporalProperty, classType, type);

		this.buildMetaValues(any, builder, paramBuilder.getParametersBuilder()
				.getControllerBuilder(), componentRegistry);

		super.applyInternalConfiguration(source, paramBuilder,
				componentRegistry);

	}

	protected void addIdentify(BeanPropertyAnnotation source,
			PropertyBuilder propertyBuilder, ComponentRegistry componentRegistry)
			throws InstantiationException, IllegalAccessException {

		Any any = source.getAnnotation(Any.class);
		Basic basic = any.metaBean();
		Class<?> classType = any.metaType();

		String name = StringUtil.adjust(basic.bean());
		ScopeType scope = AnnotationUtil.getScope(basic);
		EnumerationType enumProperty = AnnotationUtil.getEnumerationType(any);
		String temporalProperty = AnnotationUtil.getTemporalProperty(any);
		org.brandao.brutos.type.Type type = AnnotationUtil.getTypeInstance(any);

		MetaBeanBuilder builder = propertyBuilder.buildMetaBean(name, scope,
				enumProperty, temporalProperty, classType, type);

		this.buildMetaValues(any, builder,
				propertyBuilder.getControllerBuilder(), componentRegistry);

		super.applyInternalConfiguration(source, propertyBuilder,
				componentRegistry);

	}

	protected void addIdentify(ConstructorArgEntry source,
			ConstructorArgBuilder constructorArgBuilder,
			ComponentRegistry componentRegistry) throws InstantiationException,
			IllegalAccessException {

		Any any = source.getAnnotation(Any.class);
		Basic basic = any.metaBean();
		Class<?> classType = any.metaType();

		String name = StringUtil.adjust(basic.bean());
		ScopeType scope = AnnotationUtil.getScope(basic);
		EnumerationType enumProperty = AnnotationUtil.getEnumerationType(any);
		String temporalProperty = AnnotationUtil.getTemporalProperty(any);
		org.brandao.brutos.type.Type type = AnnotationUtil.getTypeInstance(any);

		MetaBeanBuilder builder = constructorArgBuilder.buildMetaBean(name,
				scope, enumProperty, temporalProperty, classType, type);

		this.buildMetaValues(any, builder, constructorArgBuilder
				.getConstructorBuilder().getBeanBuilder()
				.getControllerBuilder(), componentRegistry);

		super.applyInternalConfiguration(source, constructorArgBuilder,
				componentRegistry);

	}

	protected void addIdentify(KeyEntry source, KeyBuilder sourceBuilder,
			ComponentRegistry componentRegistry) throws InstantiationException,
			IllegalAccessException {

		Any any = source.getAnnotation(Any.class);
		Basic basic = any.metaBean();
		Class<?> classType = any.metaType();

		String name = StringUtil.adjust(basic.bean());
		ScopeType scope = AnnotationUtil.getScope(basic);
		EnumerationType enumProperty = AnnotationUtil.getEnumerationType(any);
		String temporalProperty = AnnotationUtil.getTemporalProperty(any);
		org.brandao.brutos.type.Type type = AnnotationUtil.getTypeInstance(any);

		MetaBeanBuilder builder = sourceBuilder.buildMetaBean(name, scope,
				enumProperty, temporalProperty, classType, type);

		this.buildMetaValues(any, builder, sourceBuilder.getBeanBuilder()
				.getControllerBuilder(), componentRegistry);

		super.applyInternalConfiguration(source, builder, componentRegistry);

	}

	protected void addIdentify(ElementEntry source,
			ElementBuilder sourceBuilder, ComponentRegistry componentRegistry)
			throws InstantiationException, IllegalAccessException {

		Any any = source.getAnnotation(Any.class);
		Basic basic = any.metaBean();
		Class<?> classType = any.metaType();

		String name = StringUtil.adjust(basic.bean());
		ScopeType scope = AnnotationUtil.getScope(basic);
		EnumerationType enumProperty = AnnotationUtil.getEnumerationType(any);
		String temporalProperty = AnnotationUtil.getTemporalProperty(any);
		org.brandao.brutos.type.Type type = AnnotationUtil.getTypeInstance(any);

		MetaBeanBuilder builder = sourceBuilder.buildMetaBean(name, scope,
				enumProperty, temporalProperty, classType, type);

		this.buildMetaValues(any, builder, sourceBuilder.getBeanBuilder()
				.getControllerBuilder(), componentRegistry);

		super.applyInternalConfiguration(source, builder, componentRegistry);

	}

	private void buildMetaValues(Any any, MetaBeanBuilder metaBeanBuilder,
			ControllerBuilder controllerBuilder,
			ComponentRegistry componentRegistry) throws InstantiationException,
			IllegalAccessException {

		//Obtém a lista de tipos a partir da anotação
		if (any.metaValuesDefinition() == MetaValuesDefinition.class) {

			//Verifica se o tipo do bean é elegivel como opção
			Class<?> beanType = metaBeanBuilder.getClassType();
			
			if(Modifier.isAbstract(beanType.getModifiers()) || beanType.isInterface()){
				if (any.metaValues().length == 0){
					throw new MappingException("meta values is required");
				}
			}
			else{
				if (controllerBuilder.getBean(AnnotationUtil.getBeanName(beanType)) == null) {
					super.applyInternalConfiguration(
							new ImportBeanEntry(beanType),
							controllerBuilder, componentRegistry);
				}
				
				metaBeanBuilder.addMetaValue(null,
						AnnotationUtil.getBeanName(beanType));
			}
			
			for (MetaValue value : any.metaValues()) {
				if (controllerBuilder.getBean(AnnotationUtil.getBeanName(value
						.target())) == null) {
					super.applyInternalConfiguration(
							new ImportBeanEntry(value.target()),
							controllerBuilder, componentRegistry);
				}
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
				if (controllerBuilder.getBean(AnnotationUtil.getBeanName(value
						.getTarget())) == null) {
					super.applyInternalConfiguration(
							new ImportBeanEntry(value.getTarget()),
							controllerBuilder, componentRegistry);
				}
				metaBeanBuilder.addMetaValue(value.getName(),
						AnnotationUtil.getBeanName(value.getTarget()));
			}
		}
	}
}
