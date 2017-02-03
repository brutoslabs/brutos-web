package org.brandao.brutos.validator;

import java.util.Properties;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.mapping.DependencyBean;
import org.brandao.brutos.mapping.UseBeanData;
import org.brandao.brutos.type.Type;

public class MinValidationRule implements ValidationRule {

	private String expected;

	public void validate(Object source, Object value) {
		Type valueType = null;

		if (source instanceof DependencyBean)
			valueType = ((DependencyBean) source).getType();
		else if (source instanceof UseBeanData)
			valueType = ((UseBeanData) source).getType();
		else
			throw new BrutosException("invalid source: " + source);

		Number tmp = (Number) valueType.convert(this.expected);

		if (value != null && ((Number) value).doubleValue() < tmp.doubleValue())
			throw new ValidatorException();
	}

	public void setConfiguration(Properties config) {
		this.expected = config.getProperty(RestrictionRules.MIN.toString());
	}

}
