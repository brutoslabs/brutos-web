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
import junit.framework.Test;
import junit.framework.TestCase;
import org.brandao.brutos.type.json.JSONDecoder;
import org.brandao.brutos.type.json.JSONException;

/**
 *
 * @author Afonso Brandao
 */
public class JSONDecoderObjectTest extends TestCase implements Test{

    public static class Test1{
    }

    public static class Test2 extends Test1 implements Serializable{

        private int f1;
        private String f2;
        private char f3;

        public Test2(){
            this.f1 = 100;
            this.f2 = null;
        }

        public int getF1() {
            return f1;
        }

        public void setF1(int f1) {
            this.f1 = f1;
        }

        public String getF2() {
            return f2;
        }

        public void setF2(String f2) {
            this.f2 = f2;
        }

        public char getF3() {
            return f3;
        }

        public void setF3(char f3) {
            this.f3 = f3;
        }

    }

    public static class Test3 implements Serializable{

        private Test3 f;
        private int v = 100;

        public Test3(){
            this.f = this;
        }

        public Test3 getF() {
            return f;
        }

        public void setF(Test3 f) {
            this.f = f;
        }

        public int getV() {
            return v;
        }

        public void setV(int v) {
            this.v = v;
        }

    }

    public static class Test4 extends Test3 implements Serializable{

        private Test4 f;
        private int v = 200;

        public Test4(){
            this.f = this;
        }

        public Test4 getF() {
            return f;
        }

        public void setF(Test4 f) {
            this.f = f;
        }

        public int getV() {
            return v;
        }

        public void setV(int v) {
            this.v = v;
        }

    }

    public JSONDecoderObjectTest(){
        super();
    }

    public void testNull() throws IOException{
        try{
            new JSONDecoder( (String)null );
        }
        catch( NullPointerException e ){
            return;
        }
        
        fail( "expected NullPointerException" );
    }

    public void testInvalidObject() throws IOException{
        try{
            JSONDecoder jse = new JSONDecoder( "objectName" );
           jse.decode();
        }
        catch( JSONException e ){
            return;
        }
        fail( "expected JSONException" );
    }

    public void testWithoutComma() throws IOException{
        try{
            JSONDecoder jse = new JSONDecoder( "{ \"name\" : null \"name\" : null }" );
           jse.decode();
        }
        catch( JSONException e ){
            return;
        }
        fail( "expected JSONException" );
    }

    public void testWithoutColon() throws IOException{
        try{
            JSONDecoder jse = new JSONDecoder( "{ \"name\" null }" );
           jse.decode();
        }
        catch( JSONException e ){
            return;
        }
        fail( "expected JSONException" );
    }

    public void testNullString() throws IOException{
        JSONDecoder jse = new JSONDecoder( "null" );
        assertNull( jse.decode() );
    }

    public void testClassString() throws IOException{
        JSONDecoder jse = new JSONDecoder(String.format( "\"%s\"" , Integer.class.getName() )  );
        assertEquals( Integer.class.getName(), jse.decode() );
    }

    public void testClass() throws IOException{
        JSONDecoder jse = new JSONDecoder(String.format( "\"%s\"" , Integer.class.getName() )  );
        assertEquals( Integer.class, jse.decode( Class.class ) );
    }

    public void testObjectError() throws IOException{
        try{
            JSONDecoder jse = new JSONDecoder( "{" );
            jse.decode();
        }
        catch( JSONException e ){
            return;
        }
        fail( "expected JSONException" );
    }

    public void testStringZero() throws IOException{
        try{
            JSONDecoder jse = new JSONDecoder( "" );
            jse.decode();
        }
        catch( JSONException e ){
            return;
        }
        fail( "expected JSONException" );
    }

    public void testNotSerializable() throws IOException{
        try{
            JSONDecoder jse = new JSONDecoder( String.format( "\"%s\"", Test.class.getName() ) );
            jse.decode( Test1.class );
        }
        catch( JSONException e ){
            return;
        }
        fail( "expected JSONException" );
    }

    public void testExtendsNotSerializable() throws IOException{
        JSONDecoder jsd = new JSONDecoder(
            "{ \"class\" : \"org.brandao.brutos.type.json.jsondecoder.JSONDecoderObjectTest$Test2\", " +
            "\"f1\" : 100, " +
            "\"f3\" : \"\", " +
            "\"f2\" : null }"
        );

        Test2 origin = new Test2();
        Object result = jsd.decode();
        assertNotNull( result );
        assertEquals( Test2.class,result.getClass() );
        assertEquals( origin.getF1(), ((Test2)result).getF1() );
        assertEquals( origin.getF2(), ((Test2)result).getF2() );
        assertEquals( origin.getF3(), ((Test2)result).getF3() );
    }

    public void testCiclico() throws IOException{
        try{
            JSONDecoder jsd = new JSONDecoder(
                "{ \"f\" : null, " +
                "\"class\" : \"org.brandao.brutos.type.json.jsondecoder.JSONDecoderObjectTest$Test3\", " +
                "\"v\" : 100" +
                " }"        );

            jsd.decode();
        }
        catch( Throwable e ){
            fail( "not expected Exception: " + e.getMessage() );
        }
    }

    public void testExtends() throws IOException{
        JSONDecoder jsd = new JSONDecoder(
            "{ \"class\" : \"org.brandao.brutos.type.json.jsondecoder.JSONDecoderObjectTest$Test4\", " +
            "\"v\" : 200, " +
            "\"f\" : null }"
        );

        Test4 origin = new Test4();
        Test4 result = (Test4) jsd.decode();
        assertNotNull( result );
        assertEquals( origin, origin.getF() );
        assertEquals( 200, result.getV() );
    }

    public void testSemIdentificacaoException() throws IOException{
        try{
            JSONDecoder jsd = new JSONDecoder(
                "{ \"v\" : 200, " +
                "\"f\" : null }"
            );
            jsd.decode();
        }
        catch( JSONException e ){
            return;
        }
        fail( "expected JSONException" );
    }

    public void testSemIdentificacao() throws IOException{
        JSONDecoder jsd = new JSONDecoder(
            "{ \"v\" : 200, " +
            "\"f\" : null }"
        );

        Test4 origin = new Test4();
        Test4 result = jsd.decode( Test4.class );
        assertNotNull( result );
        assertEquals( origin, origin.getF() );
        assertEquals( 200, result.getV() );
    }

}
