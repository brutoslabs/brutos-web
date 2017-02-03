package org.brandao.brutos.type;

import java.io.IOException;
import java.lang.reflect.Array;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.MvcResponse;
import org.brandao.brutos.web.http.ParameterList;

public class DefaultArrayType extends AbstractType implements ArrayType {

	private Type componentType;
	private Class rawClass;

	public DefaultArrayType() {
	}

	private Object getArray(Object value) {
		try {
			ParameterList param = (ParameterList) value;
			Object objList = Array.newInstance(
					this.componentType.getClassType(), param.size());

			for (int i = 0; i < param.size(); i++)
				Array.set(objList, i, this.componentType.convert(param.get(i)));

			return objList;
		} catch (Exception e) {
			throw new BrutosException(e);
		}
	}

	public Object convert(Object value) {
		if (value instanceof ParameterList)
			return getArray(value);
		else
			return value;
	}

	public void show(MvcResponse response, Object value) throws IOException {
		response.process(value);
	}

	public void setComponentType(Type type) {
		this.componentType = type;
	}

	public Type getComponentType() {
		return this.componentType;
	}

	public void setRawClass(Class value) {
		this.rawClass = value;
	}

	public Class getRawClass() {
		return this.rawClass;
	}

}
