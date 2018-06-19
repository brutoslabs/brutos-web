package org.brandao.brutos.web;

import org.brandao.brutos.DispatcherType;

public class WebDispatcherType extends DispatcherType{

	public static final WebDispatcherType INCLUDE  = new WebDispatcherType("include");

	public static final WebDispatcherType REDIRECT = new WebDispatcherType("redirect");
	
	static {
		defaultDispatcher.put(INCLUDE.getName(),  INCLUDE);
		defaultDispatcher.put(REDIRECT.getName(), REDIRECT);
	}
	
	public WebDispatcherType(String name) {
		super(name);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.getName() == null) ? 0 : this.getName().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DispatcherType other = (DispatcherType) obj;
		if (this.getName() == null) {
			if (other.getName() != null)
				return false;
		} else if (!this.getName().equals(other.getName()))
			return false;
		return true;
	}
	
}
