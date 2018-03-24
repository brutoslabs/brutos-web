package org.brandao.brutos.mapping;

public class BeanLoadInfo {

	private StringBuilder path;
	
	private NodeBeanDecoder data;

	public BeanLoadInfo(StringBuilder path, NodeBeanDecoder data) {
		this.path = path;
		this.data = data;
	}

	public StringBuilder getPath() {
		return path;
	}

	public NodeBeanDecoder getData() {
		return data;
	}
	
}
