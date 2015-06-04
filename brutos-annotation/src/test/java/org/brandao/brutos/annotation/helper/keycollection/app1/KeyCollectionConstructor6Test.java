package org.brandao.brutos.annotation.helper.keycollection.app1;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.brandao.brutos.annotation.ElementCollection;
import org.brandao.brutos.annotation.KeyCollection;

public class KeyCollectionConstructor6Test {

	private Map<Date,Date> entity;
	
	public KeyCollectionConstructor6Test(@KeyCollection(temporal="mm-dd-yyyy") @ElementCollection(temporal="mm-dd-yyyy")Map<Date,Date> entity){
		this.entity = entity;
	}

	public Map<Date,Date> getEntity() {
		return entity;
	}

	public void setEntity(Map<Date,Date> entity) {
		this.entity = entity;
	}
	
}
