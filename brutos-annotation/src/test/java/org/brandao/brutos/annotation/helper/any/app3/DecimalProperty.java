package org.brandao.brutos.annotation.helper.any.app3;

import org.brandao.brutos.annotation.Constructor;

public class DecimalProperty extends Property{

    private int length;

    private int decimals;
    
    public DecimalProperty(String name, int length, int decimals) {
		super(name);
		this.length = length;
		this.decimals = decimals;
	}

	@Constructor
    public DecimalProperty(){
    }
    
	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getDecimals() {
		return decimals;
	}

	public void setDecimals(int decimals) {
		this.decimals = decimals;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + decimals;
		result = prime * result + length;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		DecimalProperty other = (DecimalProperty) obj;
		if (decimals != other.decimals)
			return false;
		if (length != other.length)
			return false;
		return true;
	}

}
