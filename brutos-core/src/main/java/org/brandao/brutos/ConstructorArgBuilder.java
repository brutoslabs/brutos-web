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

import org.brandao.brutos.mapping.ConstructorArgBean;
import org.brandao.brutos.mapping.MappingException;
import org.brandao.brutos.mapping.MetaBean;
import org.brandao.brutos.type.Type;
import org.brandao.brutos.validator.RestrictionRules;

/**
 * 
 * @author Brandao
 */
public class ConstructorArgBuilder extends RestrictionBuilder implements
		GenericBuilder {

	private ConstructorArgBean arg;

	private ConstructorBuilder constructorBuilder;

	private ValidatorFactory validatorFactory;

	public ConstructorArgBuilder(ConstructorArgBean arg,
			ConstructorBuilder constructorBuilder,
			ValidatorFactory validatorFactory) {
		super(arg.getValidator().getConfiguration());
		this.arg = arg;
		this.constructorBuilder = constructorBuilder;
		this.validatorFactory = validatorFactory;
	}

	public RestrictionBuilder addRestriction(RestrictionRules ruleId,
			Object value) {
		return super.addRestriction(ruleId, value);
	}

	public RestrictionBuilder setMessage(String message) {
		return super.setMessage(message);
	}

	public ConstructorBuilder getConstructorBuilder() {
		return this.constructorBuilder;
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

		MetaBean metaBean = this.arg.getMetaBean();

		if (metaBean == null)
			throw new MappingException("can't add meta bean");

		this.arg.setMetaBean(metaBean);

		MetaBeanBuilder builder = new MetaBeanBuilder(metaBean, name, scope,
				enumProperty, temporalProperty, classType, type,
				validatorFactory, this.constructorBuilder.getBeanBuilder()
						.getControllerBuilder(), this.arg.getParent().getName()
						+ "#" + this.arg.getParameterName());

		return builder;
	}

	public void setFetchType(FetchType value){
		this.arg.setFetchType(value);
	}
	
	public FetchType getFetchType(){
		return this.arg.getFetchType();
	}
	
}
