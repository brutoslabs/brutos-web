package org.brandao.brutos.javassist;

import javassist.util.proxy.MethodHandler;
import org.brandao.brutos.ConfigurableApplicationContext;
import org.brandao.brutos.Invoker;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.proxy.ActionHandlerImp;

public class JavassistActionHandler extends ActionHandlerImp implements
		MethodHandler {

	public JavassistActionHandler(Object resource, Controller form,
			ConfigurableApplicationContext context, Invoker invoker) {
		super(resource, form, context, invoker);
	}

}
