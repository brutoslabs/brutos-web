package org.brandao.brutos.annotation.helper.keycollection.app1;

import java.util.List;
import java.util.Map;

import org.brandao.brutos.annotation.ElementCollection;
import org.brandao.brutos.annotation.KeyCollection;
import org.brandao.brutos.annotation.ScopeType;
import org.brandao.brutos.annotation.helper.EnumTest;

public class KeyCollectionConstructor5Test {

	private Map<Integer,Integer> entity;
	
	public KeyCollectionConstructor5Test(@KeyCollection(scope=ScopeType.SESSION) @ElementCollection(scope=ScopeType.SESSION)Map<Integer,Integer> entity){
		this.entity = entity;
	}

	public Map<Integer,Integer> getEntity() {
		return entity;
	}

	public void setEntity(Map<Integer,Integer> entity) {
		this.entity = entity;
	}
	
}
