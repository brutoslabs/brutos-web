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


package org.brandao.brutos.type.json.jsonencoder;

import java.io.ByteArrayOutputStream;
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
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import junit.framework.Test;
import junit.framework.TestCase;
import org.brandao.brutos.type.json.JSONEncoder;

/**
 *
 * @author Afonso Brandao
 */
public class JSONEncoderArrayTest extends TestCase implements Test{

    public static class MyObject implements Serializable{
        private Long a;
        private byte b;

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

    public JSONEncoderArrayTest(){
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

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JSONEncoder jse = new JSONEncoder( out );
        jse.writeObject( maps );
        jse.close();
        
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

        //assertEquals( map, out.toString() );
        assertEquals( Utils.trimWhiteSpace(map), Utils.trimWhiteSpace(out.toString()) );
    }

    public void testObject() throws IOException{

        MyObject o0 = new MyObject( 10l, (byte)25 );
        MyObject o1 = new MyObject( 20l, (byte)-25 );
        MyObject o2 = new MyObject( 1l, (byte)127 );


        MyObject[] array = new MyObject[]{ null, o0, o1, o2 };

        String ex = String.format( 
                        "[ null, %s, %s, %s ]",
                        "{ \"b\" : 25, \"a\" : 10, \"class\" : \"org.brandao.brutos.type.json.jsonencoder.JSONEncoderArrayTest$MyObject\" }",
                        "{ \"b\" : -25, \"a\" : 20, \"class\" : \"org.brandao.brutos.type.json.jsonencoder.JSONEncoderArrayTest$MyObject\" }",
                        "{ \"b\" : 127, \"a\" : 1, \"class\" : \"org.brandao.brutos.type.json.jsonencoder.JSONEncoderArrayTest$MyObject\" }" );

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JSONEncoder jse = new JSONEncoder( out );
        jse.writeObject( array );
        jse.close();

        //assertEquals( ex, out.toString() );
        assertEquals( Utils.trimWhiteSpace(ex), Utils.trimWhiteSpace(out.toString()) );
    }

    public void testCollection() throws IOException{

        MyObject o0 = new MyObject( 10l, (byte)25 );
        MyObject o1 = new MyObject( 20l, (byte)-25 );
        MyObject o2 = new MyObject( 1l, (byte)127 );


        Collection<MyObject> collection = new ArrayList<MyObject>();
        collection.add( null );
        collection.add( o0 );
        collection.add( o1 );
        collection.add( o2 );

        Collection[] array = new Collection[]{ null, collection,collection,collection };
        String ex = String.format(
                        "[ null, %s, %s, %s ]",
                        "{ \"b\" : 25, \"a\" : 10, \"class\" : \"org.brandao.brutos.type.json.jsonencoder.JSONEncoderArrayTest$MyObject\" }",
                        "{ \"b\" : -25, \"a\" : 20, \"class\" : \"org.brandao.brutos.type.json.jsonencoder.JSONEncoderArrayTest$MyObject\" }",
                        "{ \"b\" : 127, \"a\" : 1, \"class\" : \"org.brandao.brutos.type.json.jsonencoder.JSONEncoderArrayTest$MyObject\" }" );

        String result = String.format(
                    "[ null, %s, %s, %s ]",
                    ex,
                    ex,
                    ex );

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JSONEncoder jse = new JSONEncoder( out );
        jse.writeObject( array );
        jse.close();
        
        //assertEquals( result, out.toString() );
        assertEquals( Utils.trimWhiteSpace(result), Utils.trimWhiteSpace(out.toString()) );
    }

    public void testInt() throws IOException{
        int[] array = new int[]{ 0, 100, 200, 150 };
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JSONEncoder jse = new JSONEncoder( out );
        jse.writeObject( array );
        jse.close();
        //assertEquals( "[ 0, 100, 200, 150 ]", out.toString() );
        assertEquals( Utils.trimWhiteSpace("[ 0, 100, 200, 150 ]"), Utils.trimWhiteSpace(out.toString()) );

    }

    public void testLong() throws IOException{
        long[] array = new long[]{ 0, 100, 200, 150 };
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JSONEncoder jse = new JSONEncoder( out );
        jse.writeObject( array );
        jse.close();
        //assertEquals( "[ 0, 100, 200, 150 ]", out.toString() );
        assertEquals( Utils.trimWhiteSpace("[ 0, 100, 200, 150 ]"), Utils.trimWhiteSpace(out.toString()) );
    }

    public void testDouble() throws IOException{
        double[] array = new double[]{ 0, 15.2, 20.2, 1.01 };
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JSONEncoder jse = new JSONEncoder( out );
        jse.writeObject( array );
        jse.close();
        //assertEquals( "[ 0.0, 15.2, 20.2, 1.01 ]", out.toString() );
        assertEquals( Utils.trimWhiteSpace("[ 0.0, 15.2, 20.2, 1.01 ]"), Utils.trimWhiteSpace(out.toString()) );
    }

    public void testFloat() throws IOException{
        float[] array = new float[]{ 0f, 15.2f, 20.2f, 1.01f };
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JSONEncoder jse = new JSONEncoder( out );
        jse.writeObject( array );
        jse.close();
        //assertEquals( "[ 0.0, 15.2, 20.2, 1.01 ]", out.toString() );
        assertEquals( Utils.trimWhiteSpace("[ 0.0, 15.2, 20.2, 1.01 ]"), Utils.trimWhiteSpace(out.toString()) );
    }

    public void testShort() throws IOException{
        short[] array = new short[]{ 0, 100, 200, 150 };
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JSONEncoder jse = new JSONEncoder( out );
        jse.writeObject( array );
        jse.close();
        //assertEquals( "[ 0, 100, 200, 150 ]", out.toString() );
        assertEquals( Utils.trimWhiteSpace("[ 0, 100, 200, 150 ]"), Utils.trimWhiteSpace(out.toString()) );
    }

    public void testBoolean() throws IOException{
        boolean[] array = new boolean[]{ true, false, false, true };
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JSONEncoder jse = new JSONEncoder( out );
        jse.writeObject( array );
        jse.close();
        //assertEquals( "[ true, false, false, true ]", out.toString() );
        assertEquals( Utils.trimWhiteSpace("[ true, false, false, true ]"), Utils.trimWhiteSpace(out.toString()) );
    }

    public void testChar() throws IOException{
        char[] array = new char[]{ 'A', 'F', 'O', 'N' };
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JSONEncoder jse = new JSONEncoder( out );
        jse.writeObject( array );
        jse.close();
        //assertEquals( "[ \"A\", \"F\", \"O\", \"N\" ]", out.toString() );
        assertEquals( Utils.trimWhiteSpace("[ \"A\", \"F\", \"O\", \"N\" ]"), Utils.trimWhiteSpace(out.toString()) );
    }

    public void testByte() throws IOException{
        byte[] array = new byte[]{ (byte)0, (byte)-100, (byte)127, (byte)99 };
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JSONEncoder jse = new JSONEncoder( out );
        jse.writeObject( array );
        jse.close();
        //assertEquals( "[ 0, -100, 127, 99 ]", out.toString() );
        assertEquals( Utils.trimWhiteSpace("[ 0, -100, 127, 99 ]"), Utils.trimWhiteSpace(out.toString()) );

    }

    public void testBigDecimal() throws IOException{
        BigDecimal[] array = new BigDecimal[]{ new BigDecimal(0), new BigDecimal(-100), new BigDecimal( 127 ) };
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JSONEncoder jse = new JSONEncoder( out );
        jse.writeObject( array );
        jse.close();
        //assertEquals( "[ 0, -100, 127 ]", out.toString() );
        assertEquals( Utils.trimWhiteSpace("[ 0, -100, 127 ]"), Utils.trimWhiteSpace(out.toString()) );
    }

    public void testBigInteger() throws IOException{
        BigInteger[] array = new BigInteger[]{ new BigInteger("0",10), new BigInteger("-100",10), new BigInteger( "127",10 ) };
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JSONEncoder jse = new JSONEncoder( out );
        jse.writeObject( array );
        jse.close();
        //assertEquals( "[ 0, -100, 127 ]", out.toString() );
        assertEquals( Utils.trimWhiteSpace("[ 0, -100, 127 ]"), Utils.trimWhiteSpace(out.toString()) );

    }

    public void testString() throws IOException{
        String[] array = new String[]{ "AA", "BB", "CC", "DD" };
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JSONEncoder jse = new JSONEncoder( out );
        jse.writeObject( array );
        jse.close();
        //assertEquals( "[ \"AA\", \"BB\", \"CC\", \"DD\" ]", out.toString() );
        assertEquals( Utils.trimWhiteSpace("[ \"AA\", \"BB\", \"CC\", \"DD\" ]"), Utils.trimWhiteSpace(out.toString()) );
    }

    public void testURL() throws IOException{
        URL[] array = new URL[]{
                            new URL("http://www.mysite.com"),
                            new URL("http://www.mysite.net"),
                            new URL("http://www.mysite.br")};
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JSONEncoder jse = new JSONEncoder( out );
        jse.writeObject( array );
        jse.close();
        /*assertEquals(
            "[ " +
                "\"http\\u003a\\/\\/www\\u002emysite\\u002ecom\", " +
                "\"http\\u003a\\/\\/www\\u002emysite\\u002enet\", " +
                "\"http\\u003a\\/\\/www\\u002emysite\\u002ebr\" ]", out.toString() );*/
        assertEquals(Utils.trimWhiteSpace(
            "[ " +
                "\"http\\u003a\\/\\/www\\u002emysite\\u002ecom\", " +
                "\"http\\u003a\\/\\/www\\u002emysite\\u002enet\", " +
                "\"http\\u003a\\/\\/www\\u002emysite\\u002ebr\" ]"), Utils.trimWhiteSpace(out.toString()) );

    }

    public void testURI() throws IOException, URISyntaxException{
        URI[] array = new URI[]{
                            new URI("http://www.mysite.com"),
                            new URI("http://www.mysite.net"),
                            new URI("http://www.mysite.br")};
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JSONEncoder jse = new JSONEncoder( out );
        jse.writeObject( array );
        jse.close();
        /*assertEquals(
            "[ " +
                "\"http\\u003a\\/\\/www\\u002emysite\\u002ecom\", " +
                "\"http\\u003a\\/\\/www\\u002emysite\\u002enet\", " +
                "\"http\\u003a\\/\\/www\\u002emysite\\u002ebr\" ]", out.toString() );*/
         assertEquals(Utils.trimWhiteSpace(
            "[ " +
                "\"http\\u003a\\/\\/www\\u002emysite\\u002ecom\", " +
                "\"http\\u003a\\/\\/www\\u002emysite\\u002enet\", " +
                "\"http\\u003a\\/\\/www\\u002emysite\\u002ebr\" ]"), Utils.trimWhiteSpace(out.toString()));
    }

    public void testClass() throws IOException{
        Class[] array = new Class[]{ Integer.class, int.class, double.class, Float.class };
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JSONEncoder jse = new JSONEncoder( out );
        jse.writeObject( array );
        jse.close();
        //assertEquals( "[ \"java.lang.Integer\", \"int\", \"double\", \"java.lang.Float\" ]", out.toString() );
        assertEquals( Utils.trimWhiteSpace("[ \"java.lang.Integer\", \"int\", \"double\", \"java.lang.Float\" ]"), Utils.trimWhiteSpace(out.toString()) );
    }

    public void testLocale() throws IOException{
        Locale[] array = new Locale[]{ Locale.US, Locale.CANADA, Locale.FRANCE };
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JSONEncoder jse = new JSONEncoder( out );
        jse.writeObject( array );
        jse.close();
        /*assertEquals(
                String.format(
                    "[ \"%s\\u002d%s\", \"%s\\u002d%s\", \"%s\\u002d%s\" ]",
                    Locale.US.getLanguage(),
                    Locale.US.getCountry(),
                    Locale.CANADA.getLanguage(),
                    Locale.CANADA.getCountry(),
                    Locale.FRANCE.getLanguage(),
                    Locale.FRANCE.getCountry() )
                ,out.toString() );*/
        assertEquals(Utils.trimWhiteSpace(
                String.format(
                    "[ \"%s\\u002d%s\", \"%s\\u002d%s\", \"%s\\u002d%s\" ]",
                    Locale.US.getLanguage(),
                    Locale.US.getCountry(),
                    Locale.CANADA.getLanguage(),
                    Locale.CANADA.getCountry(),
                    Locale.FRANCE.getLanguage(),
                    Locale.FRANCE.getCountry() ))
                ,Utils.trimWhiteSpace(out.toString()));
    }

    public void testDate() throws IOException{
        Date[] array = new Date[]{ new Date(), new Date() };
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JSONEncoder jse = new JSONEncoder( out );
        jse.writeObject( array );
        jse.close();
        /*assertEquals(
                String.format(
                    "[ %d, %d ]",
                    array[0].getTime(),
                    array[1].getTime()
                ), out.toString() );*/
        assertEquals(Utils.trimWhiteSpace(
                String.format(
                    "[ %d, %d ]",
                    array[0].getTime(),
                    array[1].getTime()
                )), Utils.trimWhiteSpace(out.toString()));
    }

    public void testTime() throws IOException{
        Time[] array = new Time[]{ new Time( (new Date()).getTime() ), new Time( (new Date()).getTime() ) };
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JSONEncoder jse = new JSONEncoder( out );
        jse.writeObject( array );
        jse.close();
        /*assertEquals(
                String.format(
                    "[ %d, %d ]",
                    array[0].getTime(),
                    array[1].getTime()
                ), out.toString() );*/
        assertEquals(Utils.trimWhiteSpace(
                String.format(
                    "[ %d, %d ]",
                    array[0].getTime(),
                    array[1].getTime()
                )), Utils.trimWhiteSpace(out.toString()));
    }

    public void testTimestamp() throws IOException{
        Timestamp[] array = new Timestamp[]{ new Timestamp( (new Date()).getTime() ), new Timestamp( (new Date()).getTime() ) };
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JSONEncoder jse = new JSONEncoder( out );
        jse.writeObject( array );
        jse.close();
        /*assertEquals(
                String.format(
                    "[ %d, %d ]",
                    array[0].getTime(),
                    array[1].getTime()
                ), out.toString() );*/
        assertEquals(Utils.trimWhiteSpace(
                String.format(
                    "[ %d, %d ]",
                    array[0].getTime(),
                    array[1].getTime()
                )), Utils.trimWhiteSpace(out.toString()));
    }

    public void testCalendar() throws IOException{
        Calendar[] array = new Calendar[]{ new GregorianCalendar(), new GregorianCalendar() };
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JSONEncoder jse = new JSONEncoder( out );
        jse.writeObject( array );
        jse.close();
        /*assertEquals(
                String.format(
                    "[ %d, %d ]",
                    array[0].getTime().getTime(),
                    array[1].getTime().getTime()
                ), out.toString() );*/
        assertEquals(Utils.trimWhiteSpace(
                String.format(
                    "[ %d, %d ]",
                    array[0].getTime().getTime(),
                    array[1].getTime().getTime()
                )), Utils.trimWhiteSpace(out.toString()));
    }

    public void testNull() throws IOException{
        Object[] array = new Object[]{ null, null };
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JSONEncoder jse = new JSONEncoder( out );
        jse.writeObject( array );
        jse.close();
        //assertEquals( "[ null, null ]", out.toString() );
        assertEquals(Utils.trimWhiteSpace("[ null, null ]"), Utils.trimWhiteSpace(out.toString()));
    }

}
