package org.brandao.brutos;

import org.brandao.brutos.type.TypeFactory;

public interface TypeRegistry {

	void registerType(TypeFactory factory);

	void registerType(Class<?> classType, Class<?> type);

	TypeFactory getRegistredType(Class<?> classType);

	boolean isStandardType(Class<?> clazz);

}
