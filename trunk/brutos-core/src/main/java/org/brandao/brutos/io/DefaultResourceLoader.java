package org.brandao.brutos.io;

import java.net.MalformedURLException;
import java.net.URL;
import org.brandao.brutos.BrutosException;

public class DefaultResourceLoader implements ResourceLoader {

	private ClassLoader classLoader;

	public DefaultResourceLoader() {
		this(Thread.currentThread().getContextClassLoader());
	}

	public DefaultResourceLoader(ClassLoader classLoader) {
		this.classLoader = classLoader;
	}

	public Resource getResource(String path) {
		if (path.startsWith(CLASSPATH_URL_PREFIX)) {
			return new ClassPathResource(getClassloader(),
					path.substring(CLASSPATH_URL_PREFIX.length()));
		} else {
			try {
				URL url = new URL(path);
				return new UrlResource(url);
			} catch (MalformedURLException e) {
				return getContextResource(path);
			}
		}
	}

	protected Resource getContextResource(String path) {
		throw new BrutosException("not found: " + path);
	}

	public ClassLoader getClassloader() {
		return this.classLoader;
	}

}
