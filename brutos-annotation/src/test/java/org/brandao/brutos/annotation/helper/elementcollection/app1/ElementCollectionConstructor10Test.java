package org.brandao.brutos.annotation.helper.elementcollection.app1;

import java.util.List;

import org.brandao.brutos.annotation.ElementCollection;
import org.brandao.brutos.annotation.MappingTypes;

public class ElementCollectionConstructor10Test {

	private List<ElementCollectionBeanTest0> entity;
	
	public ElementCollectionConstructor10Test(@ElementCollection(mappingType=MappingTypes.COMPLEX)List<ElementCollectionBeanTest0> entity){
		this.entity = entity;
	}

	public List<ElementCollectionBeanTest0> getEntity() {
		return entity;
	}

	public void setEntity(List<ElementCollectionBeanTest0> entity) {
		this.entity = entity;
	}
	
}
