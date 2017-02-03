package org.brandao.brutos;

import java.util.HashMap;
import java.util.Map;

public class ScopeType {

	public static final ScopeType REQUEST = new ScopeType("request");
	public static final ScopeType SINGLETON = new ScopeType("singleton");
	public static final ScopeType PARAM = new ScopeType("param");
	public static final ScopeType THREAD = new ScopeType("thread");
	public static final ScopeType IOC = new ScopeType("ioc");
	public static final ScopeType CONTROLLER = new ScopeType("controller");

	protected final static Map defaultScopes = new HashMap();

	static {
		defaultScopes.put(REQUEST.toString(), REQUEST);
		defaultScopes.put(THREAD.toString(), THREAD);
		defaultScopes.put(PARAM.toString(), PARAM);
		defaultScopes.put(SINGLETON.toString(), SINGLETON);
		defaultScopes.put(IOC.toString(), IOC);
		defaultScopes.put(CONTROLLER.toString(), CONTROLLER);
	}

	private String name;

	public ScopeType(String name) {
		this.name = name;
	}

	public String toString() {
		return this.name;
	}

	public static ScopeType valueOf(String value) {
		if (defaultScopes.containsKey(value))
			return (ScopeType) defaultScopes.get(value);
		else
			return new ScopeType(value);
	}
}
