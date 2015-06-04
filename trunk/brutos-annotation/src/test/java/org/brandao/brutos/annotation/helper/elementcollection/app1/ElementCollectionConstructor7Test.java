package org.brandao.brutos.annotation.helper.elementcollection.app1;

import java.util.List;

import org.brandao.brutos.annotation.ElementCollection;

public class ElementCollectionConstructor7Test {

	private List entity;
	
	public ElementCollectionConstructor7Test(@ElementCollection(target=Integer.class)List entity){
		this.entity = entity;
	}

	public List getEntity() {
		return entity;
	}

	public void setEntity(List entity) {
		this.entity = entity;
	}
	
}
