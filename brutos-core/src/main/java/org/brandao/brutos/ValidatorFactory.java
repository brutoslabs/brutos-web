package org.brandao.brutos;

import java.util.Properties;
import org.brandao.brutos.validator.Validator;

public interface ValidatorFactory {

	void configure(Properties config);

	Validator getValidator(Properties config);

	void destroy();

}
