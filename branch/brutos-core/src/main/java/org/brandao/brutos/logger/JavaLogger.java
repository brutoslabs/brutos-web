/*
 * Brutos Web MVC http://brutos.sourceforge.net/
 * Copyright (C) 2009 Afonso Brandao. (afonso.rbn@gmail.com)
 *
 * This library is free software. You can redistribute it
 * and/or modify it under the terms of the GNU General Public
 * License (GPL) version 3.0 or (at your option) any later
 * version.
 * You may obtain a copy of the License at
 *
 * http://www.gnu.org/licenses/gpl.html
 *
 * Distributed WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied.
 *
 */

package org.brandao.brutos.logger;

import java.util.logging.Level;

/**
 *
 * @author Afonso Brandao
 */
public class JavaLogger implements Logger{
    
    private java.util.logging.Logger logger;
    
    public JavaLogger( java.util.logging.Logger logger ) {
        this.logger = logger;
    }

    public void info(String message) {
        logger.info( message );
    }

    public void info(String message, Throwable t) {
        logger.info( message );
    }

    public void debug(String message) {
        logger.config( message );
    }

    public void debug(String message, Throwable t) {
        logger.config( message );
    }

    public void error(String message) {
        logger.severe( message );
    }

    public void error(String message, Throwable t) {
        logger.severe( message );
    }

    public void fatal(String message) {
        logger.severe( message );
    }

    public void fatal(String message, Throwable t) {
        logger.severe( message );
    }

    public void warn(String message) {
        logger.warning( message );
    }

    public void warn(String message, Throwable t) {
        logger.warning( message );
    }

    public boolean isDebugEnabled() {
        return logger.isLoggable( Level.CONFIG );
    }

    public boolean isInfoEnabled() {
        return logger.isLoggable( Level.INFO );
    }

}
