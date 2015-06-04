package org.brandao.brutos.annotation.helper.keycollection.app1;

import java.util.List;
import java.util.Map;

import org.brandao.brutos.annotation.ElementCollection;
import org.brandao.brutos.annotation.KeyCollection;
import org.brandao.brutos.annotation.MappingTypes;

public class KeyCollectionConstructor9Test {

	private Map<KeyCollectionBeanTest0, KeyCollectionBeanTest0> entity;
	
	public KeyCollectionConstructor9Test(@KeyCollection(mappingType=MappingTypes.SIMPLE) @ElementCollection(mappingType=MappingTypes.SIMPLE)Map<KeyCollectionBeanTest0,KeyCollectionBeanTest0> entity){
		this.entity = entity;
	}

	public Map<KeyCollectionBeanTest0,KeyCollectionBeanTest0> getEntity() {
		return entity;
	}

	public void setEntity(Map<KeyCollectionBeanTest0,KeyCollectionBeanTest0> entity) {
		this.entity = entity;
	}
	
}
