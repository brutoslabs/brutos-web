package org.brandao.brutos.validator;

import java.util.Properties;
import org.brandao.brutos.ValidatorFactory;


public class JSR303ValidatorFactory implements ValidatorFactory{

    public void configure(Properties config) {
    }

    public Validator getValidator(Properties config) {
        Validator validator = new JSR303Validator();
        validator.configure(config);
        return validator;
    }

    public void destroy() {
    }
    
}
