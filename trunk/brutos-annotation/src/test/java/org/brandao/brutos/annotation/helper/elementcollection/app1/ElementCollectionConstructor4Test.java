package org.brandao.brutos.annotation.helper.elementcollection.app1;

import java.util.List;

import org.brandao.brutos.annotation.ElementCollection;
import org.brandao.brutos.annotation.EnumerationType;
import org.brandao.brutos.annotation.Transient;
import org.brandao.brutos.annotation.helper.EnumTest;

public class ElementCollectionConstructor4Test {

	@Transient
	private List<EnumTest> entity;
	
	public ElementCollectionConstructor4Test(@ElementCollection(enumerated=EnumerationType.STRING)List<EnumTest> entity){
		this.entity = entity;
	}

	public List<EnumTest> getEntity() {
		return entity;
	}

	public void setEntity(List<EnumTest> entity) {
		this.entity = entity;
	}
	
}
