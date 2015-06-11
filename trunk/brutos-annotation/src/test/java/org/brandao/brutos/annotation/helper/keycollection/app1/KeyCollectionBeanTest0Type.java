package org.brandao.brutos.annotation.helper.keycollection.app1;

import java.io.IOException;

import org.brandao.brutos.MvcResponse;
import org.brandao.brutos.type.Type;

public class KeyCollectionBeanTest0Type implements Type{

	public Object convert(Object value) {
		if(value == null)
			return null;
		else{
			KeyCollectionBeanTest0 r = new KeyCollectionBeanTest0();
			r.setProperty((String) value);
			return r;
		}
	}

	public void show(MvcResponse response, Object value) throws IOException {
		// TODO Auto-generated method stub
		
	}

	public Class getClassType() {
		return KeyCollectionBeanTest0.class;
	}

	public void setClassType(Class value) {
	}

	public boolean isAlwaysRender() {
		return false;
	}

}
