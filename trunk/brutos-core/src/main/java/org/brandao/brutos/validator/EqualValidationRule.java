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
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.mapping.DependencyBean;
import org.brandao.brutos.mapping.UseBeanData;
import org.brandao.brutos.type.Type;

/**
 *
 * @author Brandao
 */
public class EqualValidationRule implements ValidationRule{

    public void validate(Properties config, Object source, Object value) {
        Type valueType = null;

        if( source instanceof DependencyBean )
            valueType = ((DependencyBean)source).getType();
        else
        if( source instanceof UseBeanData )
            valueType = ((UseBeanData)source).getType();
        else
            throw new BrutosException( "invalid source: " + source );

        Object tmp = valueType
                        .getValue(
                        config.get(RestrictionRules.EQUAL.toString()));

        if( tmp != null && !tmp.equals( value ) )
            throw new ValidatorException();
    }

}
