package org.brandao.brutos;

import java.util.Properties;

public class ObjectFactoryWrapper implements ObjectFactory {

	private ObjectFactory objectFactory;

	public ObjectFactoryWrapper(ObjectFactory objectFactory) {
		this.objectFactory = objectFactory;
	}

	public Object getBean(String name) {
		return objectFactory.getBean(name);
	}

	public void configure(Properties properties) {
		objectFactory.configure(properties);
	}

	public void destroy() {
		objectFactory.destroy();
	}

	public Object getBean(Class clazz) {
		return objectFactory.getBean(clazz);
	}

}
