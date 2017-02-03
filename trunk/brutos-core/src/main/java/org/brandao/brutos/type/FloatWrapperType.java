package org.brandao.brutos.type;

import java.io.IOException;
import org.brandao.brutos.MvcResponse;

public class FloatWrapperType extends AbstractType implements Type {

	public FloatWrapperType() {
	}

	public Class getClassType() {
		return Float.class;
	}

	public Object convert(Object value) {
		if (value instanceof Float)
			return value;
		else if (value instanceof String)
			return ((String) value).isEmpty() ? null : Float
					.valueOf((String) value);
		else if (value == null)
			return null;
		else
			throw new UnknownTypeException();
	}

	public void show(MvcResponse response, Object value) throws IOException {
		response.process(value);
	}

}
