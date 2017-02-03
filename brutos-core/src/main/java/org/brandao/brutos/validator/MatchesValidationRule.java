package org.brandao.brutos.validator;

import java.util.Properties;

public class MatchesValidationRule implements ValidationRule {

	private String regex;

	public void validate(Object source, Object value) {
		if (value != null && !((String) value).matches(this.regex))
			throw new ValidatorException();
	}

	public void setConfiguration(Properties config) {
		this.regex = config.getProperty(RestrictionRules.MATCHES.toString());
	}

}
