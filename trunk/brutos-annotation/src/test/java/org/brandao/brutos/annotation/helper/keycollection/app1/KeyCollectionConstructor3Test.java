package org.brandao.brutos.annotation.helper.keycollection.app1;

import java.util.Map;

import org.brandao.brutos.annotation.KeyCollection;
import org.brandao.brutos.annotation.Transient;

public class KeyCollectionConstructor3Test {

	@Transient
	private Map<Integer,String> entity;
	
	public KeyCollectionConstructor3Test(
			@KeyCollection(bean="elx")Map<Integer,String> entity){
		this.entity = entity;
	}

	public Map<Integer,String> getEntity() {
		return entity;
	}

	public void setEntity(Map<Integer,String> entity) {
		this.entity = entity;
	}
	
}
