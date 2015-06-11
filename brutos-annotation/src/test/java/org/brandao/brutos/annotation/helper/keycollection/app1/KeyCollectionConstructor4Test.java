package org.brandao.brutos.annotation.helper.keycollection.app1;

import java.util.Map;

import org.brandao.brutos.annotation.EnumerationType;
import org.brandao.brutos.annotation.KeyCollection;
import org.brandao.brutos.annotation.Transient;
import org.brandao.brutos.annotation.helper.EnumTest;

public class KeyCollectionConstructor4Test {

	@Transient
	private Map<EnumTest,String> entity;
	
	public KeyCollectionConstructor4Test(
			@KeyCollection(enumerated=EnumerationType.STRING)Map<EnumTest,String> entity){
		this.entity = entity;
	}

	public Map<EnumTest,String> getEntity() {
		return entity;
	}

	public void setEntity(Map<EnumTest,String> entity) {
		this.entity = entity;
	}
	
}
