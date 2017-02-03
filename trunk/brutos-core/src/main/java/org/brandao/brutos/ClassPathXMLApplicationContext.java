package org.brandao.brutos;

import org.brandao.brutos.io.ClassPathResource;
import org.brandao.brutos.io.Resource;

public class ClassPathXMLApplicationContext extends
		AbstractXMLApplicationContext {

	private Resource[] resources;

	public ClassPathXMLApplicationContext(String[] locations) {
		this(locations, null, Thread.currentThread().getContextClassLoader(),
				null);
	}

	public ClassPathXMLApplicationContext(String location) {
		this(new String[] { location }, null, Thread.currentThread()
				.getContextClassLoader(), null);
	}

	public ClassPathXMLApplicationContext(String location,
			AbstractApplicationContext parent) {
		this(new String[] { location }, parent, Thread.currentThread()
				.getContextClassLoader(), null);
	}

	public ClassPathXMLApplicationContext(String[] locations, Class clazz) {
		this(locations, null, null, clazz);
	}

	public ClassPathXMLApplicationContext(String[] locations,
			ClassLoader classLoader) {
		this(locations, null, classLoader, null);
	}

	protected Resource getContextResource(String path) {
		return new ClassPathResource(this.getClassloader(), path);
	}

	public ClassPathXMLApplicationContext(String[] locations,
			AbstractApplicationContext parent, ClassLoader classLoader,
			Class clazz) {
		super(parent);

		resources = new Resource[locations.length];
		for (int i = 0; i < locations.length; i++) {

			if (clazz != null)
				resources[i] = new ClassPathResource(clazz, locations[i]);
			else if (classLoader != null)
				resources[i] = new ClassPathResource(classLoader, locations[i]);
		}
	}

	protected Resource[] getContextResources() {
		return resources;
	}

}
