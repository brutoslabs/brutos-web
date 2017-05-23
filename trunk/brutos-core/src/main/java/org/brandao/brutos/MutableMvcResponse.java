package org.brandao.brutos;

public interface MutableMvcResponse extends MvcResponse{

	void setResult(Object value);
	
	void setRequest(MvcRequest value);
	
}
