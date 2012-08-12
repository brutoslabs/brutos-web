/*
 * Brutos Web MVC http://www.brutosframework.com.br/
 * Copyright (C) 2009 Afonso Brandao. (afonso.rbn@gmail.com)
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
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.EnumerationType;
import org.brandao.brutos.web.http.BrutosFile;
import org.brandao.brutos.web.http.Download;

/**
 *
 * @author Brandao
 */
public class TypeManager {

    private final static Map types = new HashMap();
    
    private static Class defaultListType;
    
    private static Class defaultSetType;
    
    private static Class defaultMapType;
    
    static{
        types.put( Class.class,         new DefaultTypeFactory(ClassType.class ));
        types.put( Boolean.TYPE,        new DefaultTypeFactory(BooleanType.class ));
        types.put( Byte.TYPE,           new DefaultTypeFactory(ByteType.class ));
        types.put( Character.TYPE,      new DefaultTypeFactory(CharType.class ));
        types.put( Double.TYPE,         new DefaultTypeFactory(DoubleType.class ));
        types.put( Float.TYPE,          new DefaultTypeFactory(FloatType.class ));
        types.put( Integer.TYPE,        new DefaultTypeFactory(IntegerType.class ));
        types.put( Long.TYPE,           new DefaultTypeFactory(LongType.class ));
        types.put( Short.TYPE,          new DefaultTypeFactory(ShortType.class ));
        types.put( String.class,        new DefaultTypeFactory(StringType.class ));
        types.put( BrutosFile.class,    new DefaultTypeFactory(BrutosFileType.class ));
        types.put( File.class,          new DefaultTypeFactory(FileType.class ));
        types.put( Boolean.class,       new DefaultTypeFactory(BooleanWrapperType.class ));
        types.put( Byte.class,          new DefaultTypeFactory(ByteWrapperType.class ));
        types.put( Character.class,     new DefaultTypeFactory(CharacterType.class ));
        types.put( Double.class,        new DefaultTypeFactory(DoubleWrapperType.class ));
        types.put( Float.class,         new DefaultTypeFactory(FloatWrapperType.class ));
        types.put( Integer.class,       new DefaultTypeFactory(IntegerWrapperType.class ));
        types.put( Long.class,          new DefaultTypeFactory(LongWrapperType.class ));
        types.put( Short.class,         new DefaultTypeFactory(ShortWrapperType.class ));
        types.put( Download.class,      new DefaultTypeFactory(DownloadType.class ));
        types.put( Enum.class,          new DefaultTypeFactory(DefaultEnumType.class ));
        types.put( List.class ,         new DefaultTypeFactory(ListType.class ));
        types.put( Set.class ,          new DefaultTypeFactory(SetType.class ));
        types.put( Object.class ,       new DefaultTypeFactory(ObjectType.class ));
        types.put( Serializable.class , new DefaultTypeFactory(SerializableTypeImp.class ));
        types.put( Date.class,          new DefaultTypeFactory(DefaultDateTimeType.class ));
        types.put( Calendar.class,      new DefaultTypeFactory(CalendarType.class ));
        types.put( Array.class,         new DefaultTypeFactory(DefaultArrayType.class ));
        
        defaultListType = ArrayList.class;
        defaultSetType  = HashSet.class;
        defaultMapType  = HashMap.class;
    }
    
    public static void register( Class clazz, TypeFactory factory ){
        types.put(clazz, factory);
    }

    public static void remove( Class clazz ){
        types.remove(clazz);
    }
    
   public static boolean isStandardType(Class clazz){
       TypeFactory typeFactory = 
            getTypeFactory( clazz );
               
       return typeFactory != null && !(typeFactory.getClassType() == ObjectType.class);
   }
   
    public static Type getType( Object classType ){
        return getType( classType, EnumerationType.ORDINAL, "dd/MM/yyyy" );
    }
    
    private static TypeFactory getTypeFactory( Object classType ){
        
        TypeFactory factory = (TypeFactory) types.get(getRawType( classType ));
        
        if( factory == null ){
            /*FIXME: refactor the line getRawType(classType).isEnum()*/
            if( getRawType(classType).isEnum() )
                factory = (TypeFactory)types.get( Enum.class );
            else
            if( getRawType(classType).isArray() )
                factory = (TypeFactory)types.get( java.lang.reflect.Array.class );
            else
            if( Serializable.class.isAssignableFrom( getRawType(classType) ) )
                factory = (TypeFactory)types.get( Serializable.class );
            else
                factory = (TypeFactory)types.get( Object.class );
        }
        return factory;
    }
    
    public static Type getType( Object classType, EnumerationType enumType, String maskDate ){
        
        TypeFactory factory = 
                getTypeFactory( classType );
                
        Type type = factory.getInstance();
            
        if( type instanceof EnumType ){
            ((EnumType)type).setEnumType( enumType );
            ((EnumType)type).setClassType( getRawType( classType ) );
        }

        if( type instanceof SerializableType )
            ((SerializableType)type).setClassType( getRawType( classType ) );

        if( type instanceof DateTimeType )
            ((DateTimeType)type).setMask( maskDate );

        if( type instanceof CollectionType )
            ((CollectionType)type).setGenericType( classType );

        if( type instanceof ArrayType ){
            ((ArrayType)type).setContentType( getRawType( classType ).getComponentType() );
            ((ArrayType)type).setClassType( getRawType( classType ) );
        }
        
        return type;
    }
    
    public static Class getRawType( Object type ){
        try{
            Class parameterizedTypeClass =
                    Class.forName("java.lang.reflect.ParameterizedType");

            if( parameterizedTypeClass.isAssignableFrom(type.getClass()) ){
                Method getRawType =
                        parameterizedTypeClass.getMethod("getRawType", new Class[]{});

                Object clazz = getRawType.invoke(type, new Object[]{});
                return (Class)clazz;
            }
            else
            if( type instanceof Class )
                return (Class)type;
            else
                throw new BrutosException( "invalid type: " + type );
        }
        catch( ClassNotFoundException ex ){
            if( type instanceof Class )
                return (Class)type;
            else
                throw new BrutosException( "invalid type: " + type );
        }
        catch( Exception e ){
            throw new BrutosException(e);
        }
    }

    public static Object getCollectionType( Object type ){
        int index = -1;

        Class rawType = getRawType(type);
        
        if(Map.class.isAssignableFrom(rawType))
            index = 1;
        else
        if(Collection.class.isAssignableFrom(rawType))
            index = 0;
        
        return getParameter(type, index);
    }

    public static Object getKeyType( Object type ){
        int index = -1;

        Class rawType = getRawType(type);
        
        if(Map.class.isAssignableFrom(rawType))
            index = 0;
        
        return getParameter(type, index);
    }
    
    public static Object getParameter( Object type, int index ){
        try{
            Class parameterizedTypeClass =
                    Class.forName("java.lang.reflect.ParameterizedType");

            if( parameterizedTypeClass.isAssignableFrom(type.getClass()) ){
                Method getRawType =
                        parameterizedTypeClass.getMethod("getActualTypeArguments", new Class[]{});

                Object args = getRawType.invoke(type, new Object[]{});
                
                return Array.get(args, index);
            }
            else
                return null;
        }
        catch( Exception e ){
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
