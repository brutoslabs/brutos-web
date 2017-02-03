package org.brandao.brutos;

import org.brandao.brutos.logger.Logger;
import org.brandao.brutos.logger.LoggerProvider;
import org.brandao.brutos.mapping.Bean;
import org.brandao.brutos.mapping.ConstructorArgBean;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.mapping.MappingBeanUtil;
import org.brandao.brutos.mapping.MappingException;
import org.brandao.brutos.mapping.StringUtil;
import org.brandao.brutos.type.Type;

public class ConstructorBuilder extends RestrictionBuilder {

	private Bean mappingBean;

	private BeanBuilder beanBuilder;

	private ValidatorFactory validatorFactory;

	private Controller controller;

	public ConstructorBuilder(Bean mappingBean, BeanBuilder beanBuilder,
			ValidatorFactory validatorFactory, Controller controller) {
		super(mappingBean.getConstructor().getValidator().getConfiguration());
		this.mappingBean = mappingBean;
		this.beanBuilder = beanBuilder;
		this.validatorFactory = validatorFactory;
		this.controller = controller;
	}

	public BeanBuilder buildConstructorArg(String name, Class<?> target) {

		name = StringUtil.adjust(name);

		String beanName = this.mappingBean.getName() + "#"
				+ this.mappingBean.getConstructor().size();

		BeanBuilder beanBuilder = this.beanBuilder.getControllerBuilder()
				.buildMappingBean(beanName, this.mappingBean.getName(), target);

		this.addMappedContructorArg(name, beanName);

		return beanBuilder;
	}

	public BeanBuilder buildConstructorArg(String name, Class<?> classType,
			Class<?> target) {

		name = StringUtil.adjust(name);

		String beanName = this.mappingBean.getName() + "#"
				+ this.mappingBean.getConstructor().size();

		BeanBuilder beanBuilder = this.beanBuilder.getControllerBuilder()
				.buildMappingBean(beanName, this.mappingBean.getName(), target);

		this.addMappedContructorArg(name, beanName);

		return beanBuilder;
	}

	public ConstructorArgBuilder addContructorArg(String name,
			EnumerationType enumProperty) {
		return addContructorArg(name, enumProperty, null, null,
				ScopeType.PARAM, null, false, null, null);
	}

	public ConstructorArgBuilder addContructorArg(String name,
			String temporalProperty) {
		return addContructorArg(name, EnumerationType.ORDINAL,
				temporalProperty, null, ScopeType.PARAM, null, false, null,
				null);
	}

	public ConstructorArgBuilder addNullContructorArg() {
		return addContructorArg(null, EnumerationType.ORDINAL, null, null,
				ScopeType.PARAM, null, true, null, null);
	}

	public ConstructorArgBuilder addContructorArg(String name, Type type) {
		return addContructorArg(name, EnumerationType.ORDINAL, "dd/MM/yyyy",
				null, ScopeType.PARAM, null, false, type, null);
	}

	public ConstructorArgBuilder addMappedContructorArg(String name,
			String mapping) {
		return addContructorArg(name, EnumerationType.ORDINAL, "dd/MM/yyyy",
				mapping, ScopeType.PARAM, null, false, null, null);
	}

	public ConstructorArgBuilder addMappedContructorArg(String name,
			String mapping, Class<?> type) {
		return addContructorArg(name, EnumerationType.ORDINAL, "dd/MM/yyyy",
				mapping, ScopeType.PARAM, null, false, false, null,
				(Object) type);
	}

	public ConstructorArgBuilder addContructorArg(String name) {
		return addContructorArg(name, EnumerationType.ORDINAL, "dd/MM/yyyy",
				null, ScopeType.PARAM, null, false, null, null);
	}

	public ConstructorArgBuilder addContructorArg(String name, ScopeType scope) {
		return addContructorArg(name, EnumerationType.ORDINAL, "dd/MM/yyyy",
				null, scope, null, false, null, null);
	}

	public ConstructorArgBuilder addStaticContructorArg(String name,
			Object value) {
		return addContructorArg(name, EnumerationType.ORDINAL, "dd/MM/yyyy",
				null, ScopeType.PARAM, value, false, null, null);
	}

	public ConstructorArgBuilder addContructorArg(String name,
			EnumerationType enumProperty, String temporalProperty,
			String mapping, ScopeType scope, Object value, boolean nullable,
			Type typeDef, Class<?> type) {
		return addContructorArg(name, enumProperty, temporalProperty, mapping,
				scope, value, nullable, false, typeDef, (Object) type);
	}

	public ConstructorArgBuilder addGenericContructorArg(String name,
			Class<?> type) {
		return this.addContructorArg(name,
				BrutosConstants.DEFAULT_ENUMERATIONTYPE,
				BrutosConstants.DEFAULT_TEMPORALPROPERTY, null,
				BrutosConstants.DEFAULT_SCOPETYPE, null, false, true, null,
				type);
	}

	public ConstructorArgBuilder addGenericContructorArg(String name) {
		return this.addContructorArg(name,
				BrutosConstants.DEFAULT_ENUMERATIONTYPE,
				BrutosConstants.DEFAULT_TEMPORALPROPERTY, null,
				BrutosConstants.DEFAULT_SCOPETYPE, null, false, true, null,
				null);
	}

	public ConstructorArgBuilder addContructorArg(String name,
			EnumerationType enumProperty, String temporalProperty,
			String mapping, ScopeType scope, Object value, boolean nullable,
			boolean generic, Type typeDef, Object type) {

		name = StringUtil.adjust(name);

		if (StringUtil.isEmpty(name)
				&& (StringUtil.isEmpty(mapping) && !generic && value == null && !nullable)) {
			throw new IllegalArgumentException("bean name is required");
		}

		if (scope == null)
			throw new MappingException("invalid scope");

		ConstructorArgBean arg = (ConstructorArgBean) MappingBeanUtil
				.createConstructorArg(name, enumProperty, temporalProperty,
						mapping, scope, value, nullable, generic, typeDef,
						type, this.mappingBean, this.validatorFactory,
						this.controller);

		getLogger().info(
				String.format(
						"%s added constructor arg %s",
						new Object[] {
								this.getPrefixLogger(),
								String.valueOf(this.mappingBean
										.getConstructor().size()) }));

		Configuration validatorConfig = new Configuration();
		arg.setValidator(this.validatorFactory.getValidator(validatorConfig));
		this.mappingBean.getConstructor().addConstructorArg(arg);
		return new ConstructorArgBuilder(arg, this, this.validatorFactory);
	}

	protected String getPrefixLogger() {
		return this.mappingBean.getName() + ":";
	}

	protected Logger getLogger() {
		return LoggerProvider.getCurrentLoggerProvider().getLogger(
				ConstructorBuilder.class);
	}

	public int getConstructorArgSize() {
		return mappingBean.getConstructor().size();
	}

	public ConstructorArgBuilder getConstructorArg(int index) {
		ConstructorArgBean arg = mappingBean.getConstructor()
				.getConstructorArg(index);
		return new ConstructorArgBuilder(arg, this, this.validatorFactory);
	}

	public Class<?> getClassType() {
		return this.mappingBean.getClassType();
	}

	public BeanBuilder getBeanBuilder() {
		return this.beanBuilder;
	}

}
