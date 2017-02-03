package org.brandao.brutos.type;

public interface TypeFactory {

	Type getInstance();

	boolean matches(Class type);

	Class getClassType();

}
