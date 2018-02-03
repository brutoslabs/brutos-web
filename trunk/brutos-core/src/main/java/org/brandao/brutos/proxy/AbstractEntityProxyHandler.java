package org.brandao.brutos.proxy;

import java.lang.reflect.Method;

public abstract class AbstractEntityProxyHandler 
	implements EntityProxyHandler{

	protected Object target;
	
	protected boolean isGetEntityProxyHandlerMethod(Method method){
		return method.getDeclaringClass() == EntityProxy.class && 
				method.getName().equals("getEntityProxyHandler");
	}
	
	public Object getTarget(){
		return this.target;
	}
	
}
