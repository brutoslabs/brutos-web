package org.brandao.brutos.type;

public class ObjectTypeFactory implements TypeFactory {

	public ObjectTypeFactory() {
	}

	public Type getInstance() {
		return new ObjectType();
	}

	public Class getClassType() {
		return Object.class;
	}

	public boolean matches(Class type) {
		return true;
	}

}
