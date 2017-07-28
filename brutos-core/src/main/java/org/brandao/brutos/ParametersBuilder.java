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

import org.brandao.brutos.mapping.Action;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.mapping.MappingException;
import org.brandao.brutos.mapping.MetaBean;
import org.brandao.brutos.mapping.ParameterAction;
import org.brandao.brutos.mapping.StringUtil;
import org.brandao.brutos.type.ObjectType;
import org.brandao.brutos.type.Type;
import org.brandao.brutos.type.TypeUtil;
import org.brandao.brutos.type.UnknownTypeException;

/**
 * 
 * @author Brandao
 */
public class ParametersBuilder extends RestrictionBuilder {

	private Controller controller;

	private Action action;

	private ValidatorFactory validatorFactory;

	private ControllerBuilder controllerBuilder;

	private ConfigurableApplicationContext applicationContext;

	public ParametersBuilder(Controller controller, Action action,
			ValidatorFactory validatorFactory,
			ControllerBuilder controllerBuilder,
			ConfigurableApplicationContext applicationContext) {
		super(action.getParametersValidator().getConfiguration());
		this.controller = controller;
		this.action = action;
		this.validatorFactory = validatorFactory;
		this.controllerBuilder = controllerBuilder;
		this.applicationContext = applicationContext;
	}

	public ParameterBuilder addParameter(String name, ScopeType scope,
			EnumerationType enumProperty, Class<?> classType) {
		return addParameter(name, scope, enumProperty, null, null, null, null,
				false, classType);
	}

	public ParameterBuilder addNullParameter() {
		return addParameter(null, null, null, null, null, null, null, false,
				null);
	}

	public ParameterBuilder addParameter(String name, ScopeType scope,
			String temporalProperty, Class<?> classType) {
		return addParameter(name, scope, null,
				temporalProperty, null, null, null, false, classType);
	}

	public ParameterBuilder addParameter(String name, ScopeType scope,
			Type typeDef) {
		return addParameter(name, scope, null, null,
				null, typeDef, null, false, typeDef.getClassType());
	}

	public ParameterBuilder addParameter(String name,
			EnumerationType enumProperty, Class<?> classType) {
		return addParameter(name, null, enumProperty, null, null,
				null, null, false, classType);
	}

	public ParameterBuilder addParameter(String name, ScopeType scope,
			Class<?> classType) {
		return addParameter(name, scope, null, null,
				null, null, null, false, classType);
	}

	public ParameterBuilder addParameter(String name, String temporalProperty,
			Class<?> classType) {
		return addParameter(name, null, null,
				temporalProperty, null, null, null, false, classType);
	}

	public ParameterBuilder addParameter(String name, Type typeDef) {
		return addParameter(name, null, null,
				null, null, typeDef, null, false,
				typeDef.getClassType());
	}

	public ParameterBuilder addParameterMapping(String mapping,
			Class<?> classType) {
		return addParameter(null, null, null,
				null, mapping, null, null, false, classType);
	}

	public ParameterBuilder addParameterMapping(String name, String mapping,
			Class<?> classType) {
		return addParameter(name, null, null,
				null, mapping, null, null, false, classType);
	}

	public ParameterBuilder addParameterMapping(String name, String mapping,
			Class<?> classType, FetchType fetchType) {
		return this.addParameter(name, null, null, null, mapping, null, null, 
				false, false, fetchType, classType);
	}
	
	public ParameterBuilder addParameterMapping(String name, String mapping,
			ScopeType scope, Class<?> classType) {
		return addParameter(name, scope, null, null,
				mapping, null, null, false, classType);
	}

	public ParameterBuilder addParameter(String name, Class<?> classType) {
		return addParameter(name, null, null,
				null, null, null, null, false, classType);
	}

	public BeanBuilder buildParameter(Class<?> classType) {
		String beanName = this.action.getCode() + "#"
				+ this.action.getParamterSize();
		BeanBuilder bb = this.controllerBuilder.buildMappingBean(beanName,
				null, classType);

		this.addParameterMapping(beanName, classType);
		return bb;
	}

	public BeanBuilder buildParameter(String name, Class<?> classType) {
		String beanName = this.action.getCode() + "#"
				+ this.action.getParamterSize();
		BeanBuilder bb = this.controllerBuilder.buildMappingBean(beanName,
				null, classType);

		this.addParameterMapping(name, beanName, classType);
		return bb;
	}

	public BeanBuilder buildParameter(Class<?> classType, Class<?> beanType) {
		String beanName = this.action.getCode() + "#"
				+ this.action.getParamterSize();
		BeanBuilder bb = this.controllerBuilder.buildMappingBean(beanName,
				null, beanType);

		this.addParameterMapping(beanName, classType);
		return bb;
	}

	public BeanBuilder buildParameter(String name, Class<?> classType,
			Class<?> beanType) {
		String beanName = this.action.getCode() + "#"
				+ this.action.getParamterSize();
		BeanBuilder bb = this.controllerBuilder.buildMappingBean(beanName,
				null, beanType);

		this.addParameterMapping(name, beanName, classType);
		return bb;
	}

	public ParameterBuilder addStaticParameter(Class<?> classType, Object value) {
		return addParameter(null, null, null,
				null, null, null, value, false, classType);
	}

	public ParameterBuilder addParameter(String name, ScopeType scope,
			EnumerationType enumProperty, String temporalProperty,
			String mapping, Type typeDef, Object value, boolean nullable,
			Class<?> classType) {
		return addParameter(name, scope, enumProperty, temporalProperty,
				mapping, typeDef, value, nullable, (Object) classType);
	}

	public ParameterBuilder addGenericParameter(String name, Class<?> classType) {
		return this.addParameter(name, null,
				null, null, null, null, null,
				false, true, classType);
	}

	public ParameterBuilder addGenericParameter(String name, Class<?> classType, FetchType fetchType) {
		return this.addParameter(name, null,
				null, null, null, null, null,
				false, true, fetchType, classType);
	}
	
	public ParameterBuilder addGenericParameter(String name) {
		return this.addParameter(name, null,
				null,
				null, null, null, null,
				false, true, null);
	}

	public ParameterBuilder addParameter(String name, ScopeType scope,
			EnumerationType enumProperty, String temporalProperty,
			String mapping, Type typeDef, Object value, boolean nullable,
			Object classType) {
		return this.addParameter(name, scope, enumProperty, temporalProperty,
				mapping, typeDef, value, nullable, false, classType);
	}

	public ParameterBuilder addParameter(String name, ScopeType scope,
			EnumerationType enumProperty, String temporalProperty,
			String mapping, Type typeDef, Object value, boolean nullable,
			boolean generic, Object classType) {
		
		return this.addParameter(name, scope, enumProperty, temporalProperty, 
				mapping, typeDef, value, nullable, generic, null, classType);
	}
	
	public ParameterBuilder addParameter(String name, ScopeType scope,
			EnumerationType enumProperty, String temporalProperty,
			String mapping, Type typeDef, Object value, boolean nullable,
			boolean generic, FetchType fetchType, Object classType) {

		name             = StringUtil.adjust(name);
		enumProperty     = enumProperty == null? this.applicationContext.getEnumerationType() : enumProperty;
		temporalProperty = StringUtil.isEmpty(temporalProperty)? this.applicationContext.getTemporalProperty() : temporalProperty;
		scope            = scope == null? this.applicationContext.getScopeType() : scope;
		mapping          = StringUtil.adjust(mapping);
		fetchType        = fetchType == null? this.applicationContext.getFetchType() : fetchType;
		
		Class<?> rawType = TypeUtil.getRawType(classType);

		if (StringUtil.isEmpty(name)
				&& (StringUtil.isEmpty(mapping) && !generic && value == null && !nullable)) {
			throw new IllegalArgumentException("bean name is required");
		}

		// if(StringUtil.isEmpty(name) && value == null && !nullable)
		// throw new IllegalArgumentException("bean name is required");

		if (scope == null)
			throw new MappingException("invalid scope");

		Configuration validatorConfig = new Configuration();

		ParameterAction parameter = new ParameterAction(this.action);

		parameter.setName(name);
		parameter.setFetchType(fetchType);
		parameter.setScopeType(scope);
		parameter.setValidate(this.validatorFactory
				.getValidator(validatorConfig));
		parameter.setStaticValue(value);
		parameter.setNullable(nullable);

		if (typeDef == null) {
			if (classType != null) {
				try {
					typeDef = this.applicationContext.getTypeManager().getType(
							classType, enumProperty, temporalProperty);

				} catch (UnknownTypeException e) {
					throw new MappingException(String.format(
							"%s.%s(...) index %d : %s",
							new Object[] {
									this.controller.getClassType().getName(),
									action.getExecutor(),
									new Integer(action.getParamterSize()),
									e.getMessage() }), e);
				}
			}

			if (typeDef == null)
				typeDef = new ObjectType(rawType);
		} else if (classType != null) {
			if (!typeDef.getClassType().isAssignableFrom(rawType)) {
				throw new MappingException(String.format(
						"expected %s found %s",
						new Object[] { rawType.getName(),
								typeDef.getClassType().getName() }));
			}
		}

		parameter.setType(typeDef);

		if (generic) {
			MetaBean metaBean = new MetaBean(controller);
			metaBean.setClassType(rawType);
			parameter.setMetaBean(metaBean);
		} else if (!StringUtil.isEmpty(mapping)) {
			if (controller.getBean(mapping) != null)
				parameter.setMapping(controller.getBean(mapping));
			else
				throw new MappingException("mapping name " + mapping
						+ " not found!");
		}

		action.addParameter(parameter);
		return new ParameterBuilder(parameter, this, this.validatorFactory);
	}

	public int getParametersSize() {
		return this.action.getParamterSize();
	}

	public ParameterBuilder getParameter(int index) {
		ParameterAction param = this.action.getParameter(index);
		return new ParameterBuilder(param, this, this.validatorFactory);
	}

	public ControllerBuilder getControllerBuilder() {
		return this.controllerBuilder;
	}

}
