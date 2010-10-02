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

package org.brandao.brutos;

import java.util.HashMap;
import java.util.Map;

/**
 *
 *  @author Afonso Brandao
 */
public class EnumerationType {
    
    public static final EnumerationType ORDINAL = new EnumerationType( "ordinal" );
    public static final EnumerationType STRING = new EnumerationType( "string" );
    private static Map defaultTypes = new HashMap();

    static{
        defaultTypes.put( ORDINAL.toString(), ORDINAL );
        defaultTypes.put( STRING.toString(), STRING );
    }

    private String name;

    public EnumerationType( String name ){
        this.name = name;
    }

    public String toString(){
        return name;
    }

    public static EnumerationType valueOf( String value ){
        return (EnumerationType)defaultTypes.get(value);
    }

}
