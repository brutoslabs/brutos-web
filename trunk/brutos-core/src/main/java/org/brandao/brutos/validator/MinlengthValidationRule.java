package org.brandao.brutos.validator;

import java.util.Properties;
import org.brandao.brutos.type.IntegerType;
import org.brandao.brutos.type.Type;

public class MinlengthValidationRule implements ValidationRule {

	private Integer expected;

	public void validate(Object source, Object value) {
		if (value != null && ((String) value).length() < expected.intValue())
			throw new ValidatorException();
	}

	public void setConfiguration(Properties config) {
		Type integerType = new IntegerType();
		this.expected = (Integer) integerType.convert(config
				.getProperty(RestrictionRules.MINLENGTH.toString()));
	}

}
