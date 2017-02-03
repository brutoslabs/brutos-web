package org.brandao.brutos.validator;

import java.util.Properties;

public class RequiredValidationRule implements ValidationRule {

	public void validate(Object source, Object value) {
		if (value == null)
			throw new ValidatorException();
	}

	public void setConfiguration(Properties config) {
	}

}
