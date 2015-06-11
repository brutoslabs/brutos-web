package org.brandao.brutos.annotation.helper.keycollection.app1;

import java.util.Map;

import org.brandao.brutos.annotation.Transient;
import org.brandao.brutos.annotation.helper.EnumTest;

public class KeyCollectionConstructor12Test {

	@Transient
	private Map<EnumTest,String> entity;
	
	public KeyCollectionConstructor12Test(Map<EnumTest,String> entity){
		this.entity = entity;
	}

	public Map<EnumTest,String> getEntity() {
		return entity;
	}

	public void setEntity(Map<EnumTest,String> entity) {
		this.entity = entity;
	}
	
}
