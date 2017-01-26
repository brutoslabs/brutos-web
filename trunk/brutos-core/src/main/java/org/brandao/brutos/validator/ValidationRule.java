


package org.brandao.brutos.validator;

import java.util.Properties;


public interface ValidationRule {

    
    void setConfiguration(Properties config);
    
    
    void validate(Object source, Object value)
            throws ValidatorException;
}
