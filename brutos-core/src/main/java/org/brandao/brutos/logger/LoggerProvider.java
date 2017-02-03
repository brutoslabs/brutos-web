package org.brandao.brutos.logger;

import java.util.Properties;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.ClassUtil;

public abstract class LoggerProvider {

	private static LoggerProvider currentLoggerProvider;

	static {
		Properties config = new Properties();
		setCurrentLoggerProvider(getProvider(config));
	}

	public static LoggerProvider getProvider(Properties config) {

		String loggerName = config.getProperty(
				"org.brandao.brutos.logger.provider",
				"org.brandao.brutos.logger.JavaLoggerProvider");

		LoggerProvider logger = null;

		try {
			Class loggerClass = ClassUtil.get(loggerName);
			logger = (LoggerProvider) ClassUtil.getInstance(loggerClass);

		} catch (ClassNotFoundException e) {
			throw new BrutosException(e);
		} catch (InstantiationException e) {
			throw new BrutosException(e);
		} catch (IllegalAccessException e) {
			throw new BrutosException(e);
		}

		logger.configure(config);
		return logger;
	}

	public static LoggerProvider getCurrentLoggerProvider() {
		return currentLoggerProvider;
	}

	public static void setCurrentLoggerProvider(
			LoggerProvider aCurrentLoggerProvider) {
		currentLoggerProvider = aCurrentLoggerProvider;
	}

	public abstract void configure(Properties config);

	public abstract void destroy();

	public abstract Logger getLogger(String name);

	public abstract Logger getLogger(Class clazz);

}
