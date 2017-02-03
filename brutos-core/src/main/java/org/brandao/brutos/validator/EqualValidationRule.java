package org.brandao.brutos.validator;

import java.util.Properties;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.mapping.DependencyBean;
import org.brandao.brutos.mapping.UseBeanData;
import org.brandao.brutos.type.Type;

public class EqualValidationRule implements ValidationRule {

	private Object expected;

	public void validate(Object source, Object value) {
		Type valueType = null;

		if (source instanceof DependencyBean)
			valueType = ((DependencyBean) source).getType();
		else if (source instanceof UseBeanData)
			valueType = ((UseBeanData) source).getType();
		else
			throw new BrutosException("invalid source: " + source);

		Object convertedValue = valueType.convert(this.expected);

		if (convertedValue != null && !convertedValue.equals(value))
			throw new ValidatorException();
	}

	public void setConfiguration(Properties config) {
		this.expected = config.get(RestrictionRules.EQUAL.toString());
	}

}
