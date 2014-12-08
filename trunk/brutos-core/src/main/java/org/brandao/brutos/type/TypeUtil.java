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

package org.brandao.brutos.type;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import org.brandao.brutos.BrutosException;

/**
 *
 * @author Brandao
 */
public final class TypeUtil {
    
    private static Class defaultListType;
    
    private static Class defaultSetType;
    
    private static Class defaultMapType;
    
    static{
        defaultListType = ArrayList.class;
        defaultSetType = HashSet.class;
        defaultMapType = HashMap.class;
    }
    
    /**
     * Obtém a classe base de um tipo que usa generics.
     * @param type Classe. Pode ser uma {@link java.lang.Class} ou 
     * {@link java.lang.reflect.Type}.
     * @return Classe base.
     */
    public static Class getRawType(Object type) {
        try {
            Class parameterizedTypeClass =
                    Class.forName("java.lang.reflect.ParameterizedType");

            if (parameterizedTypeClass.isAssignableFrom(type.getClass())) {
                Method getRawType =
                        parameterizedTypeClass.getMethod("getRawType", new Class[]{});

                Object clazz = getRawType.invoke(type, new Object[]{});
                return (Class) clazz;
            } else if (type instanceof Class) {
                return (Class) type;
            } else {
                throw new BrutosException("invalid type: " + type);
            }
        } catch (ClassNotFoundException ex) {
            if (type instanceof Class) {
                return (Class) type;
            } else {
                throw new BrutosException("invalid type: " + type);
            }
        } catch (Exception e) {
            throw new BrutosException(e);
        }
    }

    /**
     * Obtém o tipo dos objetos de uma coleção.
     * @param type Classe. Pode ser uma {@link java.lang.Class} ou 
     * {@link java.lang.reflect.Type}.
     * @return Tipo da coleção.
     */
    public static Object getCollectionType(Object type) {
        int index = -1;

        Class rawType = getRawType(type);

        if (Map.class.isAssignableFrom(rawType)) {
            index = 1;
        } else if (Collection.class.isAssignableFrom(rawType)) {
            index = 0;
        }

        return getParameter(type, index);
    }

    /**
     * Obtém o tipo da chave de uma coleção.
     * @param type Classe. Pode ser uma {@link java.lang.Class} ou 
     * {@link java.lang.reflect.Type}.
     * @return Tipo da chave.
     */
    public static Object getKeyType(Object type) {
        int index = -1;

        Class rawType = getRawType(type);

        if (Map.class.isAssignableFrom(rawType)) {
            index = 0;
        }

        return getParameter(type, index);
    }

    /**
     * Obtém o parâmetro de um tipo que usa generics.
     * @param type Classe. Pode ser uma {@link java.lang.Class} ou 
     * {@link java.lang.reflect.Type}.
     * @param index Índice do parâmetro.
     * @return Parâmetro.
     */
    public static Object getParameter(Object type, int index) {
        try{
            Object args = getParameters(type);
            return args == null? null : Array.get(args, index);
        }
        catch (Exception e){
            return null;
        }
    }

    public static Object[] getParameters(Object type) {
        try{
            Class parameterizedTypeClass =
                    Class.forName("java.lang.reflect.ParameterizedType");

            if (parameterizedTypeClass.isAssignableFrom(type.getClass())) {
                Method getRawType =
                        parameterizedTypeClass.getMethod("getActualTypeArguments", new Class[]{});

                Object args = getRawType.invoke(type, new Object[]{});

                return (Object[])args;
            }
            else
                return null;
        }
        catch (Exception e){
            return null;
        }
    }
    
    public static Class getDefaultListType() {
        return defaultListType;
    }

    public static void setDefaultListType(Class aDefaultListType) {
        defaultListType = aDefaultListType;
    }

    public static Class getDefaultSetType() {
        return defaultSetType;
    }

    public static void setDefaultSetType(Class aDefaultSetType) {
        defaultSetType = aDefaultSetType;
    }

    public static Class getDefaultMapType() {
        return defaultMapType;
    }

    public static void setDefaultMapType(Class aDefaultMapType) {
        defaultMapType = aDefaultMapType;
    }
    
}
