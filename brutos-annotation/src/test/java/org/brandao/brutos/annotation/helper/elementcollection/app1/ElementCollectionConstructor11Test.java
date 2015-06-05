package org.brandao.brutos.annotation.helper.elementcollection.app1;

import java.util.List;

import org.brandao.brutos.annotation.Transient;

public class ElementCollectionConstructor11Test {

	@Transient
	private List<ElementCollectionBeanTest0> entity;
	
	public ElementCollectionConstructor11Test(List<ElementCollectionBeanTest0> entity){
		this.entity = entity;
	}

	public List<ElementCollectionBeanTest0> getEntity() {
		return entity;
	}

	public void setEntity(List<ElementCollectionBeanTest0> entity) {
		this.entity = entity;
	}
	
}
