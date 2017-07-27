package org.brandao.brutos.annotation.helper.elementcollection.app1;

import java.util.ArrayList;

import org.brandao.brutos.annotation.ElementCollection;
import org.brandao.brutos.annotation.Transient;

@SuppressWarnings("serial")
@ElementCollection(bean="subElement")
public class CustomList 
	extends ArrayList<Integer>{

	/* overrride mappging of the ArrayList type */

	@Transient
	private Object[] elementData;
	
	@Transient
	private boolean empty;
	
	@Transient
	private int size;
	
	@Transient
	private int modCount;
	
	public CustomList(){
		super();
	}
	
}
