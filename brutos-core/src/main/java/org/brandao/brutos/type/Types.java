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
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.http.BrutosFile;
import org.brandao.brutos.EnumerationType;
import org.brandao.brutos.http.Download;
import java.lang.reflect.Array;

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
        types.put( Character.TYPE,      CharacterType.class );
        types.put( Double.TYPE,         DoubleType.class );
        types.put( Float.TYPE,          FloatType.class );
        types.put( Integer.TYPE,        IntegerType.class );
        types.put( Long.TYPE,           LongType.class );
        types.put( Short.TYPE,          ShortType.class );
        types.put( String.class,        StringType.class );
        types.put( BrutosFile.class,    BrutosFileType.class );
        types.put( File.class,          FileType.class );
        types.put( Boolean.class,       BooleanType.class );
        types.put( Byte.class,          ByteType.class );
        types.put( Character.class,     CharacterType.class );
        types.put( Double.class,        DoubleType.class );
        types.put( Float.class,         FloatType.class );
        types.put( Integer.class,       IntegerType.class );
        types.put( Long.class,          LongType.class );
        types.put( Short.class,         ShortType.class );
        types.put( Download.class,      DownloadType.class );
        types.put( Enum.class,          DefaultEnumType.class );
        types.put( List.class ,         ListType.class );
        types.put( Set.class ,          SetType.class );
        types.put( Object.class ,       ObjectType.class );
        types.put( Serializable.class , JSONType.class );
        types.put( Array.class,         DefaultArrayType.class );
    }
    
    public Types() {
    }

    public static void setType( Class clazz, Class type ){
        types.put( clazz, type );
    }
    
    public static Type getType( java.lang.reflect.Type classType ){
        return getType( classType, EnumerationType.ORDINAL, "dd/MM/yyyy" );
    }
    
    public static Type getType( java.lang.reflect.Type classType, EnumerationType enumType, String maskDate ){
        Class typeClass = (Class)types.get( getClass( classType ) );

        if( typeClass == null ){
            if( getClass(classType).isEnum() )
                typeClass = (Class)types.get( Enum.class );
            else
            if( getClass(classType).isArray() )
                typeClass = (Class)types.get( java.lang.reflect.Array.class );
            else
            if( Serializable.class.isAssignableFrom( getClass(classType) ) )
                typeClass = (Class)types.get( Serializable.class );
        }

        if( typeClass != null )
            return (Type) getInstance( typeClass, classType, enumType, maskDate );
        else
            return new ObjectType((Class)classType);
    }

    private static Object getInstance( Class clazz, java.lang.reflect.Type classType, EnumerationType enumType, String maskDate ){
        try{
            Object instance = clazz.newInstance();
            
            if( instance instanceof EnumType ){
                ((EnumType)instance).setEnumType( enumType );
                ((EnumType)instance).setClassType( getClass( classType ) );
            }

            if( instance instanceof SerializableType )
                ((SerializableType)instance).setClassType( getClass( classType ) );
            
            if( instance instanceof DateTimeType )
                ((DateTimeType)instance).setMask( maskDate );
            
            if( instance instanceof CollectionType )
                ((CollectionType)instance).setGenericType( classType );
            
            if( instance instanceof ArrayType ){
                ((ArrayType)instance).setContentType( getClass( classType ).getComponentType() );
                ((ArrayType)instance).setClassType( getClass( classType ) );
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

    private static Class getClass( java.lang.reflect.Type type ){
        if( type instanceof ParameterizedType )
            return (Class)((ParameterizedType)type).getRawType();
        else
            return (Class)type;
    }
}
