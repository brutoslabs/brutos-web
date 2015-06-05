package org.brandao.brutos.annotation.helper.elementcollection.app1;

import java.util.List;

import org.brandao.brutos.annotation.ElementCollection;
import org.brandao.brutos.annotation.ScopeType;
import org.brandao.brutos.annotation.Transient;

public class ElementCollectionConstructor5Test {

	@Transient
	private List<Integer> entity;
	
	public ElementCollectionConstructor5Test(@ElementCollection(scope=ScopeType.SESSION)List<Integer> entity){
		this.entity = entity;
	}

	public List<Integer> getEntity() {
		return entity;
	}

	public void setEntity(List<Integer> entity) {
		this.entity = entity;
	}
	
}
