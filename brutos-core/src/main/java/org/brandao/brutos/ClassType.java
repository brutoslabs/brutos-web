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

package org.brandao.brutos;

import java.util.HashMap;
import java.util.Map;

/**
 * Classe usada para empacotar tipos primitivos e obter uma classe a partir
 * de seu nome.
 * 
 * @author Afonso Brandao
 */
public final class ClassType {
    
    private final static Map primitiveType;
    
    static{
        primitiveType = new HashMap();
        primitiveType.put("boolean",java.lang.Boolean.TYPE);
        primitiveType.put("byte",java.lang.Byte.TYPE);
        primitiveType.put("char",java.lang.Character.TYPE);
        primitiveType.put("double",java.lang.Double.TYPE);
        primitiveType.put("float",java.lang.Float.TYPE);
        primitiveType.put("int",java.lang.Integer.TYPE);
        primitiveType.put("long",java.lang.Long.TYPE);
        primitiveType.put("short",java.lang.Short.TYPE);
        primitiveType.put("void",java.lang.Void.TYPE);
        
        primitiveType.put(boolean.class,java.lang.Boolean.class);
        primitiveType.put(byte.class,java.lang.Byte.class);
        primitiveType.put(char.class,java.lang.Character.class);
        primitiveType.put(double.class,java.lang.Double.class);
        primitiveType.put(float.class,java.lang.Float.class);
        primitiveType.put(int.class,java.lang.Integer.class);
        primitiveType.put(long.class,java.lang.Long.class);
        primitiveType.put(short.class,java.lang.Short.class);
        primitiveType.put(void.class,java.lang.Void.class);
    }

    /**
     * Obt�m o tipo empacotado, caso seja um tipo primitivo.
     *
     * @param clazz Classe primitiva.
     * @return Classe empacotada.
     */
    public static Class getWrapper( Class clazz ){
        Class classe = (Class) primitiveType.get( clazz );
        
        return classe == null? clazz : classe;
        
    }

    /**
     * Obt�m uma classe a partir se seu nome.
     * @param name Nome da classe.
     * @return Classe.
     * @throws ClassNotFoundException Lan�ada se n�o for poss�vel obter a
     * classe.
     */
    public static Class get( String name ) throws ClassNotFoundException{
        Class classe = (Class) primitiveType.get( name );
        
        return classe == null? getClasse( name ) : classe;
        
    }
    
    private static Class getClasse( String name ) throws ClassNotFoundException{
        return Class.forName( name, true, Thread.currentThread().getContextClassLoader() );
    }
}
