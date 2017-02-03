package org.brandao.brutos;

import java.util.HashMap;
import java.util.Map;

public class EnumerationType {

	public static final EnumerationType AUTO = new EnumerationType("auto");

	public static final EnumerationType ORDINAL = new EnumerationType("ordinal");

	public static final EnumerationType STRING = new EnumerationType("string");

	private final static Map defaultTypes = new HashMap();

	static {
		defaultTypes.put(AUTO.toString(), AUTO);
		defaultTypes.put(ORDINAL.toString(), ORDINAL);
		defaultTypes.put(STRING.toString(), STRING);
	}

	private String name;

	public EnumerationType(String name) {
		this.name = name;
	}

	public String toString() {
		return name;
	}

	public static EnumerationType valueOf(String value) {
		return (EnumerationType) defaultTypes.get(value);
	}

}
