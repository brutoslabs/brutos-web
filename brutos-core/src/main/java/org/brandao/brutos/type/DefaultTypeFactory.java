package org.brandao.brutos.type;

import org.brandao.brutos.BrutosException;

public class DefaultTypeFactory implements TypeFactory {

	private Class type;
	private Class classType;

	public DefaultTypeFactory(Class type, Class classType) {

		if (!Type.class.isAssignableFrom(type))
			throw new BrutosException("invalid class type: " + type.getName());

		this.type = type;
		this.classType = classType;
	}

	public Type getInstance() {
		try {
			return (Type) type.newInstance();
		} catch (Exception e) {
			throw new BrutosException(e);
		}
	}

	public Class getClassType() {
		return this.classType;
	}

	public boolean matches(Class type) {
		return type == this.classType;
	}

}
