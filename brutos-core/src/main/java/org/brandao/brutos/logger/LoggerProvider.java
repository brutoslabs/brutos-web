/*
 * Brutos Web MVC http://www.brutosframework.com.br/
 * Copyright (C) 2009-2017 Afonso Brandao. (afonso.rbn@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.brandao.brutos.logger;

import java.util.Properties;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.ClassUtil;

/**
 * 
 * @author Brandao
 */
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
