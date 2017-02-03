package org.brandao.brutos.bean;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class GetterProperty {

	private Field field;
	private Object object;
	private Method method;

	public GetterProperty(Field field, Method method, Object object) {

	}

	public GetterProperty(Field field, Object object) {
		this.field = field;
		this.object = object;
	}

	public GetterProperty(Method method, Object object) {
		this.method = method;
		this.object = object;
	}

	public Object get(Object o) throws Exception {
		return field != null ? field.get(o) : method.invoke(o, new Object[] {});
	}

	public Object get() throws Exception {
		return get(object);
	}

	public Method getMethod() {
		return method;
	}

	public Field getField() {
		return this.field;
	}

	public void setField(Field field) {
		this.field = field;
	}

	public void setMethod(Method method) {
		this.method = method;
	}
}
