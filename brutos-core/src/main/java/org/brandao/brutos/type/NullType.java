package org.brandao.brutos.type;

import java.io.IOException;
import org.brandao.brutos.MvcResponse;

public class NullType extends AbstractType implements Type {

	private Class type;

	public NullType(Class type) {
		this.type = type;
	}

	public Class getClassType() {
		return type;
	}

	public Object convert(Object value) {
		return null;
	}

	public void show(MvcResponse response, Object value) throws IOException {
		response.process("null");
	}

}
