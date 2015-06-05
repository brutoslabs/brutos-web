package org.brandao.brutos.annotation.helper.elementcollection.app1;

import java.util.List;

import org.brandao.brutos.annotation.ElementCollection;
import org.brandao.brutos.annotation.Transient;

public class ElementCollectionConstructor7Test {

	@Transient
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
