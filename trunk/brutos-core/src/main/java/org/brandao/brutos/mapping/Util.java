/*
 * Brutos Web MVC http://www.brutosframework.com.br/
 * Copyright (C) 2009-2012 Afonso Brandao. (afonso.rbn@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.brandao.brutos.mapping;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Afonso Brandao
 */
final class Util {
    /*
    public static SimpleDateFormat sdf = new SimpleDateFormat( "dd/MM/yyy" );
    
    private Util() {
    }
    
    public static <T> T toValueSafe( String value, Class<T> type ) throws ParseException{
        try{
            Object val = toValue( value, type );
        
            if( val == null )
                return (T)safeValue( type );
            else
                return (T)val;
            
        }
        catch( Exception e ){
            return (T)safeValue( type );
        }
    }
    
    public static Object toValue( String value, Class<?> type ) throws ParseException{
        try{
            if( type == Boolean.TYPE ){
                return Boolean.parseBoolean( value );
            }
            else
            if( type == Byte.TYPE ){
                return Byte.parseByte( value );
            }
            else
            if( type == Character.TYPE ){
                return value.length() != 0? value.charAt( 0 ) : null;
            }
            else
            if( type == Double.TYPE ){
                return Double.parseDouble( value );
            }
            else
            if( type == Float.TYPE ){
                return Float.parseFloat( value );
            }
            else
            if( type == Integer.TYPE ){
                return Integer.parseInt( value );
            }
            else
            if( type == Long.TYPE ){
                return Long.parseLong( value );
            }
            else
            if( type == Short.TYPE ){
                return Short.parseShort( value );
            }
            else
            if( type == Date.class ){
                return sdf.parse( value );
            }
            else
                return value;
        }
        catch( Exception e ){
            return null;
        }

    }
    
    private static Object safeValue( Class<?> type ) throws ParseException{
        
        if( type == Boolean.TYPE ){
            return false;
        }
        else
        if( type == Byte.TYPE ){
            return (byte)0;
        }
        else
        if( type == Double.TYPE ){
            return 0.0;
        }
        else
        if( type == Float.TYPE ){
            return 0.0f;
        }
        else
        if( type == Integer.TYPE ){
            return 0;
        }
        else
        if( type == Long.TYPE ){
            return 0;
        }
        else
        if( type == Short.TYPE ){
            return 0;
        }
        else
            return null;

    }
    */
}
