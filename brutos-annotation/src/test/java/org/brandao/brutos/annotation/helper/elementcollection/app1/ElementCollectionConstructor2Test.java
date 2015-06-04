package org.brandao.brutos.annotation.helper.elementcollection.app1;

import java.util.List;

import org.brandao.brutos.annotation.ElementCollection;

public class ElementCollectionConstructor2Test {

	private List<Integer> entity;
	
	public ElementCollectionConstructor2Test(@ElementCollection List<Integer> entity){
		this.entity = entity;
	}

	public List<Integer> getEntity() {
		return entity;
	}

	public void setEntity(List<Integer> entity) {
		this.entity = entity;
	}
	
}
