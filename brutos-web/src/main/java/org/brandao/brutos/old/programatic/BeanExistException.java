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

package org.brandao.brutos.old.programatic;

import org.brandao.brutos.BrutosException;

/**
 *
 * @author Afonso Brandao
 */
public class BeanExistException extends BrutosException{
    
    public BeanExistException() {
	super();
    }

    public BeanExistException(String message) {
	super(message);
    }

    public BeanExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public BeanExistException(Throwable cause) {
        super(cause);
    }
}