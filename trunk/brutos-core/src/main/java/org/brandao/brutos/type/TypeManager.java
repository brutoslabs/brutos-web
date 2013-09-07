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

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.*;
import org.brandao.brutos.ApplicationContext;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.EnumerationType;
import org.brandao.brutos.web.http.BrutosFile;
import org.brandao.brutos.web.http.Download;

/**
 *
 * @author Brandao
 */
public class TypeManager {

    private final static List staticTypes = new LinkedList();
    private final static List customTypes = new LinkedList();
    private static Class defaultListType;
    private static Class defaultSetType;
    private static Class defaultMapType;

    static {
        staticTypes.add(new DefaultTypeFactory(BooleanType.class, Boolean.TYPE));
        staticTypes.add(new DefaultTypeFactory(ByteType.class, Byte.TYPE));
        staticTypes.add(new DefaultTypeFactory(CharType.class, Character.TYPE));
        staticTypes.add(new DefaultTypeFactory(DoubleType.class, Double.TYPE));
        staticTypes.add(new DefaultTypeFactory(FloatType.class, Float.TYPE));
        staticTypes.add(new DefaultTypeFactory(IntegerType.class, Integer.TYPE));
        staticTypes.add(new DefaultTypeFactory(LongType.class, Long.TYPE));
        staticTypes.add(new DefaultTypeFactory(ShortType.class, Short.TYPE));
        staticTypes.add(new DefaultTypeFactory(StringType.class, String.class));
        staticTypes.add(new DefaultTypeFactory(BrutosFileType.class, BrutosFile.class));
        staticTypes.add(new DefaultTypeFactory(FileType.class, File.class));
        staticTypes.add(new DefaultTypeFactory(BooleanWrapperType.class, Boolean.class));
        staticTypes.add(new DefaultTypeFactory(ByteWrapperType.class, Byte.class));
        staticTypes.add(new DefaultTypeFactory(CharacterType.class, Character.class));
        staticTypes.add(new DefaultTypeFactory(DoubleWrapperType.class, Double.class));
        staticTypes.add(new DefaultTypeFactory(FloatWrapperType.class, Float.class));
        staticTypes.add(new DefaultTypeFactory(IntegerWrapperType.class, Integer.class));
        staticTypes.add(new DefaultTypeFactory(LongWrapperType.class, Long.class));
        staticTypes.add(new DefaultTypeFactory(ShortWrapperType.class, Short.class));
        staticTypes.add(new DefaultTypeFactory(DownloadType.class, Download.class));
        staticTypes.add(new DefaultTypeFactory(ListType.class, List.class));
        staticTypes.add(new DefaultTypeFactory(SetType.class, Set.class));
        staticTypes.add(new DefaultTypeFactory(SerializableTypeImp.class, Serializable.class));
        staticTypes.add(new DefaultTypeFactory(DefaultDateTimeType.class, Date.class));
        staticTypes.add(new DefaultTypeFactory(CalendarType.class, Calendar.class));
        staticTypes.add(new DefaultArrayTypeFactory());
        staticTypes.add(new DefaultEnumTypeFactory());
        staticTypes.add(new DefaultTypeFactory(ClassType.class, Class.class));
        staticTypes.add(new ObjectTypeFactory());

        /*
         * types.put( Class.class, new
         * DefaultTypeFactory(ClassType.class,Class.class)); types.put(
         * Boolean.TYPE, new
         * DefaultTypeFactory(BooleanType.class,Boolean.TYPE)); types.put(
         * Byte.TYPE, new DefaultTypeFactory(ByteType.class,Byte.TYPE ));
         * types.put( Character.TYPE, new
         * DefaultTypeFactory(CharType.class,Character.TYPE )); types.put(
         * Double.TYPE, new DefaultTypeFactory(DoubleType.class,Double.TYPE));
         * types.put( Float.TYPE, new
         * DefaultTypeFactory(FloatType.class,Float.TYPE )); types.put(
         * Integer.TYPE, new DefaultTypeFactory(IntegerType.class,Integer.TYPE
         * )); types.put( Long.TYPE, new
         * DefaultTypeFactory(LongType.class,Long.TYPE )); types.put(
         * Short.TYPE, new DefaultTypeFactory(ShortType.class,Short.TYPE ));
         * types.put( String.class, new
         * DefaultTypeFactory(StringType.class,String.class )); types.put(
         * BrutosFile.class, new
         * DefaultTypeFactory(BrutosFileType.class,BrutosFile.class ));
         * types.put( File.class, new
         * DefaultTypeFactory(FileType.class,File.class )); types.put(
         * Boolean.class, new
         * DefaultTypeFactory(BooleanWrapperType.class,Boolean.class ));
         * types.put( Byte.class, new
         * DefaultTypeFactory(ByteWrapperType.class,Byte.class )); types.put(
         * Character.class, new
         * DefaultTypeFactory(CharacterType.class,Character.class )); types.put(
         * Double.class, new
         * DefaultTypeFactory(DoubleWrapperType.class,Double.class ));
         * types.put( Float.class, new
         * DefaultTypeFactory(FloatWrapperType.class,Float.class )); types.put(
         * Integer.class, new
         * DefaultTypeFactory(IntegerWrapperType.class,Integer.class ));
         * types.put( Long.class, new
         * DefaultTypeFactory(LongWrapperType.class,Long.class )); types.put(
         * Short.class, new
         * DefaultTypeFactory(ShortWrapperType.class,Short.class )); types.put(
         * Download.class, new
         * DefaultTypeFactory(DownloadType.class,Download.class )); types.put(
         * Enum.class, new DefaultTypeFactory(DefaultEnumType.class,Enum.class
         * )); types.put( List.class , new
         * DefaultTypeFactory(ListType.class,List.class )); types.put( Set.class
         * , new DefaultTypeFactory(SetType.class,Set.class )); types.put(
         * Object.class , new DefaultTypeFactory(ObjectType.class,Object.class
         * )); types.put( Serializable.class , new
         * DefaultTypeFactory(SerializableTypeImp.class,Serializable.class ));
         * types.put( Date.class, new
         * DefaultTypeFactory(DefaultDateTimeType.class,Date.class ));
         * types.put( Calendar.class, new
         * DefaultTypeFactory(CalendarType.class,Calendar.class )); types.put(
         * Array.class, new
         * DefaultTypeFactory(DefaultArrayType.class,Array.class ));
         */

        defaultListType = ArrayList.class;
        defaultSetType = HashSet.class;
        defaultMapType = HashMap.class;
    }

    public static void register(TypeFactory factory) {
        customTypes.add(factory);
    }

    public static void remove(Class type) {
        Iterator i = customTypes.iterator();
        while (i.hasNext()) {
            TypeFactory factory = (TypeFactory) i.next();
            if (factory.getClassType() == type) {
                customTypes.remove(factory);;
            }
        }

        i = staticTypes.iterator();
        while (i.hasNext()) {
            TypeFactory factory = (TypeFactory) i.next();
            if (factory.getClassType() == type) {
                customTypes.remove(factory);
            }
        }
    }
    
    public static void remove(TypeFactory factory) {
        customTypes.remove(factory);
    }

    public static List getAllTypes(){
        return customTypes;
    }
    
    public static boolean isStandardType(Class clazz) {
        TypeFactory typeFactory =
                getTypeFactory(clazz);

        return typeFactory != null && typeFactory.getClassType() != Object.class;
    }

    public static Type getType(Object classType) {
        return getType(classType, EnumerationType.ORDINAL, "dd/MM/yyyy");
    }

    public static TypeFactory getTypeFactory(Object classType) {
        Class rawType = getRawType(classType);

        Iterator i = customTypes.iterator();
        while (i.hasNext()) {
            TypeFactory factory = (TypeFactory) i.next();
            if (factory.matches(rawType)) {
                return factory;
            }
        }

        i = staticTypes.iterator();
        while (i.hasNext()) {
            TypeFactory factory = (TypeFactory) i.next();
            if (factory.matches(rawType)) {
                return factory;
            }
        }

        return null;

        /*
         * TypeFactory factory = (TypeFactory) types.get(getRawType( classType
         * ));
         *
         * if( factory == null ){ //FIXME: refactor the line
         * getRawType(classType).isEnum() if( getRawType(classType).isEnum() )
         * factory = (TypeFactory)types.get( Enum.class ); else if(
         * getRawType(classType).isArray() ) factory = (TypeFactory)types.get(
         * java.lang.reflect.Array.class ); else //if(
         * Serializable.class.isAssignableFrom( getRawType(classType) ) ) //
         * factory = (TypeFactory)types.get( Serializable.class ); //else
         * factory = (TypeFactory)types.get( Object.class ); } return factory;
         */
    }

    public static Type getType(
            Object classType, EnumerationType enumType, String maskDate) {

        Class rawType = getRawType(classType);

        TypeFactory factory =
                getTypeFactory(rawType);

        Type type = factory.getInstance();

        if (type instanceof EnumType) {
            ((EnumType) type).setEnumType(enumType);
            ((EnumType) type).setClassType(rawType);
        }

        if (type instanceof SerializableType) {
            ((SerializableType) type).setClassType(rawType);
        }

        if (type instanceof DateTimeType) {
            ((DateTimeType) type).setMask(maskDate);
        }

        if (type instanceof CollectionType) {
            ((CollectionType) type).setGenericType(classType);
        }

        if (type instanceof ArrayType) {
            ((ArrayType) type).setContentType(rawType.getComponentType());
            ((ArrayType) type).setClassType(rawType);
        }

        return type;
    }

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

    public static Object getKeyType(Object type) {
        int index = -1;

        Class rawType = getRawType(type);

        if (Map.class.isAssignableFrom(rawType)) {
            index = 0;
        }

        return getParameter(type, index);
    }

    public static Object getParameter(Object type, int index) {
        try {
            Class parameterizedTypeClass =
                    Class.forName("java.lang.reflect.ParameterizedType");

            if (parameterizedTypeClass.isAssignableFrom(type.getClass())) {
                Method getRawType =
                        parameterizedTypeClass.getMethod("getActualTypeArguments", new Class[]{});

                Object args = getRawType.invoke(type, new Object[]{});

                return Array.get(args, index);
            } else {
                return null;
            }
        } catch (Exception e) {
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
