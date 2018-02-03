package org.brandao.brutos.proxy;

public class ProxyUtil {

	public static Object getTarget(Object o){
		if(o instanceof EntityProxy){
			EntityProxy ep = (EntityProxy)o;
			EntityProxyHandler entityProxyHandler = ep.getEntityProxyHandler();
			return entityProxyHandler.getTarget();
		}
		else{
			return o;
		}
	}
}
