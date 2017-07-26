package org.brandao.brutos;

import java.util.HashMap;
import java.util.Map;

public class FetchType {

	public static final FetchType LAZY  = new FetchType("lazy");

	public static final FetchType EAGER = new FetchType("eager");

	private static final Map<String, FetchType> defaultEntities = 
			new HashMap<String, FetchType>();

	static {
		defaultEntities.put(LAZY.toString(),  LAZY);
		defaultEntities.put(EAGER.toString(), EAGER);
	}

	private String name;

	public FetchType(String name) {
		this.name = name;
	}

	public String toString() {
		return this.name;
	}

	public static FetchType valueOf(String value) {
		if (value == null)
			return null;
		else if (defaultEntities.containsKey(value))
			return defaultEntities.get(value);
		else
			return new FetchType(value);
	}
	
}
