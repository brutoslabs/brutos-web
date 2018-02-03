package org.brandao.brutos.javassist;

import javassist.util.proxy.MethodHandler;

import org.brandao.brutos.mapping.BeanDecoder;
import org.brandao.brutos.proxy.BeanEntityProxyHandler;

public class JavassistBeanEntityProxyHandler 
	extends BeanEntityProxyHandler
	implements MethodHandler {

	public JavassistBeanEntityProxyHandler(Object metadata, Object data, 
			BeanDecoder decoder) {
		super(metadata, data, decoder);
	}

}
