/*
 * Brutos Web MVC http://brutos.sourceforge.net/
 * Copyright (C) 2009 Afonso Brandão. (afonso.rbn@gmail.com)
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


package org.brandao.brutos.type.json.jsondecoder;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import junit.framework.Test;
import junit.framework.TestCase;
import org.brandao.brutos.type.json.JSONDecoder;

/**
 *
 * @author Afonso Brandao
 */
public class JSONDecoderListTest extends TestCase implements Test{

    public static class MyType implements ParameterizedType{

        private Type rawType;
        private Type typeArguments;

        public MyType( Class rawType, Class typeArguments ){
            this.rawType = rawType;
            this.typeArguments = typeArguments;
        }

        public Type[] getActualTypeArguments() {
            return new Type[]{ typeArguments };
        }

        public Type getRawType() {
            return rawType;
        }

        public Type getOwnerType() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

    }

    public static class MyObject implements Serializable{
        private Long a;
        private byte b;

        public MyObject(){
            this( 0l, (byte)0 );
        }

        public MyObject( Long a, byte b ){
            setA( a );
            setB( b );
        }

        public Long getA() {
            return a;
        }

        public void setA(Long a) {
            this.a = a;
        }

        public byte getB() {
            return b;
        }

        public void setB(byte b) {
            this.b = b;
        }
    }

    public JSONDecoderListTest(){
        super();
    }

    public void testMap() throws IOException{
        Map<String,Object> map0 = new HashMap<String,Object>();
        map0.put("key1", 10 );
        map0.put("key2", "Text" );

        Map<String,Object> map1 = new HashMap<String,Object>();
        map1.put("key3", -1.3 );
        map1.put("key4", 1f );

        Map<String,Object> map2 = new HashMap<String,Object>();
        map2.put("key5", "Text2" );
        map2.put("key6", "10000" );

        List<Map<String,Object>> maps = Arrays.asList( map0, map1, map2, null );

        String map = "[ ";
        String tmpMap = "";

        for( String key: map0.keySet() ){
            String t = String.format( "\"%s\" : %s" ,
                            key, 
                            map0.get( key ) instanceof String?
                                String.valueOf( "\""+map0.get( key )+"\"" ) :
                                String.valueOf( map0.get( key ) ) );

            tmpMap += tmpMap.length() == 0? t : ", " + t;
        }

        map += String.format( "{ %s }, " , tmpMap );

        tmpMap = "";
        for( String key: map1.keySet() ){
            String t = String.format( "\"%s\" : %s" ,
                            key, 
                            map1.get( key ) instanceof String?
                                String.valueOf( "\""+map1.get( key )+"\"" ) :
                                String.valueOf( map1.get( key ) ) );

            tmpMap += tmpMap.length() == 0? t : ", " + t;
        }

        map += String.format( "{ %s }, " , tmpMap );

        tmpMap = "";
        for( String key: map2.keySet() ){
            String t = String.format( "\"%s\" : %s" ,
                            key, 
                            map2.get( key ) instanceof String?
                                String.valueOf( "\""+map2.get( key )+"\"" ) :
                                String.valueOf( map2.get( key ) ) );

            tmpMap += tmpMap.length() == 0? t : ", " + t;
        }

        map += String.format( "{ %s }" , tmpMap );

        map += ", null ]";

        JSONDecoder jse = new JSONDecoder( map );
        
        List<Map> result = (List<Map>) jse.decode( new MyType( List.class, Map.class ) );

        assertNotNull(result.get(0));
        assertEquals( "10", result.get(0).get( "key1" ) );
        assertEquals( "Text", result.get(0).get( "key2" ) );

        assertNotNull(result.get(1));
        assertEquals( "-1.3", result.get(1).get( "key3" ) );
        assertEquals( "1.0", result.get(1).get( "key4" ) );

        assertNotNull(result.get(2));
        assertEquals( "Text2", result.get(2).get( "key5" ) );
        assertEquals( "10000", result.get(2).get( "key6" ) );

        assertNull(result.get(3));

    }

    public void testObject() throws IOException{

        String ex = String.format( 
                        "[ null, %s, %s, %s ]",
                        "{ \"b\" : 25, \"a\" : 10 }",
                        "{ \"b\" : -25, \"a\" : 20 }",
                        "{ \"b\" : 127, \"a\" : 1 }" );

        JSONDecoder jse = new JSONDecoder( ex );
        
        List<MyObject> result = (List<MyObject>) jse.decode( new MyType( List.class, MyObject.class ) );

        assertNull( result.get(0) );

        assertNotNull( result.get(1) );
        assertEquals( Long.valueOf( 10l ), Long.valueOf( result.get(1).getA() ) );
        assertEquals( Byte.valueOf( (byte)25 ), Byte.valueOf( result.get(1).getB() ) );

        assertNotNull( result.get(2) );
        assertEquals( Long.valueOf( 20l ), Long.valueOf( result.get(2).getA() ) );
        assertEquals( Byte.valueOf( (byte)-25 ), Byte.valueOf( result.get(2).getB() ) );

        assertNotNull( result.get(3) );
        assertEquals( Long.valueOf( 1l ), Long.valueOf( result.get(3).getA() ) );
        assertEquals( Byte.valueOf( (byte)127 ), Byte.valueOf( result.get(3).getB() ) );
    }

    public void testInt() throws IOException{
        JSONDecoder jse = new JSONDecoder( "[ 0, 100, 200, 150 ]" );
        List<Integer> array = (List<Integer>) jse.decode( new MyType( List.class, int.class )  );
        assertNotNull( array );
        assertEquals( 0, (int)array.get(0) );
        assertEquals( 100, (int)array.get(1) );
        assertEquals( 200, (int)array.get(2) );
        assertEquals( 150, (int)array.get(3) );
    }

    public void testLong() throws IOException{
        JSONDecoder jse = new JSONDecoder( "[ 0, 100, 200, 150 ]" );
        List<Long> array = (List<Long>) jse.decode( new MyType( List.class, long.class ) );
        assertNotNull( array );
        assertEquals( 0, (long)array.get(0) );
        assertEquals( 100, (long)array.get(1) );
        assertEquals( 200, (long)array.get(2) );
        assertEquals( 150, (long)array.get(3) );
    }

    public void testDouble() throws IOException{
        JSONDecoder jse = new JSONDecoder( "[ 0.0, 15.2, 20.2, 1.01 ]" );
        List<Double> array = (List<Double>) jse.decode( new MyType( List.class, double.class ) );
        assertNotNull( array );
        assertEquals( 0.0, array.get(0) );
        assertEquals( 15.2, array.get(1) );
        assertEquals( 20.2, array.get(2) );
        assertEquals( 1.01, array.get(3) );
    }

    public void testFloat() throws IOException{
        JSONDecoder jse = new JSONDecoder( "[ 0.0, 15.2, 20.2, 1.01 ]" );
        List<Float> array = (List<Float>) jse.decode( new MyType( List.class, float.class ) );
        assertNotNull( array );
        assertEquals( 0.0f, (float)array.get(0) );
        assertEquals( 15.2f, (float)array.get(1) );
        assertEquals( 20.2f, (float)array.get(2) );
        assertEquals( 1.01f, (float)array.get(3) );
    }

    public void testShort() throws IOException{
        JSONDecoder jse = new JSONDecoder( "[ 0, 100, 200, 150 ]" );
        List<Short> array = (List<Short>) jse.decode( new MyType( List.class, short.class ) );
        assertNotNull( array );
        assertEquals( 0, (short)array.get(0) );
        assertEquals( 100, (short)array.get(1) );
        assertEquals( 200, (short)array.get(2) );
        assertEquals( 150, (short)array.get(3) );
    }

    public void testBoolean() throws IOException{
        JSONDecoder jse = new JSONDecoder( "[ true, false, false, true ]" );
        List<Boolean> array = (List<Boolean>) jse.decode( new MyType( List.class, boolean.class ) );
        assertNotNull( array );
        assertEquals( true, (boolean)array.get(0) );
        assertEquals( false, (boolean)array.get(1) );
        assertEquals( false, (boolean)array.get(2) );
        assertEquals( true, (boolean)array.get(3) );
    }

    public void testChar() throws IOException{
        JSONDecoder jse = new JSONDecoder( "[ \"A\", \"F\", \"O\", \"N\" ]" );
        List<Character> array = (List<Character>) jse.decode( new MyType( List.class, char.class ) );
        assertNotNull( array );
        assertEquals( 'A', (char)array.get(0) );
        assertEquals( 'F', (char)array.get(1) );
        assertEquals( 'O', (char)array.get(2) );
        assertEquals( 'N', (char)array.get(3) );
    }

    public void testByte() throws IOException{
        JSONDecoder jse = new JSONDecoder( "[ 0, -100, 127, 99 ]" );
        List<Byte> array = (List<Byte>) jse.decode( new MyType( List.class, byte.class ) );
        assertNotNull( array );
        assertEquals( (byte)0   , (byte)array.get(0) );
        assertEquals( (byte)-100, (byte)array.get(1) );
        assertEquals( (byte)127 , (byte)array.get(2) );
        assertEquals( (byte)99  , (byte)array.get(3) );
    }

    public void testBigDecimal() throws IOException{
        JSONDecoder jse = new JSONDecoder( "[ 0, -100, 127 ]" );
        List<BigDecimal> array = (List<BigDecimal>) jse.decode( new MyType( List.class, BigDecimal.class ) );
        assertNotNull( array );
        assertEquals( new BigDecimal(0), array.get(0) );
        assertEquals( new BigDecimal(-100), array.get(1) );
        assertEquals( new BigDecimal( 127 ), array.get(2) );
    }

    public void testBigInteger() throws IOException{
        JSONDecoder jse = new JSONDecoder( "[ 0, -100, 127 ]" );
        List<BigInteger> array = (List<BigInteger>) jse.decode( new MyType( List.class, BigInteger.class ) );
        assertNotNull( array );
        assertEquals( new BigInteger( "0" ), array.get(0) );
        assertEquals( new BigInteger( "-100", 10), array.get(1) );
        assertEquals( new BigInteger( "127", 10 ), array.get(2) );
    }

    public void testString() throws IOException{
        JSONDecoder jse = new JSONDecoder( "[ \"AA\", \"BB\", \"CC\", \"DD\" ]" );
        List<String> array = (List<String>) jse.decode( new MyType( List.class, String.class ) );
        assertNotNull( array );
        assertEquals( "AA", array.get(0) );
        assertEquals( "BB", array.get(1) );
        assertEquals( "CC", array.get(2) );
        assertEquals( "DD", array.get(3) );
    }

    public void testURL() throws IOException{
        JSONDecoder jse = new JSONDecoder( "[ " +
                "\"http\\u003a\\/\\/www\\u002emysite\\u002ecom\", " +
                "\"http\\u003a\\/\\/www\\u002emysite\\u002enet\", " +
                "\"http\\u003a\\/\\/www\\u002emysite\\u002ebr\" ]" );

        List<URL> array = (List<URL>) jse.decode( new MyType( List.class, URL.class ) );
        assertNotNull( array );
        assertEquals( new URL("http://www.mysite.com"), array.get(0) );
        assertEquals( new URL("http://www.mysite.net"), array.get(1) );
        assertEquals( new URL("http://www.mysite.br"), array.get(2) );
    }

    public void testURI() throws IOException, URISyntaxException{
        JSONDecoder jse = new JSONDecoder( "[ " +
                "\"http\\u003a\\/\\/www\\u002emysite\\u002ecom\", " +
                "\"http\\u003a\\/\\/www\\u002emysite\\u002enet\", " +
                "\"http\\u003a\\/\\/www\\u002emysite\\u002ebr\" ]" );

        List<URI> array = (List<URI>) jse.decode( new MyType( List.class, URI.class ) );
        assertNotNull( array );
        assertEquals( new URI("http://www.mysite.com"), array.get(0) );
        assertEquals( new URI("http://www.mysite.net"), array.get(1) );
        assertEquals( new URI("http://www.mysite.br"), array.get(2) );
    }

    public void testClass() throws IOException{
        JSONDecoder jsd = new JSONDecoder( "[ \"java.lang.Integer\", \"int\", \"double\", \"java.lang.Float\" ]" );
        List<Class> array = (List<Class>) jsd.decode( new MyType( List.class, Class.class ) );

        assertNotNull( array );
        assertEquals( Integer.class, array.get(0) );
        assertEquals( int.class, array.get(1) );
        assertEquals( double.class, array.get(2) );
        assertEquals( Float.class, array.get(3) );
    }

    public void testLocale() throws IOException{
        Locale[] expected = new Locale[]{ Locale.US, Locale.CANADA, Locale.FRANCE };

        JSONDecoder jsd = new JSONDecoder( String.format(
                    "[ \"%s\\u002d%s\", \"%s\\u002d%s\", \"%s\\u002d%s\" ]",
                    Locale.US.getLanguage(),
                    Locale.US.getCountry(),
                    Locale.CANADA.getLanguage(),
                    Locale.CANADA.getCountry(),
                    Locale.FRANCE.getLanguage(),
                    Locale.FRANCE.getCountry() ) );

        List<Locale> array = (List<Locale>) jsd.decode( new MyType( List.class, Locale.class ) );
        assertNotNull( array );
        assertTrue( Arrays.equals(expected, array.toArray() ) );
    }

    public void testDate() throws IOException{
        Date[] expected = new Date[]{ new Date(), new Date() };

        JSONDecoder jse = new JSONDecoder( String.format(
                    "[ %d, %d ]",
                    expected[0].getTime(),
                    expected[1].getTime()
                ) );

        List<Date> result = (List<Date>) jse.decode( new MyType( List.class, Date.class ) );

        assertNotNull( result );
        assertTrue( Arrays.equals( expected , result.toArray() ) );
    }

    public void testTime() throws IOException{
        Time[] expected = new Time[]{ new Time( (new Date()).getTime() ), new Time( (new Date()).getTime() ) };
        JSONDecoder jse = new JSONDecoder( String.format(
                    "[ %d, %d ]",
                    expected[0].getTime(),
                    expected[1].getTime()
                ) );

        List<Time> result = (List<Time>) jse.decode( new MyType( List.class, Time.class ) );

        assertNotNull( result );
        assertTrue( Arrays.equals( expected , result.toArray() ) );
    }

    public void testTimestamp() throws IOException{
        Timestamp[] expected = new Timestamp[]{ new Timestamp( (new Date()).getTime() ), new Timestamp( (new Date()).getTime() ) };
        JSONDecoder jse = new JSONDecoder( String.format(
                    "[ %d, %d ]",
                    expected[0].getTime(),
                    expected[1].getTime()
                ) );

        List<Timestamp> result = (List<Timestamp>) jse.decode( new MyType( List.class, Timestamp.class ) );

        assertNotNull( result );
        assertTrue( Arrays.equals( expected , result.toArray() ) );
    }

    public void testCalendar() throws IOException{
        Calendar[] expected = new Calendar[]{ new GregorianCalendar(), new GregorianCalendar() };
        JSONDecoder jse = new JSONDecoder( String.format(
                    "[ %d, %d ]",
                    expected[0].getTime().getTime(),
                    expected[1].getTime().getTime()
                ) );

        List<Calendar> result = (List<Calendar>) jse.decode( new MyType( List.class, Calendar.class ) );

        assertNotNull( result );
        assertTrue( Arrays.equals( expected , result.toArray() ) );
    }

    public void testNull() throws IOException{
        Object[] array = new Object[]{ null, null };
        JSONDecoder jse = new JSONDecoder( "[ null, null ]" );

        List<Object> result = (List<Object>) jse.decode( new MyType( List.class, Object.class ) );

        assertNotNull( result );
        assertTrue( Arrays.equals( array , result.toArray() ) );
    }

}
