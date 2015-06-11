package org.brandao.brutos.annotation.helper.elementcollection.app1;

import java.util.ArrayList;

import org.brandao.brutos.annotation.ElementCollection;
import org.brandao.brutos.annotation.Transient;

@SuppressWarnings("serial")
@ElementCollection(bean="subElement")
public class CustomList 
	extends ArrayList<Integer>{

	@Transient
	public boolean isEmpty(){
		return super.isEmpty();
	}
	
}
