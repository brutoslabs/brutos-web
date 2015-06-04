package org.brandao.brutos.annotation.helper.elementcollection.app1;

import java.util.Date;
import java.util.List;

import org.brandao.brutos.annotation.ElementCollection;

public class ElementCollectionConstructor6Test {

	private List<Date> entity;
	
	public ElementCollectionConstructor6Test(@ElementCollection(temporal="mm-dd-yyyy")List<Date> entity){
		this.entity = entity;
	}

	public List<Date> getEntity() {
		return entity;
	}

	public void setEntity(List<Date> entity) {
		this.entity = entity;
	}
	
}
