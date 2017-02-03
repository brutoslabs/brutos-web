package org.brandao.brutos.type;

import java.io.IOException;
import org.brandao.brutos.MvcResponse;

public class ByteWrapperType extends AbstractType {

	public ByteWrapperType() {
	}

	public Object toValue(String value) {
		try {
			return Byte.valueOf(value);
		} catch (Exception e) {
			return new Byte((byte) 0);
		}
	}

	public Class getClassType() {
		return Byte.class;
	}

	public Object convert(Object value) {
		if (value instanceof Byte)
			return value;
		else if (value instanceof String)
			return ((String) value).isEmpty() ? null : Byte
					.valueOf((String) value);
		else if (value == null)
			return null;
		else
			throw new UnknownTypeException(value.getClass().getName());
	}

	public void show(MvcResponse response, Object value) throws IOException {
		response.process(value);
	}

}
