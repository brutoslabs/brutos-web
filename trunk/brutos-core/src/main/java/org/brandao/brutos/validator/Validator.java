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

import java.util.Properties;

/**
 * Validation of a value.
 * 
 * @author Afonso Brandao
 */
public interface Validator {

    /**
     * Configure the validator.
     * @param config Configuration.
     */
    public void configure( Properties config );

    /**
     * Checks if the value is valid.
     * @param source Source value.
     * @param value Value to be checked.
     */
    public void validate( Object source, Object value );

}
