package org.brandao.brutos.javassist;

import javassist.util.proxy.MethodHandler;

import org.brandao.brutos.mapping.BeanDecoder;
import org.brandao.brutos.proxy.BeanHandlerImp;

public class JavassistBeanHandler 
	extends BeanHandlerImp
	implements MethodHandler {

	public JavassistBeanHandler(Object metadata, Object data, 
			BeanDecoder decoder) {
		super(metadata, data, decoder);
	}

}
