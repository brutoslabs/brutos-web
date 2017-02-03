package org.brandao.brutos.logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class JavaLoggerProvider extends LoggerProvider {

	private Map cacheLoggers;

	public JavaLoggerProvider() {
		this.cacheLoggers = new HashMap();
	}

	public void configure(Properties config) {
	}

	public Logger getLogger(Class clazz) {
		return getLogger(clazz.getSimpleName());
	}

	public Logger getLogger(String name) {
		if (!cacheLoggers.containsKey(name)) {
			java.util.logging.Logger jLogger = java.util.logging.Logger
					.getLogger(name);

			Logger logger = new JavaLogger(jLogger);
			cacheLoggers.put(name, logger);
			return logger;
		} else
			return (Logger) cacheLoggers.get(name);
	}

	public void destroy() {
		cacheLoggers.clear();
	}

}
