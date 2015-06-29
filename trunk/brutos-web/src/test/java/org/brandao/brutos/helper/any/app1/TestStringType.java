package org.brandao.brutos.helper.any.app1;

import org.brandao.brutos.type.StringType;

public class TestStringType 
	extends StringType{

    public Object convert(Object value) {
    	String str = (String) super.convert(value);
    	return str == null? null : "xx-" + str; 
    }
	
}
