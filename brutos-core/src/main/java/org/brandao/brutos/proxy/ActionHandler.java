package org.brandao.brutos.proxy;

import java.lang.reflect.Method;

public interface ActionHandler {

	Object invoke(Object self, Method thisMethod, Method proceed, Object[] args)
			throws Throwable;

}
