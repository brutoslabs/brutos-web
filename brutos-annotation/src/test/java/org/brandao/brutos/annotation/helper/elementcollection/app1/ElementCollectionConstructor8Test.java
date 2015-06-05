package org.brandao.brutos.annotation.helper.elementcollection.app1;

import java.util.List;

import org.brandao.brutos.annotation.ElementCollection;
import org.brandao.brutos.annotation.Transient;
import org.brandao.brutos.type.StringType;

public class ElementCollectionConstructor8Test {

	@Transient
	private List entity;
	
	public ElementCollectionConstructor8Test(@ElementCollection(type=StringType.class, target=String.class)List entity){
		this.entity = entity;
	}

	public List getEntity() {
		return entity;
	}

	public void setEntity(List entity) {
		this.entity = entity;
	}
	
}
