

package org.brandao.brutos.validator;

import java.util.Properties;


public class CustomValidationRule implements ValidationRule{

    private Integer start;

    private Integer end;

    public void validate(Object source, Object value)
            throws ValidatorException {

        if( !(value instanceof Number) )
            throw new ValidatorException("invalid type: " +  value.getClass());

        Integer val = new Integer(((Number)value).intValue());
        if( val.intValue() < start.intValue() || val.intValue() > end.intValue() )
            throw new ValidatorException();
    }

    public void setConfiguration(Properties config) {
        String range = config.getProperty("range");

        if( !range.matches("\\d+-\\d+") )
            throw new ValidatorException("invalid range syntax: " +  range );

        String[] vals = range.split("-");
        this.start = Integer.valueOf(vals[0]);
        this.end = Integer.valueOf(vals[1]);
    }

}
