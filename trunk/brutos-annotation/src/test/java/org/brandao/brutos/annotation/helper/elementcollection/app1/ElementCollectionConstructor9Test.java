package org.brandao.brutos.annotation.helper.elementcollection.app1;

import java.util.List;

import org.brandao.brutos.annotation.ElementCollection;
import org.brandao.brutos.annotation.MappingTypes;
import org.brandao.brutos.annotation.Transient;

public class ElementCollectionConstructor9Test {

	@Transient
	private List<ElementCollectionBeanTest0> entity;
	
	public ElementCollectionConstructor9Test(
			@ElementCollection(mappingType=MappingTypes.SIMPLE, type=ElementCollectionBeanTest0Type.class)
			List<ElementCollectionBeanTest0> entity){
		this.entity = entity;
	}

	public List<ElementCollectionBeanTest0> getEntity() {
		return entity;
	}

	public void setEntity(List<ElementCollectionBeanTest0> entity) {
		this.entity = entity;
	}
	
}
