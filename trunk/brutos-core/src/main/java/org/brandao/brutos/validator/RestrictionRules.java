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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author Afonso Brandao
 */
public class RestrictionRules {

    public static final RestrictionRules MIN         = new RestrictionRules( "min" );
    public static final RestrictionRules MIN_LENGTH  = new RestrictionRules( "min_length" );
    public static final RestrictionRules MAX         = new RestrictionRules( "max" );
    public static final RestrictionRules MAX_LENGTH  = new RestrictionRules( "max_length" );
    public static final RestrictionRules MATCHES     = new RestrictionRules( "matches" );
    public static final RestrictionRules REQUIRED    = new RestrictionRules( "required" );
    public static final RestrictionRules EQUAL       = new RestrictionRules( "equal" );

    private final static Map defaultRules = new HashMap();

    static{
        defaultRules.put( RestrictionRules.MIN.toString(),        RestrictionRules.MIN );
        defaultRules.put( RestrictionRules.MIN_LENGTH.toString(), RestrictionRules.MIN_LENGTH );
        defaultRules.put( RestrictionRules.MAX.toString(),        RestrictionRules.MAX );
        defaultRules.put( RestrictionRules.MAX_LENGTH.toString(), RestrictionRules.MAX_LENGTH );
        defaultRules.put( RestrictionRules.MATCHES.toString(),    RestrictionRules.MATCHES );
        defaultRules.put( RestrictionRules.REQUIRED.toString(),   RestrictionRules.REQUIRED );
        defaultRules.put( RestrictionRules.EQUAL.toString(),      RestrictionRules.EQUAL );
    }

    private String name;

    public RestrictionRules( String name ){
        this.name = name;
    }

    public static List getRestrictionRules(){
        return new ArrayList(defaultRules.values());
    }

    public String toString(){
        return this.name;
    }

    public static RestrictionRules valueOf( String value ){
        if( defaultRules.containsKey(value) )
            return (RestrictionRules)defaultRules.get( value );
        else
            return new RestrictionRules( value );
    }


}
