package org.brandao.brutos;

import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.mapping.DependencyBean;
import org.brandao.brutos.mapping.MetaBean;
import org.brandao.brutos.type.Type;
import org.brandao.brutos.validator.RestrictionRules;

public class KeyBuilder extends RestrictionBuilder implements GenericBuilder {

	private DependencyBean key;

	private BeanBuilder beanBuilder;

	private ValidatorFactory validatorFactory;

	public KeyBuilder(DependencyBean key, BeanBuilder beanBuilder,
			ValidatorFactory validatorFactory) {
		super(key.getValidator().getConfiguration());
		this.key = key;
		this.beanBuilder = beanBuilder;
		this.validatorFactory = validatorFactory;
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

		Controller controller = this.key.getParent().getController();

		MetaBean metaBean = new MetaBean(controller);

		this.key.setMetaBean(metaBean);

		MetaBeanBuilder builder = new MetaBeanBuilder(metaBean, name, scope,
				enumProperty, temporalProperty, classType, type,
				this.validatorFactory, this.beanBuilder.getControllerBuilder(),
				this.beanBuilder.getName() + "#key");

		return builder;
	}

}
