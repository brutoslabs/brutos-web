package org.brandao.brutos.mapping;

import java.lang.reflect.Method;

public class ActionListener {

	private Class classType;

	private Method preAction;

	private Method postAction;

	public ActionListener() {
	}

	public Method getPreAction() {
		return preAction;
	}

	public void setPreAction(Method preAction) {
		this.preAction = preAction;
	}

	public Method getPostAction() {
		return postAction;
	}

	public void setPostAction(Method postAction) {
		this.postAction = postAction;
	}

	public Class getClassType() {
		return classType;
	}

	public void setClassType(Class classType) {
		this.classType = classType;
	}

}
