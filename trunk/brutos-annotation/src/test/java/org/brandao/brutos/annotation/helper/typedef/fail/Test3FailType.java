package org.brandao.brutos.annotation.helper.typedef.fail;

import org.brandao.brutos.annotation.TypeDef;
import org.brandao.brutos.annotation.helper.typedef.app1.Test;
import org.brandao.brutos.type.Type;
import org.brandao.brutos.type.TypeFactory;

@TypeDef
public class Test3FailType{

	public Type getInstance() {
		return null;
	}

	public boolean matches(Class type) {
		return Type.class.isAssignableFrom(type);
	}

	public Class getClassType() {
		return Test.class;
	}

}
