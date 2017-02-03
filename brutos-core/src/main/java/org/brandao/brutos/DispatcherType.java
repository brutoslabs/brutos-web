package org.brandao.brutos;

import java.util.HashMap;
import java.util.Map;

public class DispatcherType {

	public static final DispatcherType INCLUDE = new DispatcherType("include");

	public static final DispatcherType FORWARD = new DispatcherType("forward");

	public static final DispatcherType REDIRECT = new DispatcherType("redirect");

	private static final Map defaultDispatcher = new HashMap();

	static {
		defaultDispatcher.put(INCLUDE.toString(), INCLUDE);
		defaultDispatcher.put(FORWARD.toString(), FORWARD);
		defaultDispatcher.put(REDIRECT.toString(), REDIRECT);
	}

	private String name;

	public DispatcherType(String name) {
		this.name = name;
	}

	public String toString() {
		return this.name;
	}

	public static DispatcherType valueOf(String value) {
		if (value == null)
			return null;
		else if (defaultDispatcher.containsKey(value))
			return (DispatcherType) defaultDispatcher.get(value);
		else
			return new DispatcherType(value);
	}
}
