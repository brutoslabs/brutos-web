package org.brandao.brutos.annotation.helper.typedef.app1;

import org.brandao.brutos.type.Type;
import org.brandao.brutos.type.TypeFactory;

public class Test1TypeFactory 
	implements TypeFactory{

	public Type getInstance() {
		return null;
	}

	public boolean matches(Class type) {
		return Test.class.isAssignableFrom(type);
	}

	public Class getClassType() {
		return Test.class;
	}

}
