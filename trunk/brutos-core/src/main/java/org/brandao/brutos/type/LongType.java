package org.brandao.brutos.type;

import java.io.IOException;
import org.brandao.brutos.MvcResponse;

public class LongType extends AbstractType implements Type {

	private static final long DEFAULT_VALUE = 0L;

	public LongType() {
	}

	public Class getClassType() {
		return Long.TYPE;
	}

	public Object convert(Object value) {
		if (value instanceof Long)
			return value;
		else if (value instanceof String)
			return ((String) value).isEmpty() ? DEFAULT_VALUE : Long
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
