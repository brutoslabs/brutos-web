package org.brandao.brutos;

import java.util.HashMap;

public class DataType {

	private static final HashMap<String, DataType> defaultTypes =
		new HashMap<String, DataType>();
	
	public static DataType valueOf(String value){
		
		if(value == null){
			throw new NullPointerException();
		}
		
		DataType r = defaultTypes.get(value);
		return r == null? new DataType(value) : r;
	}
	
	private final String name;

	public DataType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
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
		DataType other = (DataType) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	

}
