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

package org.brandao.brutos.type;

import org.brandao.brutos.EnumerationType;

/**
 * Allows the creation enum types.
 *
 * @author Afonso Brandao
 */
public interface EnumType extends Type{

    /**
     * Get enumeration type.
     * @return Enumeration type.
     */
    public EnumerationType getEnumType();

    /**
     * Set enumeration type.
     * @param type Enumeration type.
     */
    public void setEnumType(EnumerationType type);
    
    /**
     * Set the class type.
     * @param classType Class type.
     */
    public void setClassType( Class classType );
    
}
