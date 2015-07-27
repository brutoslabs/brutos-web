package org.brandao.examples;

public abstract class AbstractService 
	implements Service{

	protected Long price;
	
	public void setPrice(Long value) {
		this.price = value;
	}

	public Long getPrice() {
		return this.price;
	}

}
