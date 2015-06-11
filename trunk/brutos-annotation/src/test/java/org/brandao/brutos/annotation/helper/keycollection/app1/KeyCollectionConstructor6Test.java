package org.brandao.brutos.annotation.helper.keycollection.app1;

import java.util.Date;
import java.util.Map;

import org.brandao.brutos.annotation.KeyCollection;
import org.brandao.brutos.annotation.Transient;

public class KeyCollectionConstructor6Test {

	@Transient
	private Map<Date,String> entity;
	
	public KeyCollectionConstructor6Test(
			@KeyCollection(temporal="mm-dd-yyyy")Map<Date,String> entity){
		this.entity = entity;
	}

	public Map<Date,String> getEntity() {
		return entity;
	}

	public void setEntity(Map<Date,String> entity) {
		this.entity = entity;
	}
	
}
