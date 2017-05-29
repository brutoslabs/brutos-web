package org.brandao.brutos;

import org.brandao.brutos.mapping.MappingException;
import org.brandao.brutos.mapping.MetaBean;
import org.brandao.brutos.type.Type;

public class ResultActionBuilder 
	extends RestrictionBuilder 
	implements GenericBuilder {

	private org.brandao.brutos.mapping.ResultAction resultAction;
	
	private ValidatorFactory validatorFactory;
	
	private ActionBuilder actionBuilder;
	
	public ResultActionBuilder(
			ActionBuilder actionBuilder,
			org.brandao.brutos.mapping.ResultAction resultAction, 
			ValidatorFactory validatorFactory) {
		super(resultAction.getValidate().getConfiguration());
		this.resultAction = resultAction;
		this.validatorFactory = validatorFactory;
		this.actionBuilder = actionBuilder;
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

		MetaBean metaBean = this.resultAction.getMetaBean();

		if (metaBean == null)
			throw new MappingException("can't add meta bean");
		
		MetaBeanBuilder builder = new MetaBeanBuilder(metaBean, name, scope,
				enumProperty, temporalProperty, classType, type,
				this.validatorFactory,
				this.actionBuilder.getControllerBuilder(),
				this.resultAction.getName());

		return builder;
	}

	public ActionBuilder getActionBuilder(){
		return this.actionBuilder;
	}
}
