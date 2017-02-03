package org.brandao.brutos.type;

import java.io.IOException;
import org.brandao.brutos.MvcResponse;

public class StringType extends AbstractType implements Type {

	public StringType() {
	}

	public Class getClassType() {
		return String.class;
	}

	public Object convert(Object value) {
		if (value == null)
			return null;
		else if (value instanceof String)
			return ((String) value).isEmpty() ? null : value;
		else
			throw new UnknownTypeException();
	}

	public void show(MvcResponse response, Object value) throws IOException {
		response.process(value);
	}

}
