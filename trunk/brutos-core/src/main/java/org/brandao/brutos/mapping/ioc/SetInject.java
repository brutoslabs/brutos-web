package org.brandao.brutos.mapping.ioc;

public class SetInject extends ComplexObjectInject {

	public SetInject(String name, Class valueType, Class type, String factory,
			Property[] props) {
		// super( type == null? java.util.HashSet.class : type, name, props );
		super(name, null, valueType, java.util.Set.class, factory, props);
		setType(type == null ? java.util.HashSet.class : type);
	}

}
