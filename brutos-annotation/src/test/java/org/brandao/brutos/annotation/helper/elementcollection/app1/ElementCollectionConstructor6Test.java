package org.brandao.brutos.annotation.helper.elementcollection.app1;

import java.util.Date;
import java.util.List;

import org.brandao.brutos.annotation.ElementCollection;
import org.brandao.brutos.annotation.Transient;

public class ElementCollectionConstructor6Test {

	@Transient
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
