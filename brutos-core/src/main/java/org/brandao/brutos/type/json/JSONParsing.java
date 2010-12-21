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

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Afonso Brandao
 */
class JSONParsing implements JSONConstants{

    private InputStream in;
    private StringBuffer buffer;
    int read = 0;
    
    public JSONParsing( InputStream in ) throws JSONException{
        try{
            this.in = in;
            this.buffer = new StringBuffer();
            this.read();
        }
        catch( Exception e ){
            throw new JSONException( e );
        }
    }

    private void read() throws IOException{
        int l;
        byte[] buf = new byte[2048];

        while( ( l = in.read(buf) ) != -1 )
            buffer.append( new String( buf, 0, l ) );
    }

    public Object object() throws IOException{
        return object( null );
    }

    public Object object( Type clazz ) throws IOException{

        Character v = next( true );back();

        if( v == null )
            return null;
        
        switch (v){
            case START_OBJECT_STR:
                return object0( clazz );
            case START_ARRAY_STR:
                return array( clazz );
            default:
                return value( clazz );
        }
        
    }

    Object object0( Type clazz ) throws IOException{
        Map<String,Object> obj = new HashMap<String,Object>();

        if( START_OBJECT_STR == next(true) ){
                Object[] value = atributo( clazz );
                obj.put( (String)value[0] , value[1]);

                while( SEPARATOR_STR == next(true) ){
                    value = atributo(clazz);
                    obj.put( (String)value[0] , value[1]);
                }

                back();

                if( END_OBJECT_STR == next(true) ){
                    return obj;
                }
                else
                    throwJSON( "expected at position $lin : \"}\"", null );return null;
                    
                
        }
        else
            throwJSON( "expected at position $lin : \"{\"", null );return null;
    }

    /*
    private Object createObject( Type clazz, Map<String,Object> values ){
        try{
            if( clazz == null ){
                String clazzName = (String) values.get( "class" );
                if( clazzName == null )
                    throw new JSONException( "undetermined class: not found \"class\"" );
                else
                    clazz = Class.forName( clazzName, true,
                            Thread.currentThread().getContextClassLoader() );
            }

            MappingBean mb = MappingBean.getMapping( getClass( clazz ) );
            Object instance = factoryBean.getInstance( getClass( clazz ) );
            if( instance instanceof Map )
                return values;
            else{
                Map<String,MethodMapping> attributes = mb.getGetters();
                Set<String> keys = attributes.keySet();
                for( String key: keys ){
                    if( !"class".equals( key ) ){
                        MethodMapping method = attributes.get( key );
                        Object value =
                            toObject( values.get( key ), method.getType() );
                        if( value != null )
                            method.setValue( value, instance );
                    }
                }
                return instance;
            }
        }
        catch( JSONException e ){
            throw e;
        }
        catch( Exception e ){
            throw new JSONException( e );
        }
    }
    */

    /*
    private Object toObject( Object value, Type type ){
        try{
            if( value == null )
                return null;
            else
            if( value instanceof List ){
                Class classType;
                if( type instanceof ParameterizedType )
                    classType = (Class)((ParameterizedType)type).getRawType();
                else
                    classType = (Class)type;

                if( classType.isArray() )
            }
            else
                return ClassType.getWrapper( (Class)type )
                    .getMethod( "valueOf" , String.class )
                        .invoke( type , value);
        }
        catch( Exception e ){
            throw new JSONException( e );
        }
    }
    */
    private Object[] atributo( Type clazz ) throws IOException{
        String id = string();
        Object value;
        if( EQUALS_STR == next(true) ){
            value = value( clazz );
            return new Object[]{ id, value };
        }
        else
            throwJSON( "expected at position $lin : \":\"", null );return null;
    }
    
    Object value( Type clazz ) throws IOException{
        char inicio = next(true);back();

        if( QUOTE_STR == inicio )
            return string();
        else
        if( ( inicio >= '0' && inicio <= '9' ) || inicio == '-' )
            return number( clazz );
        else
        if( START_OBJECT_STR == inicio )
            return object0( clazz );
        else
        if( START_ARRAY_STR == inicio )
            return array( clazz );
        else
        if( inicio == 't' || inicio == 'f' )
            return bool();
        else
        if( inicio == 'n' )
            return null0();
        else
            throwJSON( "expected at position $lin : String, Number, Object, Array, null, true or false", null );return null;
    }

    Object number( Type clazz ) throws IOException{
        char value = next(true);
        StringBuffer result = new StringBuffer();

        if( value == '-' ){
            result.append( '-' );
            value = next(false);
        }

        if( ( value >= '1' && value <= '9' ) ){
            result.append( value );
            digits( '0', '9', result, 0 );
            value = next(false);
        }
        else
        if( value == '0' ){
            result.append( value );
            value = next(false);
        }
        else{
            throwJSON( "expected at position $lin : 0-9", null );return null;
        }

        if( value == '.' ){
            result.append( value );
            digits( '0', '9', result, 1 );
            value = next(false);
        }
        
        if( value == 'e' || value == 'E' ){
            result.append( value );
            value = next(false);

            if( value == '+' || value == '-' ){
                result.append( value );
                value = next(false);
            }
            else
                back();

            digits( '0', '9', result, 1 );
        }
        else
            back();

        /*
        if( clazz == Double.class || clazz == Double.TYPE )
            return Double.parseDouble( result.toString() );
        else
        if( clazz == Integer.class || clazz == Integer.TYPE )
            return Integer.parseInt( result.toString() );
        else
        if( clazz == Float.class || clazz == Float.TYPE )
            return Float.parseFloat( result.toString() );
        else
        if( clazz == Long.class || clazz == Long.TYPE )
            return Long.parseLong( result.toString() );
        else
        if( clazz == Short.class || clazz == Short.TYPE )
            return Short.parseShort( result.toString() );
        else
            return Double.parseDouble( result.toString() );
        */
        return result.toString();
    }

    void digits( char min, char max, StringBuffer number, int minLength ) throws IOException{
        int oldLength = number.length();

        Character value = next(false);
        while( value != null && value >= min && value <= max ){
            number.append( value );
            value = next(false);
        }
        back();

        if( ( number.length() - oldLength ) < minLength )
            throwJSON( "expected at position $lin : 0-9", null );
    }

    void hexDigits(  StringBuffer number, int minLength ) throws IOException{
        int oldLength = number.length();

        Character value = Character.toLowerCase( next(false) );
        while( value != null && 
               HEX.indexOf( String.valueOf( value ) ) != -1 &&
               ( number.length() - oldLength ) < minLength ){
            number.append( value );
            value = Character.toLowerCase( next(false) );
        }
        back();

        
        if( ( number.length() - oldLength ) < minLength )
            throwJSON( "expected at position $lin : 0-f", null );
        
    }

    Object bool() throws IOException{
        Character value = Character.toLowerCase( next(true) );
        StringBuffer result = new StringBuffer();

        if( value == 't' || value == 'f' ){
            result.append( value );
            value = next(false);
            while( value != null &&
                   ( TRUE.indexOf( String.valueOf( value ) ) != -1 ||
                   FALSE.indexOf( String.valueOf( value ) ) != -1 ) ){
                result.append( value );
                value = next(false);
            }
        }

        back();
        
        if( TRUE.indexOf( String.valueOf( result ) ) != -1 )
            return TRUE;
        else
        if( FALSE.indexOf( String.valueOf( result ) ) != -1 )
            return FALSE;
        else
            throwJSON( "expected at position $lin : true or false", null );return null;

    }
    
    Object null0() throws IOException{
        Character value = next(true);
        StringBuffer result = new StringBuffer();

        if( value == 'n' ){
            result.append( value );
            value = next(false);
            while( value != null && NULL.indexOf( String.valueOf( value ) ) != -1 ){
                result.append( value );
                value = next(false);
            }
        }

        back();
        
        if( NULL.indexOf( String.valueOf( result ) ) != -1 )
            return null;
        else
            throwJSON( "expected at position $lin : null", null );return null;
    }

    private Object array( Type clazz ) throws IOException{
        List<Object> obj = new ArrayList<Object>();

        if( START_ARRAY_STR == next(true) ){
                Object value = value( clazz );
                obj.add( value );
                while( SEPARATOR_STR == next(true) ){
                    value = value( clazz );
                    obj.add( value );
                }

                back();
                
                if( END_ARRAY_STR == next(true) )
                    return obj;
                else
                    throwJSON( "expected at position $lin : \"]\"", null );return null;


        }
        else
            throwJSON( "expected at position $lin : \"[\"", null );return null;

    }

    String string() throws IOException{

        Character v = next(true);
        StringBuffer result = new StringBuffer();
        if( QUOTE_STR == v ){
            v = next(false);
            //bug '\uffff'
            while( v != null && v != QUOTE_STR ){
                
                if( v == '\\' ){
                    v = next(false);

                    switch( v ){
                        case 'u':
                            StringBuffer hex = new StringBuffer();
                            hexDigits(hex, 4);
                            result.append( (char)Integer.parseInt(hex.toString(), 16 ) );
                            break;
                        case '\"':
                        case '\\':
                        case '/':
                            result.append( v );
                            break;
                        case 'b':
                            result.append( '\b' );
                            break;
                        case 't':
                            result.append( '\t' );
                            break;
                        case 'n':
                            result.append( '\n' );
                            break;
                        case 'f':
                            result.append( '\f' );
                            break;
                        case 'r':
                            result.append('\r');
                            break;
                        default:
                            throwJSON( "expected at position " +
                            "$lin : \\u, \\b, \\t, \\n, \\f or \\r", null );return null;
                    }
                }
                else
                    result.append( v );
                
                v = next(false);
            }

            if( v == null ){
                throwJSON( "expected at position $lin : \"", null );return null;
            }
            return result.toString();
        }
        else
            throwJSON( "expected at position $lin : \"", null );return null;
    }

    private void throwJSON( String msg, Throwable t ){
        throw new JSONException( msg.replace( "$lin", String.valueOf( read ) ), t );
    }

    private Character next( boolean sp ) throws IOException{
        
        if( read >= buffer.length() )
            return null;
            //throwJSON( "unexpected end", null );
        
        char value = buffer.charAt( read );
        read++;

        while( sp && value == ' ' && read < buffer.length() ){
            value = buffer.charAt( read );
            read++;
        }

        return value;
    }

    private void back(){
        read = read > 0? read-1 : 0;
    }

}
