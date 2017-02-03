package org.brandao.brutos.scope;

import java.util.HashMap;

public class ThreadScope implements Scope {

	private static final ThreadLocal threadLocal;

	static {
		threadLocal = new ThreadLocal();
	}

	public ThreadScope() {
	}

	public static boolean create() {

		if (threadLocal.get() == null) {
			threadLocal.set(new HashMap());
			return true;
		} else
			return false;

	}

	public static void destroy() {
		threadLocal.remove();
	}

	public void put(String name, Object value) {
		HashMap map = (HashMap) threadLocal.get();
		map.put(name, value);
	}

	public Object get(String name) {
		HashMap map = (HashMap) threadLocal.get();
		return map.get(name);
	}

	public Object getCollection(String name) {
		return get(name);
	}

	public void remove(String name) {
		HashMap map = (HashMap) threadLocal.get();
		map.remove(name);
	}

}
