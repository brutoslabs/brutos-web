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

package org.brandao.brutos.type;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.EnumerationType;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Date;
import org.brandao.brutos.web.http.BrutosFile;
import org.brandao.brutos.web.http.Download;

/**
 *
 * @author Afonso Brandao
 */
public class Types {
    
    private final static Map types = new HashMap();
    
    static{
        types.put( Class.class,         ClassType.class );
        types.put( Boolean.TYPE,        BooleanType.class );
        types.put( Byte.TYPE,           ByteType.class );
        types.put( Character.TYPE,      CharType.class );
        types.put( Double.TYPE,         DoubleType.class );
        types.put( Float.TYPE,          FloatType.class );
        types.put( Integer.TYPE,        IntegerType.class );
        types.put( Long.TYPE,           LongType.class );
        types.put( Short.TYPE,          ShortType.class );
        types.put( String.class,        StringType.class );
        types.put( BrutosFile.class,    BrutosFileType.class );
        types.put( File.class,          FileType.class );
        types.put( Boolean.class,       BooleanWrapperType.class );
        types.put( Byte.class,          ByteWrapperType.class );
        types.put( Character.class,     CharacterType.class );
        types.put( Double.class,        DoubleWrapperType.class );
        types.put( Float.class,         FloatWrapperType.class );
        types.put( Integer.class,       IntegerWrapperType.class );
        types.put( Long.class,          LongWrapperType.class );
        types.put( Short.class,         ShortWrapperType.class );
        types.put( Download.class,      DownloadType.class );
        types.put( Enum.class,          DefaultEnumType.class );
        types.put( List.class ,         ListType.class );
        types.put( Set.class ,          SetType.class );
        types.put( Object.class ,       ObjectType.class );
        types.put( Serializable.class , JSONType.class );
        types.put( Date.class,          DefaultDateTimeType.class );
        types.put( Calendar.class,      CalendarType.class );
        types.put( Array.class,         DefaultArrayType.class );
    }
    
    public Types() {
    }

    public static void setType( Class clazz, Class type ){
        types.put( clazz, type );
    }
    
    public static Type getType( Object classType ){
        return getType( classType, EnumerationType.ORDINAL, "dd/MM/yyyy" );
    }
    
    public static Type getType( Object classType, EnumerationType enumType, String maskDate ){
        Class typeClass = (Class)types.get( getRawType( classType ) );

        if( typeClass == null ){
            if( getRawType(classType).isEnum() )
                typeClass = (Class)types.get( Enum.class );
            else
            if( getRawType(classType).isArray() )
                typeClass = (Class)types.get( java.lang.reflect.Array.class );
            else
            if( Serializable.class.isAssignableFrom( getRawType(classType) ) )
                typeClass = (Class)types.get( Serializable.class );
        }

        if( typeClass != null )
            return (Type) getInstance( typeClass, classType, enumType, maskDate );
        else
            return new ObjectType((Class)classType);
    }

    private static Object getInstance( Class clazz, Object classType,
            EnumerationType enumType, String maskDate ){
        try{
            Object instance = clazz.newInstance();
            
            if( instance instanceof EnumType ){
                ((EnumType)instance).setEnumType( enumType );
                ((EnumType)instance).setClassType( getRawType( classType ) );
            }

            if( instance instanceof SerializableType )
                ((SerializableType)instance).setClassType( getRawType( classType ) );
            
            if( instance instanceof DateTimeType )
                ((DateTimeType)instance).setMask( maskDate );
            
            if( instance instanceof CollectionType )
                ((CollectionType)instance).setGenericType( classType );
            
            if( instance instanceof ArrayType ){
                ((ArrayType)instance).setContentType( getRawType( classType ).getComponentType() );
                ((ArrayType)instance).setClassType( getRawType( classType ) );
            }

            return instance;
        }
        catch( BrutosException e ){
            throw e;
        }
        catch( Exception e ){
            throw new BrutosException( e );
        }
    }

    public static Class getRawType( Object type ){
        try{
            Class parameterizedTypeClass =
                    Class.forName("java.lang.reflect.ParameterizedType");

            if( parameterizedTypeClass.isAssignableFrom(type.getClass()) ){
                Method getRawType =
                        parameterizedTypeClass.getMethod("getRawType");

                Object clazz = getRawType.invoke(type);
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

    public static Class getCollectionType( Object type ){
        try{
            Class parameterizedTypeClass =
                    Class.forName("java.lang.reflect.ParameterizedType");

            if( parameterizedTypeClass.isAssignableFrom(type.getClass()) ){
                Method getRawType =
                        parameterizedTypeClass.getMethod("getActualTypeArguments");

                Object args = getRawType.invoke(type);
                return (Class) Array.get(args, 0);
            }
            else
                return null;
        }
        catch( Exception e ){
            return null;
        }
    }

}
