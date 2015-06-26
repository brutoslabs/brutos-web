package org.brandao.brutos.annotation.helper.any.app3;

import java.util.Arrays;
import java.util.List;

import org.brandao.brutos.annotation.Constructor;

public class SetProperty extends Property{

	private List<String> values;

	@Constructor
	public SetProperty(){
	}
	
	public SetProperty(String name, String[] values){
		super(name);
		this.values = Arrays.asList(values);
	}
	
	public List<String> getValues() {
		return values;
	}

	public void setValues(List<String> values) {
		this.values = values;
	}
	
}
