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
import org.brandao.brutos.BrutosConstants;
import org.brandao.brutos.web.http.UploadedFile;
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
        types.put( UploadedFile.class,    UploadedFileType.class );
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
        types.put( Serializable.class , SerializableTypeImp.class );
        types.put( Date.class,          DefaultDateType.class );
        types.put( Calendar.class,      CalendarType.class );
        types.put( Array.class,         DefaultArrayType.class );
    }
    
    public Types() {
    }

    public static void setType( Class clazz, Class type ){
        types.put( clazz, type );
    }
    
    public static Type getType( Object classType ){
        return getType( classType, EnumerationType.ORDINAL, 
                BrutosConstants.DEFAULT_TEMPORALPROPERTY);
    }
    
    public static Type getType( Object classType, EnumerationType enumType, String patternDate ){
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
            return (Type) getInstance( typeClass, classType, enumType, patternDate );
        else
            return new ObjectType((Class)classType);
    }

    private static Object getInstance( Class clazz, Object classType,
            EnumerationType enumType, String patternDate ){
        try{
            Object instance = clazz.newInstance();
            
            if( instance instanceof EnumType ){
                ((EnumType)instance).setEnumType( enumType );
                ((EnumType)instance).setClassType( getRawType( classType ) );
            }

            if( instance instanceof SerializableType )
                ((SerializableType)instance).setClassType( getRawType( classType ) );
            
            if( instance instanceof DateTimeType )
                ((DateTimeType)instance).setPattern(patternDate);
            
            if( instance instanceof CollectionType )
                ((CollectionType)instance).setGenericType( classType );
            
            if( instance instanceof ArrayType ){
                ((ArrayType)instance).setComponentType(getRawType( classType ).getComponentType() );
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

    public static Class getCollectionType( Object type ){
        try{
            Class parameterizedTypeClass =
                    Class.forName("java.lang.reflect.ParameterizedType");

            if( parameterizedTypeClass.isAssignableFrom(type.getClass()) ){
                Method getRawType =
                        parameterizedTypeClass.getMethod("getActualTypeArguments", new Class[]{});

                Object args = getRawType.invoke(type, new Object[]{});
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
