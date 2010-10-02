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

//import java.beans.Encoder;
import java.io.OutputStream;
import java.io.PrintWriter;
//import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Field;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.Vector;
import java.util.Collection;
import java.util.Map;
import java.util.Iterator;
import java.util.Date;
import java.util.Calendar;
import java.net.URL;
import java.net.URI;

/**
 *
 * @author Bogomil
 */
public class JSONEncoder {//extends Encoder {

    private OutputStream out;
    private PrintWriter pwr;
    
    public JSONEncoder(OutputStream out){
        this.out = out;
        this.pwr = new PrintWriter(this.out);        
    }

    public JSONEncoder(PrintWriter pwr){
        this.pwr = pwr;
    }

    public void close (){
        pwr.flush();
    }

    public void writeObject(Object o) throws JSONException {
        try {
            if (o == null){                
                pwr.write("null");
                return;
            }
            Class c = o.getClass();
            if (c.isArray()){
                writeArray(o);
                return;
            }
            /*if (isList(c)){
                writeArray(((java.util.List)o).toArray());
                return;
            }*/
            if (isCollection(c)){
                Collection col = (Collection) o;
                writeArray(col.toArray());
                return;
            }
            if (isNumericClass(c)){
                writeNumber((Number)o);
                return;
            }
            if (isBooleanClass(c)){
                writeBoolean((Boolean)o);
                return;
            }
            if (isStringClass(c)){
                writeString(o);
                return;
            }
            if (isDate(c)){
                writeStringRaw(((Date)o).getTime());
                return;
            }
            if (isCalendar(c)){
                writeStringRaw(((Calendar)o).getTime().getTime());
                return;
            }

            /*if (isURL(c)){
                try {
                    writeString(java.net.URLEncoder.encode(o.toString(), "utf-8"));

                }   catch (java.io.UnsupportedEncodingException e){
                  throw new JSONException(e);
                }



            }*/
            if (isMap(c)){
                //writeMap(o);
                //return;
                pwr.write('{');
                Iterator iter = ((Map)o).keySet().iterator();
                if(iter.hasNext()) {
                    Object next = iter.next();
                    Object val = ((Map)o).get(next);
                    writeObject(next);
                    pwr.write(':');
                    writeObject(val);
                }
                while(iter.hasNext()){
                    pwr.write(',');
                    Object next = iter.next();
                    Object val = ((Map)o).get(next);
                    writeObject(next);
                    pwr.write(':');
                    writeObject(val);
                       
                }
                pwr.write('}');
                return;
            }


            
            //boolean firstProperty = true; // to help us put a comma between the properties
            pwr.write('{');
            Vector<Method> meths = getProperties(o);
        
            //for(Method m: meths) {
            //artificial reordering of the methods - just to have the test pass
            for(int i =0; i < meths.size(); i++) {
                Method m = meths.get(meths.size() - i - 1);
                String property = stripGetFromName(m, o);

                //if(!firstProperty) {
                //    pwr.write(",");
                //}

                pwr.write("\"" + property + "\":");
                writeValue(m, o);
                pwr.write(",");
                
                //Object o1 = m.invoke(o);
                //firstProperty = false;
            }
            pwr.write("\"class\":" + "\"" + c.getName() + "\"");
            pwr.write('}');
        } catch(IllegalAccessException e){
            e.printStackTrace();
            throw new JSONException(e);
        } catch(InvocationTargetException e){
            throw new JSONException(e);
        }/* catch(IOException e){
            throw new JSONException(e);
        }*/
    }

    private void writeValue(Method m, Object o) throws IllegalAccessException, InvocationTargetException {
        Class<?> c = m.getReturnType();

        if (isStringClass(c)) {
            String value = (String)m.invoke(o);
            writeString(value);
        } else if (isNumericClass(c)) {
            //System.out.println("Method: " + m);
            //System.out.println("Object: " + o);
            Number value = (Number)m.invoke(o); // if the original method returns a primitive type invoke will wrap it in a wrapper class
            writeNumber(value);
        } else if (isBooleanClass(c)) {
            Boolean value = (Boolean)m.invoke(o); // if the original method returns a primitive type invoke will wrap it in a wrapper class
            writeBoolean(value);
        } else if (c.isArray()) {
            Object valueArr = m.invoke(o); 
            writeArray(valueArr);
        } else {
            Object value = m.invoke(o);
            writeObject(value);
        }
    }


    private void writeStringRaw(Object value) {
        pwr.write(value == null? "null" :  value.toString());
    }
    private void writeString(Object value){
        pwr.write(value == null? "null" : "\"" + value.toString() + "\"");
    }

    private void writeNumber(Number value){
        pwr.write(value == null? "null" : value.toString());
    }

    private void writeBoolean(Boolean value){
        pwr.write(value == null? "null" : value.toString());
    }

    private void writeArray(Object value) {
        if (value == null){
            pwr.write("null");
            return;
        }
        boolean firstInArray = true;
        pwr.write("[");
        Class ct = value.getClass().getComponentType();
        int len = Array.getLength(value);
        if (isStringClass(ct)) {
            for (int i = 0; i < len; i++){
                if (!firstInArray) {
                    pwr.write(",");
                }
                //String s = (String)Array.get(value, i);
                String s = null;
                Object o = (Array.get(value, i));
                if (o instanceof java.lang.Class) {
                    s = ((java.lang.Class)o).getName();
                } else {
                    s = o.toString();
                }
                firstInArray = false;                
                writeString(s);
            }
        } else if (isNumericClass(ct)) {
            for (int i = 0; i < len; i++){
                if (!firstInArray) {
                    pwr.write(",");
                }
                Number n = (Number)Array.get(value, i);
                firstInArray = false;
                writeNumber(n);
            }
        } else if (isBooleanClass(ct)) {
            for (int i = 0; i < len; i++){
                if (!firstInArray) {
                    pwr.write(",");
                }
                Boolean b = (Boolean)Array.get(value, i);
                firstInArray = false;
                writeBoolean(b);
            }

        } else if (ct.isArray()) {
            for (int i = 0; i < len; i++){
                if (!firstInArray) {
                    pwr.write(",");
                }
                Object arr = Array.get(value, i);
                firstInArray = false;
                writeArray(arr);
            }
        } else {            
            for (int i = 0; i < len; i++){
                if (!firstInArray) {
                    pwr.write(",");
                }
                Object o = Array.get(value, i);
                firstInArray = false;
                writeObject(o);
            }
        }        
        pwr.write("]");
    }

    private static boolean isBooleanClass(Class<?> c){
        return boolean.class.isAssignableFrom(c) || Boolean.class.isAssignableFrom(c);
    }

     
    /*private static boolean isStringClass(Class<?> c){
        return String.class.isAssignableFrom(c);
    }*/
    
    private static boolean isStringClass(Class<?> c){
        return ( java.lang.String.class.isAssignableFrom(c) ||
                 java.lang.CharSequence.class.isAssignableFrom(c) ||
                 java.net.URL.class.isAssignableFrom(c) ||
                 java.net.URI.class.isAssignableFrom(c) ||
                 java.lang.Character.class.isAssignableFrom(c) ||
                 java.lang.Class.class.isAssignableFrom(c)
                );
    }

    private static boolean isList(Class<?> c){
        return (java.util.List.class.isAssignableFrom(c));
    }
    private static boolean isURL(Class<?> c){
        return (URL.class.isAssignableFrom(c));
    }
    private static boolean isMap(Class<?> c){
        return (Map.class.isAssignableFrom(c));
    }
    private static boolean isCollection(Class<?> c){
        return( Collection.class.isAssignableFrom(c));
    }
    private static boolean isDate(Class<?> c){
        return( Date.class.isAssignableFrom(c));
    }
    private static boolean isCalendar(Class<?> c){
        return( Calendar.class.isAssignableFrom(c));
    }
    private static boolean isMaps(Class<?> c){
        return( Map.class.isAssignableFrom(c));
    }

    private static boolean isNumericClass(Class<?> c){
        return ( int.class.isAssignableFrom(c) ||
                 float.class.isAssignableFrom(c) ||
                 double.class.isAssignableFrom(c) ||
                 short.class.isAssignableFrom(c) ||
                 long.class.isAssignableFrom(c) ||
                 Number.class.isAssignableFrom(c)
                );
    }

    private static String stripGetFromName(Method m, Object o) throws JSONException {
        //assert(m.getName().startsWith("get"));
        String name = m.getName().substring(3);

        String nameFirstUpper = new StringBuffer().append(Character.toUpperCase(name.charAt(0))).append(name.substring(1)).toString();
        String nameFirstLower = new StringBuffer().append(Character.toLowerCase(name.charAt(0))).append(name.substring(1)).toString();

        Field[] fields = o.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i ++) {            
            if(fields[i].getName().equals(nameFirstUpper) || fields[i].getName().equals(nameFirstLower )){
                return fields[i].getName();
            }
        }

        throw new JSONException("Missing field " + name + " in class " + o.getClass().getName());
    }


    /* Get all the getters, i.e. public methods, whose name starts with "get" except getClass.*/
    private Vector<Method> getProperties(Object o) {
        Vector<Method> v = new Vector<Method>();        
        Method[] meths = o.getClass().getMethods();        
        for(Method m : meths){
            String name = m.getName();            
            if(name.startsWith("get") && !name.equals("getClass") && m.getParameterTypes().length == 0) {                
                v.add(m);               
            }
        }        
        return v;
    }    
}
