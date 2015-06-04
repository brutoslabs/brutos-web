package org.brandao.brutos.annotation.helper.keycollection.app1;

import java.util.List;
import java.util.Map;

import org.brandao.brutos.annotation.helper.EnumTest;

public class KeyCollectionConstructor12Test {

	private Map<EnumTest, EnumTest> entity;
	
	public KeyCollectionConstructor12Test(Map<EnumTest, EnumTest> entity){
		this.entity = entity;
	}

	public Map<EnumTest, EnumTest> getEntity() {
		return entity;
	}

	public void setEntity(Map<EnumTest, EnumTest> entity) {
		this.entity = entity;
	}
	
}
