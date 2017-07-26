package org.brandao.brutos.annotation.helper.elementcollection.app1;

import org.brandao.brutos.MvcResponse;
import org.brandao.brutos.type.Type;

public class ElementCollectionBeanTest0Type implements Type{

	public Object convert(Object value) {
		if(value == null)
			return null;
		else{
			ElementCollectionBeanTest0 r = new ElementCollectionBeanTest0();
			r.setProperty((String) value);
			return r;
		}
	}

	public Class<?> getClassType() {
		return ElementCollectionBeanTest0.class;
	}

	public void setClassType(Class<?> value) {
	}

	public boolean isAlwaysRender() {
		return false;
	}

	public String toString(Object value) {
		// TODO Auto-generated method stub
		return null;
	}


	public void show(MvcResponse response, Object value) {
		// TODO Auto-generated method stub
		
	}

}
