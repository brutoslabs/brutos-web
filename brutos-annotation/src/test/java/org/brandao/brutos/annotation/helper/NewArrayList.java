package org.brandao.brutos.annotation.helper;

import java.util.ArrayList;

import org.brandao.brutos.annotation.Transient;

@SuppressWarnings("serial")
public class NewArrayList
	extends ArrayList<Object>{

	@Transient
	public boolean isEmpty(){
		return super.isEmpty();
	}
	
}
