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

import java.util.logging.Level;

/**
 * 
 * @author Brandao
 */
public class JavaLogger implements Logger {

	private java.util.logging.Logger logger;

	public JavaLogger(java.util.logging.Logger logger) {
		this.logger = logger;
	}

	public void info(String message) {
		logger.info(message);
	}

	public void info(String message, Throwable t) {
		logger.info(message);
	}

	public void debug(String message) {
		logger.config(message);
	}

	public void debug(String message, Throwable t) {
		logger.config(message);
	}

	public void error(String message) {
		logger.severe(message);
	}

	public void error(String message, Throwable t) {
		logger.severe(message);
	}

	public void fatal(String message) {
		logger.severe(message);
	}

	public void fatal(String message, Throwable t) {
		logger.severe(message);
	}

	public void warn(String message) {
		logger.warning(message);
	}

	public void warn(String message, Throwable t) {
		logger.warning(message);
	}

	public boolean isDebugEnabled() {
		return logger.isLoggable(Level.CONFIG);
	}

	public boolean isInfoEnabled() {
		return logger.isLoggable(Level.INFO);
	}

}
