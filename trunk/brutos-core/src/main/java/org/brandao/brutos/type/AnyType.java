package org.brandao.brutos.type;

import java.io.IOException;

import org.brandao.brutos.MvcResponse;

public class AnyType implements Type{

	private Class<?> type;
	
	public AnyType(Class<?> type){
		this.type = type;
	}
	
	public Object convert(Object value) {
		if(value == null)
			return null;
		else
		if(type.isAssignableFrom(value.getClass()))
			return value;
		else
			throw new UnknownTypeException(value.getClass().getSimpleName());
	}

	public void show(MvcResponse response, Object value) throws IOException {
		response.process(value);
	}

	public Class getClassType() {
		return this.type;
	}

	public void setClassType(Class value) {
		this.type = value;
	}

	public boolean isAlwaysRender() {
		return false;
	}

}
