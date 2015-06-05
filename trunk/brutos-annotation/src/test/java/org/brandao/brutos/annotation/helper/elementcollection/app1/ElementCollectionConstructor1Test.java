package org.brandao.brutos.annotation.helper.elementcollection.app1;

import java.util.List;

import org.brandao.brutos.annotation.Transient;

public class ElementCollectionConstructor1Test {

	@Transient
	private List<Integer> entity;
	
	public ElementCollectionConstructor1Test(List<Integer> entity){
		this.entity = entity;
	}

	public List<Integer> getEntity() {
		return entity;
	}

	public void setEntity(List<Integer> entity) {
		this.entity = entity;
	}
	
}
