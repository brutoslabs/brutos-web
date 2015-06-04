package org.brandao.brutos.annotation.helper.keycollection.app1;

import java.util.List;
import java.util.Map;

import org.brandao.brutos.annotation.ElementCollection;
import org.brandao.brutos.annotation.KeyCollection;
import org.brandao.brutos.type.StringType;

public class KeyCollectionConstructor8Test {

	private Map entity;
	
	public KeyCollectionConstructor8Test(@KeyCollection(type=StringType.class) @ElementCollection(type=StringType.class)Map entity){
		this.entity = entity;
	}

	public Map getEntity() {
		return entity;
	}

	public void setEntity(Map entity) {
		this.entity = entity;
	}
	
}
