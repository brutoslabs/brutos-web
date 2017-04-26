package org.brandao.brutos.mapping;

public class SimpleKeyMap {

	private String name;
	
	private String prefix;

	public SimpleKeyMap(String name, String prefix) {
		this.name = name;
		this.prefix = prefix;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	
}
