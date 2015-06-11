package org.brandao.brutos.annotation.helper.keycollection.app1;

import java.util.Map;

import org.brandao.brutos.annotation.KeyCollection;
import org.brandao.brutos.annotation.ScopeType;
import org.brandao.brutos.annotation.Transient;

public class KeyCollectionConstructor5Test {

	@Transient
	private Map<Integer,String> entity;
	
	public KeyCollectionConstructor5Test(
			@KeyCollection(scope=ScopeType.SESSION)Map<Integer,String> entity){
		this.entity = entity;
	}

	public Map<Integer,String> getEntity() {
		return entity;
	}

	public void setEntity(Map<Integer,String> entity) {
		this.entity = entity;
	}
	
}
