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


package org.brandao.brutos.bean;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import org.brandao.brutos.BrutosException;

/**
 *
 * @author Brandao
 */
public class EnumUtil {

    private Class enumClass;

    public EnumUtil(Class enumClass){
        this.enumClass = enumClass;
    }

    public Object getEnumConstants(){
        return getEnumConstants(this.enumClass);
    }
    
    public static Object getEnumConstants(Class clazz){
        try{
            Method m =
                Class.class.getMethod("getEnumConstants", new Class[]{});
            return m.invoke(clazz, new Object[]{});
        }
        catch( Exception e ){
            throw new BrutosException(e);
        }

    }

    public Object valueOf( String value ){
        return valueOf(this.enumClass,value);
    }

    public static Object getEnumConstant( Class enumClazz, Integer index ){
        Object cons = getEnumConstants(enumClazz);
        return Array.get(cons, index.intValue());
    }

    public static Object valueOf( Class enumClazz, String value ){
        try{
            Class clazz =
                    Class.forName("java.lang.Enum");
            Method m =
                clazz.getMethod(
                    "valueOf",
                    new Class[]{Class.class,String.class});
            return m.invoke(clazz, new Object[]{enumClazz,value});
        }
        catch( Exception e ){
            throw new BrutosException(e);
        }

    }

}
