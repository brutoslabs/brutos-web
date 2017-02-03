package org.brandao.brutos.validator;

import java.util.Properties;
import org.brandao.brutos.mapping.Action;
import org.brandao.brutos.mapping.ConstructorArgBean;
import org.brandao.brutos.mapping.ConstructorBean;
import org.brandao.brutos.mapping.ParameterAction;
import org.brandao.brutos.mapping.PropertyBean;
import org.brandao.brutos.mapping.PropertyController;

public interface Validator {

	void configure(Properties config);

	Properties getConfiguration();

	void validate(ConstructorArgBean source, Object value)
			throws ValidatorException;

	void validate(ConstructorBean source, Object factoryInstance, Object[] value)
			throws ValidatorException;

	void validate(ConstructorBean source, Object factoryInstance, Object value)
			throws ValidatorException;

	void validate(PropertyBean source, Object beanInstance, Object value)
			throws ValidatorException;

	void validate(PropertyController source, Object controllerInstance,
			Object value) throws ValidatorException;

	void validate(ParameterAction source, Object controllerInstance,
			Object value) throws ValidatorException;

	void validate(Action source, Object controller, Object[] value)
			throws ValidatorException;

	void validate(Action source, Object controller, Object value)
			throws ValidatorException;

}
