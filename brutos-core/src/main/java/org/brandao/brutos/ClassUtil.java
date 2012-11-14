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


package org.brandao.brutos;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import org.brandao.brutos.type.TypeManager;

/**
 * 
 * @author Afonso Brandao
 */
public final class ClassUtil {
    
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

    public static Class getWrapper( Class clazz ){
        Class classe = (Class) primitiveType.get( clazz );
        
        return classe == null? clazz : classe;
        
    }

    public static Class get( String name ) throws ClassNotFoundException{
        Class classe = (Class) primitiveType.get( name );
        
        return classe == null? getClasse( name ) : classe;
        
    }
    
    private static Class getClasse( String name ) throws ClassNotFoundException{
        return Class.forName( name, true, Thread.currentThread().getContextClassLoader() );
    }
    
    public static Object getInstance(Class clazz, Class[] params, Object[] values) 
        throws NoSuchMethodException, InstantiationException, 
        IllegalAccessException, IllegalArgumentException, 
            InvocationTargetException{
        Constructor cons = clazz.getConstructor(params);
        return cons.newInstance(values);
    }
    
    public static Object getInstance(Class clazz) 
            throws InstantiationException, IllegalAccessException{
        return clazz.newInstance();
    }
            
    /*
    public static Object getInstance(Class clazz, 
            ConfigurableApplicationContext context) 
            throws InstantiationException, IllegalAccessException{
        
        if(context != null)
            return context.getIocProvider().getBean(clazz);
        else
            return clazz.newInstance();
    }
    */
    
    public static List getListInstance() 
            throws ClassNotFoundException, InstantiationException, 
            IllegalAccessException{
        return (List)getInstance(TypeManager.getDefaultListType());
    }
    
    public static Map getMapInstance() 
            throws ClassNotFoundException, InstantiationException, 
            IllegalAccessException{
        return (Map)getInstance(TypeManager.getDefaultMapType());
    }

    public static Set getSetInstance() 
            throws ClassNotFoundException, InstantiationException, 
            IllegalAccessException{
        return (Set)getInstance(TypeManager.getDefaultSetType());
    }
    
    public static Class getInstantiableClass(Class clazz){
        
        if(clazz == Map.class)
            return TypeManager.getDefaultMapType();
        else
        if(clazz == List.class)
            return TypeManager.getDefaultListType();
        else
        if(clazz == Set.class)
            return TypeManager.getDefaultSetType();
        else
            return clazz;
    }
}
