package org.brandao.brutos.annotation.helper.keycollection.app1;

import java.util.List;
import java.util.Map;

import org.brandao.brutos.annotation.ElementCollection;
import org.brandao.brutos.annotation.EnumerationType;
import org.brandao.brutos.annotation.KeyCollection;
import org.brandao.brutos.annotation.helper.EnumTest;

public class KeyCollectionConstructor4Test {

	private Map<EnumTest,EnumTest> entity;
	
	public KeyCollectionConstructor4Test(@KeyCollection(enumerated=EnumerationType.STRING) @ElementCollection(enumerated=EnumerationType.STRING)Map<EnumTest,EnumTest> entity){
		this.entity = entity;
	}

	public Map<EnumTest,EnumTest> getEntity() {
		return entity;
	}

	public void setEntity(Map<EnumTest,EnumTest> entity) {
		this.entity = entity;
	}
	
}
