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

package org.brandao.brutos.validator;

import java.util.LinkedList;
import java.util.List;
import org.brandao.brutos.BrutosException;

/**
 * 
 * @author Afonso Brandao
 */
public class ValidatorException extends BrutosException{

    public List exceptions = new LinkedList();

    public ValidatorException() {
	super();
    }

    public ValidatorException(String message) {
	super(message);
    }

    public ValidatorException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidatorException(Throwable cause) {
        super(cause);
    }

    public void addCause(ValidatorException vex){
        exceptions.add(vex);
    }

    public List getCauses(){
        return this.exceptions;
    }

}
