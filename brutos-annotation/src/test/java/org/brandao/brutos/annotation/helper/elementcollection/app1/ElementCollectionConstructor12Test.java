package org.brandao.brutos.annotation.helper.elementcollection.app1;

import java.util.List;

import org.brandao.brutos.annotation.helper.EnumTest;

public class ElementCollectionConstructor12Test {

	private List<EnumTest> entity;
	
	public ElementCollectionConstructor12Test(List<EnumTest> entity){
		this.entity = entity;
	}

	public List<EnumTest> getEntity() {
		return entity;
	}

	public void setEntity(List<EnumTest> entity) {
		this.entity = entity;
	}
	
}
