package org.brandao.brutos.annotation.helper.bean;

import java.util.ArrayList;

import org.brandao.brutos.annotation.Transient;

@SuppressWarnings("serial")
public class NewArrayList
	extends ArrayList<Object>{

	@Transient
	private Object[] elementData;
	
	@Transient
	private boolean empty;
	
	@Transient
	private int size;
	
	@Transient
	private int modCount;
	
	public NewArrayList(){
		super();
	}
	
}
