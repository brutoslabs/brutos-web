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
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
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
public class JSONDecoderArrayTest extends TestCase implements Test{

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

    public JSONDecoderArrayTest(){
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

        Map[] maps = new Map[]{ map0, map1, map2, null };

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
        
        Map[] result = jse.decode( Map[].class );

        assertNotNull(result[0]);
        assertEquals( "10", result[0].get( "key1" ) );
        assertEquals( "Text", result[0].get( "key2" ) );

        assertNotNull(result[1]);
        assertEquals( "-1.3", result[1].get( "key3" ) );
        assertEquals( "1.0", result[1].get( "key4" ) );

        assertNotNull(result[2]);
        assertEquals( "Text2", result[2].get( "key5" ) );
        assertEquals( "10000", result[2].get( "key6" ) );

        assertNull(result[3]);

    }

    public void testObject() throws IOException{

        String ex = String.format( 
                        "[ null, %s, %s, %s ]",
                        "{ \"b\" : 25, \"a\" : 10 }",
                        "{ \"b\" : -25, \"a\" : 20 }",
                        "{ \"b\" : 127, \"a\" : 1 }" );

        JSONDecoder jse = new JSONDecoder( ex );
        
        MyObject[] result = jse.decode( MyObject[].class );

        assertNull( result[0] );

        assertNotNull( result[1] );
        assertEquals( Long.valueOf( 10l ), Long.valueOf( result[1].getA() ) );
        assertEquals( Byte.valueOf( (byte)25 ), Byte.valueOf( result[1].getB() ) );

        assertNotNull( result[2] );
        assertEquals( Long.valueOf( 20l ), Long.valueOf( result[2].getA() ) );
        assertEquals( Byte.valueOf( (byte)-25 ), Byte.valueOf( result[2].getB() ) );

        assertNotNull( result[3] );
        assertEquals( Long.valueOf( 1l ), Long.valueOf( result[3].getA() ) );
        assertEquals( Byte.valueOf( (byte)127 ), Byte.valueOf( result[3].getB() ) );
    }

    public void testInt() throws IOException{
        JSONDecoder jse = new JSONDecoder( "[ 0, 100, 200, 150 ]" );
        int[] array = jse.decode( int[].class );
        assertNotNull( array );
        assertEquals( 0, array[0] );
        assertEquals( 100, array[1] );
        assertEquals( 200, array[2] );
        assertEquals( 150, array[3] );
    }

    public void testLong() throws IOException{
        JSONDecoder jse = new JSONDecoder( "[ 0, 100, 200, 150 ]" );
        long[] array = jse.decode( long[].class );
        assertNotNull( array );
        assertEquals( 0, array[0] );
        assertEquals( 100, array[1] );
        assertEquals( 200, array[2] );
        assertEquals( 150, array[3] );
    }

    public void testDouble() throws IOException{
        JSONDecoder jse = new JSONDecoder( "[ 0.0, 15.2, 20.2, 1.01 ]" );
        double[] array = jse.decode( double[].class );
        assertNotNull( array );
        assertEquals( 0.0, array[0] );
        assertEquals( 15.2, array[1] );
        assertEquals( 20.2, array[2] );
        assertEquals( 1.01, array[3] );
    }

    public void testFloat() throws IOException{
        JSONDecoder jse = new JSONDecoder( "[ 0.0, 15.2, 20.2, 1.01 ]" );
        float[] array = jse.decode( float[].class );
        assertNotNull( array );
        assertEquals( 0.0f, array[0] );
        assertEquals( 15.2f, array[1] );
        assertEquals( 20.2f, array[2] );
        assertEquals( 1.01f, array[3] );
    }

    public void testShort() throws IOException{
        JSONDecoder jse = new JSONDecoder( "[ 0, 100, 200, 150 ]" );
        short[] array = jse.decode( short[].class );
        assertNotNull( array );
        assertEquals( 0, array[0] );
        assertEquals( 100, array[1] );
        assertEquals( 200, array[2] );
        assertEquals( 150, array[3] );
    }

    public void testBoolean() throws IOException{
        JSONDecoder jse = new JSONDecoder( "[ true, false, false, true ]" );
        boolean[] array = jse.decode( boolean[].class );
        assertNotNull( array );
        assertEquals( true, array[0] );
        assertEquals( false, array[1] );
        assertEquals( false, array[2] );
        assertEquals( true, array[3] );
    }

    public void testChar() throws IOException{
        JSONDecoder jse = new JSONDecoder( "[ \"A\", \"F\", \"O\", \"N\" ]" );
        char[] array = jse.decode( char[].class );
        assertNotNull( array );
        assertEquals( 'A', array[0] );
        assertEquals( 'F', array[1] );
        assertEquals( 'O', array[2] );
        assertEquals( 'N', array[3] );
    }

    public void testByte() throws IOException{
        JSONDecoder jse = new JSONDecoder( "[ 0, -100, 127, 99 ]" );
        byte[] array = jse.decode( byte[].class );
        assertNotNull( array );
        assertEquals( (byte)0   , array[0] );
        assertEquals( (byte)-100, array[1] );
        assertEquals( (byte)127 , array[2] );
        assertEquals( (byte)99  , array[3] );
    }

    public void testBigDecimal() throws IOException{
        JSONDecoder jse = new JSONDecoder( "[ 0, -100, 127 ]" );
        BigDecimal[] array = jse.decode( BigDecimal[].class );
        assertNotNull( array );
        assertEquals( new BigDecimal(0), array[0] );
        assertEquals( new BigDecimal(-100), array[1] );
        assertEquals( new BigDecimal( 127 ), array[2] );
    }

    public void testBigInteger() throws IOException{
        JSONDecoder jse = new JSONDecoder( "[ 0, -100, 127 ]" );
        BigInteger[] array = jse.decode( BigInteger[].class );
        assertNotNull( array );
        assertEquals( new BigInteger( "0" ), array[0] );
        assertEquals( new BigInteger( "-100", 10), array[1] );
        assertEquals( new BigInteger( "127", 10 ), array[2] );
    }

    public void testString() throws IOException{
        JSONDecoder jse = new JSONDecoder( "[ \"AA\", \"BB\", \"CC\", \"DD\" ]" );
        String[] array = jse.decode( String[].class );
        assertNotNull( array );
        assertEquals( "AA", array[0] );
        assertEquals( "BB", array[1] );
        assertEquals( "CC", array[2] );
        assertEquals( "DD", array[3] );
    }

    public void testURL() throws IOException{
        JSONDecoder jse = new JSONDecoder( "[ " +
                "\"http\\u003a\\/\\/www\\u002emysite\\u002ecom\", " +
                "\"http\\u003a\\/\\/www\\u002emysite\\u002enet\", " +
                "\"http\\u003a\\/\\/www\\u002emysite\\u002ebr\" ]" );

        URL[] array = jse.decode( URL[].class );
        assertNotNull( array );
        assertEquals( new URL("http://www.mysite.com"), array[0] );
        assertEquals( new URL("http://www.mysite.net"), array[1] );
        assertEquals( new URL("http://www.mysite.br"), array[2] );
    }

    public void testURI() throws IOException, URISyntaxException{
        JSONDecoder jse = new JSONDecoder( "[ " +
                "\"http\\u003a\\/\\/www\\u002emysite\\u002ecom\", " +
                "\"http\\u003a\\/\\/www\\u002emysite\\u002enet\", " +
                "\"http\\u003a\\/\\/www\\u002emysite\\u002ebr\" ]" );

        URI[] array = jse.decode( URI[].class );
        assertNotNull( array );
        assertEquals( new URI("http://www.mysite.com"), array[0] );
        assertEquals( new URI("http://www.mysite.net"), array[1] );
        assertEquals( new URI("http://www.mysite.br"), array[2] );
    }

    public void testClass() throws IOException{
        JSONDecoder jsd = new JSONDecoder( "[ \"java.lang.Integer\", \"int\", \"double\", \"java.lang.Float\" ]" );
        Class[] array = jsd.decode( Class[].class );

        assertNotNull( array );
        assertEquals( Integer.class, array[0] );
        assertEquals( int.class, array[1] );
        assertEquals( double.class, array[2] );
        assertEquals( Float.class, array[3] );
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

        Locale[] array = jsd.decode( Locale[].class );
        assertNotNull( array );
        assertTrue( Arrays.equals(expected, array) );
    }

    public void testDate() throws IOException{
        Date[] expected = new Date[]{ new Date(), new Date() };

        JSONDecoder jse = new JSONDecoder( String.format(
                    "[ %d, %d ]",
                    expected[0].getTime(),
                    expected[1].getTime()
                ) );

        Date[] result = jse.decode( Date[].class );

        assertNotNull( result );
        assertTrue( Arrays.equals( expected , result ) );
    }

    public void testTime() throws IOException{
        Time[] expected = new Time[]{ new Time( (new Date()).getTime() ), new Time( (new Date()).getTime() ) };
        JSONDecoder jse = new JSONDecoder( String.format(
                    "[ %d, %d ]",
                    expected[0].getTime(),
                    expected[1].getTime()
                ) );

        Time[] result = jse.decode( Time[].class );

        assertNotNull( result );
        assertTrue( Arrays.equals( expected , result ) );
    }

    public void testTimestamp() throws IOException{
        Timestamp[] expected = new Timestamp[]{ new Timestamp( (new Date()).getTime() ), new Timestamp( (new Date()).getTime() ) };
        JSONDecoder jse = new JSONDecoder( String.format(
                    "[ %d, %d ]",
                    expected[0].getTime(),
                    expected[1].getTime()
                ) );

        Timestamp[] result = jse.decode( Timestamp[].class );

        assertNotNull( result );
        assertTrue( Arrays.equals( expected , result ) );
    }

    public void testCalendar() throws IOException{
        Calendar[] expected = new Calendar[]{ new GregorianCalendar(), new GregorianCalendar() };
        JSONDecoder jse = new JSONDecoder( String.format(
                    "[ %d, %d ]",
                    expected[0].getTime().getTime(),
                    expected[1].getTime().getTime()
                ) );

        Calendar[] result = jse.decode( Calendar[].class );

        assertNotNull( result );
        assertTrue( Arrays.equals( expected , result ) );
    }

    public void testNull() throws IOException{
        Object[] array = new Object[]{ null, null };
        JSONDecoder jse = new JSONDecoder( "[ null, null ]" );

        Object[] result = jse.decode( Object[].class );

        assertNotNull( result );
        assertTrue( Arrays.equals( array , result ) );
    }

}
