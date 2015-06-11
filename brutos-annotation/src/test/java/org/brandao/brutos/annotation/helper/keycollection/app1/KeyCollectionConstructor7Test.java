package org.brandao.brutos.annotation.helper.keycollection.app1;

import java.util.Map;

import org.brandao.brutos.annotation.ElementCollection;
import org.brandao.brutos.annotation.KeyCollection;
import org.brandao.brutos.annotation.Transient;

public class KeyCollectionConstructor7Test {

	@Transient
	private Map entity;
	
	public KeyCollectionConstructor7Test(
			@KeyCollection(target=Integer.class)
			@ElementCollection(target=String.class)
			Map entity){
		this.entity = entity;
	}

	public Map getEntity() {
		return entity;
	}

	public void setEntity(Map entity) {
		this.entity = entity;
	}
	
}
