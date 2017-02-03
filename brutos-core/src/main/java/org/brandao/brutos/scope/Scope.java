package org.brandao.brutos.scope;

public interface Scope {

	void put(String name, Object value);

	Object get(String name);

	Object getCollection(String name);

	void remove(String name);

}
