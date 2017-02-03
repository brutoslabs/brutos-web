package org.brandao.brutos.test;

import org.brandao.brutos.ObjectFactory;
import java.util.Properties;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.ClassUtil;

public class MockObjectFactory implements ObjectFactory {

	public Object getBean(String name) {
		return null;
	}

	public Object getBean(Class clazz) {
		try {
			return ClassUtil.getInstance(clazz);
		} catch (Exception e) {
			throw new BrutosException(e);
		}
	}

	public void configure(Properties properties) {
	}

	public void destroy() {
	}

}
