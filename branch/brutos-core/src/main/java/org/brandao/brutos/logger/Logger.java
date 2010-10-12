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

/**
 *
 * @author Afonso Brandao
 */
public interface Logger {

    public boolean isDebugEnabled();

    public boolean isInfoEnabled();

    public void info( String message );
    
    public void info( String message, Throwable t );

    public void debug( String message );

    public void debug( String message, Throwable t );

    public void error( String message );
    
    public void error( String message, Throwable t );

    public void fatal( String message );
    
    public void fatal( String message, Throwable t );

    public void warn( String message );
    
    public void warn( String message, Throwable t );
    
}
