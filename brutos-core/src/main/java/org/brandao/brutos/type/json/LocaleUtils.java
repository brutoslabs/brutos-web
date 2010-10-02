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

package org.brandao.brutos.type.json;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 *
 * @author Afonso Brandao
 */
class LocaleUtils {

    private static Map<String, Locale> locales;

    static{
        locales = new HashMap<String,Locale>();
        
        for( Locale locale: Locale.getAvailableLocales() ){
            String key =
                String.format(
                                "%s-%s",
                                locale.getLanguage(),
                                locale.getCountry() ).toUpperCase();
            
            locales.put( key, locale );
        }
    }

    public static Locale getLocale( String key ){
        return key == null? null : locales.get( key.toUpperCase() );
    }
    
    public static String getKey( Locale locale ){
        return locale == null?
                    null :
                    String.format(
                                    "%s-%s",
                                    locale.getLanguage(),
                                    locale.getCountry() );
    }

}
