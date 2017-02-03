package org.brandao.brutos;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.brandao.brutos.mapping.Action;

public class DefaultResourceAction implements ResourceAction {

	protected Action action;

	public DefaultResourceAction(Action action) {
		this.action = action;
	}

	public Object invoke(Object source, Object[] args)
			throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {

		return action.invoke(source, args);
	}

	public Class getResourceClass() {
		return action.getMethod() == null ? null : action.getMethod()
				.getDeclaringClass();
	}

	public Method getMethod() {
		return action.getMethod();
	}

	public Class returnType() {
		return action.getMethod().getReturnType();
	}

	public Class[] getParametersType() {
		return action.getMethod().getParameterTypes();
	}

	public boolean isAbstract() {
		return this.action.isAbstract();
	}

	public Action getMethodForm() {
		return action;
	}

}
