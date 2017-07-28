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

import org.brandao.brutos.mapping.Bean;
import org.brandao.brutos.mapping.MappingException;
import org.brandao.brutos.mapping.MetaBean;
import org.brandao.brutos.mapping.PropertyBean;
import org.brandao.brutos.mapping.PropertyController;
import org.brandao.brutos.type.Type;
import org.brandao.brutos.validator.RestrictionRules;

/**
 * 
 * @author Brandao
 */
public class PropertyBuilder extends RestrictionBuilder implements
		GenericBuilder {

	private Object propertyBean;

	private ControllerBuilder controllerBuilder;

	private BeanBuilder beanBuilder;

	private ValidatorFactory validatorFactory;

	public PropertyBuilder(PropertyBean propertyBean, BeanBuilder beanBuilder,
			ValidatorFactory validatorFactory) {
		super(propertyBean.getValidator().getConfiguration());
		this.propertyBean = propertyBean;
		this.controllerBuilder = beanBuilder.getControllerBuilder();
		this.beanBuilder = beanBuilder;
		this.validatorFactory = validatorFactory;
	}

	public PropertyBuilder(PropertyController propertyBean,
			ControllerBuilder controllerBuilder,
			ValidatorFactory validatorFactory) {
		super(propertyBean.getValidate().getConfiguration());
		this.propertyBean = propertyBean;
		this.controllerBuilder = controllerBuilder;
		this.validatorFactory = validatorFactory;
	}

	public ControllerBuilder getControllerBuilder() {
		return this.controllerBuilder;
	}

	public BeanBuilder getBeanBuilder() {
		return this.beanBuilder;
	}

	public RestrictionBuilder addRestriction(RestrictionRules ruleId,
			Object value) {
		return super.addRestriction(ruleId, value);
	}

	public RestrictionBuilder setMessage(String message) {
		return super.setMessage(message);
	}

	public void setFetchType(FetchType value){
		if(this.propertyBean instanceof PropertyBean){
			((PropertyBean)this.propertyBean).setFetchType(value);
		}
		else{
			((PropertyController)this.propertyBean).setFetchType(value);
		}
	}
	
	public FetchType getFetchType(){
		if(this.propertyBean instanceof PropertyBean){
			return ((PropertyBean)this.propertyBean).getFetchType();
		}
		else{
			return ((PropertyController)this.propertyBean).getFetchType();
		}
	}
	
	public MetaBeanBuilder buildMetaBean(String name, Class<?> classType) {
		return this.buildMetaBean(name, ScopeType.PARAM,
				BrutosConstants.DEFAULT_ENUMERATIONTYPE,
				BrutosConstants.DEFAULT_TEMPORALPROPERTY, classType, null);
	}

	public MetaBeanBuilder buildMetaBean(String name,
			EnumerationType enumProperty, String temporalProperty,
			Class<?> classType) {
		return this.buildMetaBean(name, ScopeType.PARAM, enumProperty,
				temporalProperty, classType, null);
	}

	public MetaBeanBuilder buildMetaBean(String name, Type type) {
		return this.buildMetaBean(name, ScopeType.PARAM,
				BrutosConstants.DEFAULT_ENUMERATIONTYPE,
				BrutosConstants.DEFAULT_TEMPORALPROPERTY, null, type);
	}

	public MetaBeanBuilder buildMetaBean(String name, ScopeType scope,
			Class<?> classType) {
		return this.buildMetaBean(name, scope,
				BrutosConstants.DEFAULT_ENUMERATIONTYPE,
				BrutosConstants.DEFAULT_TEMPORALPROPERTY, classType, null);
	}

	public MetaBeanBuilder buildMetaBean(String name, ScopeType scope, Type type) {
		return this.buildMetaBean(name, scope,
				BrutosConstants.DEFAULT_ENUMERATIONTYPE,
				BrutosConstants.DEFAULT_TEMPORALPROPERTY, null, type);
	}

	public MetaBeanBuilder buildMetaBean(String name, ScopeType scope,
			EnumerationType enumProperty, String temporalProperty,
			Class<?> classType, Type type) {

		String propertyName = this.propertyBean instanceof PropertyBean ? ((PropertyBean) this.propertyBean)
				.getName() : ((PropertyController) this.propertyBean).getName();

		Bean parent = this.propertyBean instanceof PropertyBean ? ((PropertyBean) this.propertyBean)
				.getParent() : null;

		MetaBean metaBean = this.propertyBean instanceof PropertyBean ? ((PropertyBean) this.propertyBean)
				.getMetaBean() : ((PropertyController) this.propertyBean)
				.getMetaBean();

		if (metaBean == null)
			throw new MappingException("can't add meta bean");

		if (this.propertyBean instanceof PropertyBean)
			((PropertyBean) this.propertyBean).setMetaBean(metaBean);
		else
			((PropertyController) this.propertyBean).setMetaBean(metaBean);

		MetaBeanBuilder builder = new MetaBeanBuilder(metaBean, name, scope,
				enumProperty, temporalProperty, classType, type,
				validatorFactory, this.controllerBuilder, (parent == null ? ""
						: parent.getName() + "#") + propertyName);

		return builder;
	}

}
