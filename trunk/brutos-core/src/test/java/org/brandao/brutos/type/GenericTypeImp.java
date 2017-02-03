package org.brandao.brutos.type;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class GenericTypeImp implements ParameterizedType {

	private Type rawType;
	private Type[] typeArguments;

	public GenericTypeImp(Class rawType, Class[] typeArguments) {
		this.rawType = rawType;
		this.typeArguments = typeArguments;
	}

	public Type[] getActualTypeArguments() {
		return typeArguments;
	}

	public Type getRawType() {
		return rawType;
	}

	public Type getOwnerType() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

}
