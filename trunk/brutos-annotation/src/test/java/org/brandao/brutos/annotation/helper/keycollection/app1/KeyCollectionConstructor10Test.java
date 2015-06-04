package org.brandao.brutos.annotation.helper.keycollection.app1;

import java.util.Map;

import org.brandao.brutos.annotation.ElementCollection;
import org.brandao.brutos.annotation.KeyCollection;
import org.brandao.brutos.annotation.MappingTypes;

public class KeyCollectionConstructor10Test {

	private Map<KeyCollectionBeanTest0, KeyCollectionBeanTest0> entity;
	
	public KeyCollectionConstructor10Test(@KeyCollection(mappingType=MappingTypes.COMPLEX)
			@ElementCollection(mappingType=MappingTypes.COMPLEX)Map<KeyCollectionBeanTest0, KeyCollectionBeanTest0> entity){
		this.entity = entity;
	}

	public Map<KeyCollectionBeanTest0, KeyCollectionBeanTest0> getEntity() {
		return entity;
	}

	public void setEntity(Map<KeyCollectionBeanTest0, KeyCollectionBeanTest0> entity) {
		this.entity = entity;
	}
	
}
