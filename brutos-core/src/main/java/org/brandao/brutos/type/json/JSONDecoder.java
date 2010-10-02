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

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.net.URL;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * Decodes a JSON object.<br>
 * If not informed "class", the method 
 * decode(Class&lt;T&gt; type) should be used.<br>
 * 
 * Ex1:<br>
 * <b>Class:</b><br>
 * <pre>
 * public class MyObject implements Serializable{
 *   private int id;
 *   private String name;
 *
 *   ...
 *
 * }
 * </pre>
 *
 * <b>Example code:</b><br>
 * <pre>
 * ...
 * JSONDecoder jde = new JSONDecoder( "{ "id": 1, "name" : "Jose", "class" : "MyObject" }" );
 * MyObject obj = (MyObject)jde.decode();
 * </pre>
 *
 * Ex2:<br>
 * <pre>
 * ...
 * JSONDecoder jde = new JSONDecoder( "{ "id": 1, "name" : "Jose" }" );
 * MyObject obj = jde.decode( MyObject.class );
 * </pre>
 *
 * <h4>Summary of decoding rules for json type into java type</h4>
 *
 * <table border="1" cellpadding="1" cellspacing="0">
 *   <tr>
 *     <td bgcolor="#CCCCFF">Json Type</td>
 *     <td bgcolor="#CCCCFF">Java Type</td>
 *   </tr>
 *   <tr>
 *     <td rowspan="2">Object</td>
 *     <td>java.util.Map</td>
 *   </tr>
 *   <tr>
 *     <td>Object</td>
 *   </tr>
 *   <tr>
 *     <td rowspan="3">Array</td>
 *     <td>Object[]</td>
 *   </tr>
 *   <tr>
 *     <td>java.util.Collection</td>
 *   </tr>
 *   <tr>
 *     <td>int[], long[], double[], float[], short[], boolean[], char[],
 *       byte[]</td>
 *   </tr>
 *   <tr>
 *     <td rowspan="7">String</td>
 *     <td>char</td>
 *   </tr>
 *   <tr>
 *     <td>java.lang.CharSequence</td>
 *   </tr>
 *   <tr>
 *     <td>java.lang.String</td>
 *   </tr>
 *   <tr>
 *     <td>java.net.URL</td>
 *   </tr>
 *   <tr>
 *     <td>java.net.URI</td>
 *   </tr>
 *   <tr>
 *     <td>java.lang.Character</td>
 *   </tr>
 *   <tr>
 *     <td>java.lang.Class</td>
 *   </tr>
  *   <tr>
 *     <td>String (language-country)</td>
 *     <td>java.util.Locale</td>
 *   </tr>
*   <tr>
 *     <td rowspan="4">number</td>
 *     <td>java.lang.Number</td>
 *   </tr>
 *   <tr>
 *     <td>int, long, double, float, short, byte</td>
 *   </tr>
 *   <tr>
 *     <td>java.math.BigDecimal</td>
 *   </tr>
 *   <tr>
 *     <td>java.math.BigInteger</td>
 *   </tr>
 *   <tr>
 *     <td rowspan="4">number (milliseconds since 1970)</td>
 *     <td>java.util.Date</td>
 *   </tr>
 *   <tr>
 *     <td>java.sql.Time</td>
 *   </tr>
 *   <tr>
 *     <td>java.sql.Timestamp</td>
 *   </tr>
 *   <tr>
 *     <td>java.util.Calendar</td>
 *   </tr>
 *   <tr>
 *     <td rowspan="2">true/false</td>
 *     <td>boolean</td>
 *   </tr>
 *   <tr>
 *     <td>java.lang.Boolean</td>
 *   </tr>
 *   <tr>
 *     <td>null</td>
 *     <td>null</td>
 *   </tr>
 * </table>
 *
 * @author Bogomil, Afonso Brandao
 * 
 * @version 1.0
 */
public class JSONDecoder implements JSONConstants{

    private InputStream in;

    /**
     * Creates a default decoder.
     * @param value JSON object.
     */
    public JSONDecoder( String value ){
        this( new ByteArrayInputStream( value.getBytes() ) );
    }

    /**
     * Create a new decoder.
     * @param in Input stream
     */
    public JSONDecoder( InputStream in ){
        this.in = in;

        if( in == null )
            throw new NullPointerException( "input stream" );

    }

    /**
     * Decodes a JSON object.
     * @return Object.
     * @throws JSONException Thrown if a problem occurs when decoding.
     */
    public Object decode() throws JSONException{
        return decode( null );
    }

    /**
     * Decodes a JSON object in a specific type.
     * @param <T> 
     * @param type Class type.
     * @return Object.
     * @throws JSONException Thrown if a problem occurs when decoding.
     */
    public <T> T decode( Class<T> type ) throws JSONException{
        try{
            JSONParsing parsing = new JSONParsing( in );
            return (T) decoder0( parsing.object( type ), type );
        }
        catch( JSONException e ){
            throw e;
        }
        catch( Exception e ){
            throw new JSONException( e );
        }
    }

    /**
     * Decodes a JSON object in a specific type.
     * @param type Class type.
     * @return Object.
     * @throws JSONException Thrown if a problem occurs when decoding.
     */
    public Object decode( Type type ) throws JSONException{
        try{
            Class clazz = getClass( type );
            JSONParsing parsing = new JSONParsing( in );
            return decoder0( parsing.object( clazz ), type );
        }
        catch( JSONException e ){
            throw e;
        }
        catch( Exception e ){
            throw new JSONException( e );
        }
    }


    private Object decoder0( Object data, Type clazz ) throws Exception{
        Class clazzType = getClass( clazz );

        if( data == null )
            return null;
        else
        if( data instanceof List ){
            return array( (List<Object>)data, clazz );
        }
        else
        if( clazz != null ){
            if( Date.class.isAssignableFrom( clazzType ) )
                return objectDate( data, clazzType );
            else
            if( Calendar.class.isAssignableFrom( clazzType ) )
                return objectDate( data, clazzType );
            else
            if( clazzType != Object.class && clazzType.isAssignableFrom( Map.class ) )
                return getMap( (Map)data, clazz );
            else
            if( clazzType.isEnum() )
                return objectEnum( data, clazzType );
        }

        return getObject( data, clazz );

    }

    private Object objectEnum( Object data, Class clazz ){
        int index = (Integer)toValue( (String)data, Integer.class );
        return clazz.getEnumConstants()[index];
    }

    private Object objectDate( Object data, Class clazz ){
        if( Date.class.isAssignableFrom( clazz ) ){
            Date dta = (Date) getInstance( clazz );
            dta.setTime( (Long)toValue( (String)data, Long.class ) );
            return dta;
        }
        else
        if( Calendar.class.isAssignableFrom( clazz ) ){
            long time = (Long)toValue( (String)data, Long.class );
            Calendar dta = (Calendar) getInstance( clazz );
            dta.setTime( new Date( time ) );
            return dta;
        }
        else
            throw new JSONException( "invalid date" );
    }

    private Object array( List<Object> data, Type type ) throws Exception{
        
        Class classType = getClass( type );
        Class collectionType = getCollectionType( type );
        
        if( classType == null )
            return data;
        else
        if( Collection.class.isAssignableFrom( classType ) ){
            Collection<Object> set = (Collection) getInstance( classType );
            for( Object dta: data )
                set.add( decoder0( dta, collectionType ) );

            return set;
        }
        else
        if( classType.isArray() ){
            Object array = Array.newInstance(classType.getComponentType(), data.size() );
            for( int i=0;i<data.size();i++ )
                Array.set(array, i, decoder0( data.get(i), classType.getComponentType() ));

            return array;
        }
        else
            return null;
    }

    private Object getMap( Map map, Type type ) throws Exception{
        Class classType = getClass( type );
        Type[] types = getMapType( type );
        //Type keyTpye = types[0];
        Type valueType = types[1];

        Set<Object> keys = map.keySet();
        Map<String,Object> values = (Map)getInstance( classType );
        for( Object k: keys ){
            Object key = decoder0( k, String.class );
            Object value = decoder0( map.get( k ), valueType );
            values.put( (String)key , value);
        }

        return values;
    }

    private Object getObject( Object data, Type clazz ) throws Exception{

        if( !(data instanceof Map) )
            return toValue( String.valueOf( data ), clazz );
        else{
            Map values = (Map)data;
            Class classType = getClass( (Map<String,Object>)values, clazz );
            MappingBean mb = MappingBean.getMapping( getClass( classType ) );
            Object instance = getInstance( classType );

            Map<String,MethodMapping> attributes = mb.getMethods();
            Set<String> keys = attributes.keySet();
            for( String key: keys ){
                if( !"class".equals( key ) ){
                    MethodMapping method = attributes.get( key );
                    Object value = decoder0( values.get( key ), method.getType() );
                    if( value != null )
                        method.setValue( value, instance );
                }
            }
            return instance;
            
        }
    }

    private Class getClass( Map<String,Object> mappedObj, Type classType ) throws ClassNotFoundException{
        if( classType == null ){
            String clazzName = (String) mappedObj.get( "class" );
            if( clazzName == null )
                throw new JSONException( "undetermined class: not found \"class\"" );
            else
                return Class.forName( clazzName, true,
                        Thread.currentThread().getContextClassLoader() );
        }
        else
            return getClass( classType );
    }

    private Object toValue( String value, Type type ){
        try{
            Class wrapper = ClassType.getWrapper( (Class)type );
            if( value == null )
                return null;
            else
            if( type == null || CharSequence.class.isAssignableFrom(wrapper) || type == Object.class )
                return value;
            else
            if( BigDecimal.class.isAssignableFrom(wrapper) )
                return new BigDecimal( value );
            else
            if( BigInteger.class.isAssignableFrom(wrapper) )
                return new BigInteger( value, 10 );
            else
            if( URI.class.isAssignableFrom(wrapper) )
                return new URI( value );
            else
            if( URL.class.isAssignableFrom(wrapper) )
                return new URL( value );
            else
            if( Locale.class.isAssignableFrom(wrapper) )
                return LocaleUtils.getLocale(value);
            else
            if( type == Class.class )
                return ClassType.get(value);
            else
            if( Character.class == wrapper )
                return value.length() == 0? null : value.charAt(0);
            else
                return wrapper
                    .getMethod( "valueOf" , String.class )
                        .invoke( type , value);
        }
        catch( Exception e ){
            throw new JSONException( e );
        }
    }

    private static Class getClass( java.lang.reflect.Type type ){
        if( type instanceof ParameterizedType )
            return (Class)((ParameterizedType)type).getRawType();
        else
            return (Class)type;
    }

    private static Class getCollectionType( java.lang.reflect.Type type ){
        if( type instanceof ParameterizedType )
            return (Class)((ParameterizedType)type).getActualTypeArguments()[0];
        else
            return Object.class;
    }

    private static Type[] getMapType( java.lang.reflect.Type type ){
        if( type instanceof ParameterizedType )
            return ((ParameterizedType)type).getActualTypeArguments();
        else
            return new Class[]{String.class, Object.class};
    }

    public <T> T getInstance( Class<T> type ){
        try{
            if( type == Map.class )
                return (T) new HashMap();
            else
            if( type == Set.class )
                return (T) new HashSet();
            else
            if( type == List.class )
                return (T) new LinkedList();
            else
            if( type == Time.class )
                return (T) new Time( (new Date()).getTime() );
            else
            if( type == Timestamp.class )
                return (T) new Timestamp( (new Date()).getTime() );
            else
            if( type == Calendar.class )
                return (T) new GregorianCalendar();
            else
                return type.newInstance();
        }
        catch( Throwable e ){
            throw new JSONException( e );
        }
    }

}
