package org.brandao.brutos.annotation.helper.keycollection.app1;

import java.util.Map;

import org.brandao.brutos.annotation.KeyCollection;
import org.brandao.brutos.annotation.MappingTypes;
import org.brandao.brutos.annotation.Transient;

public class KeyCollectionConstructor10Test {

	@Transient
	private Map<KeyCollectionBeanTest0,String> entity;
	
	public KeyCollectionConstructor10Test(
			@KeyCollection(mappingType=MappingTypes.COMPLEX)Map<KeyCollectionBeanTest0,String> entity){
		this.entity = entity;
	}

	public Map<KeyCollectionBeanTest0,String> getEntity() {
		return entity;
	}

	public void setEntity(Map<KeyCollectionBeanTest0,String> entity) {
		this.entity = entity;
	}
	
}
