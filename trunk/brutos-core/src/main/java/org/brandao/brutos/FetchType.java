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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FetchType other = (FetchType) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
}
