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
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.EnumerationType;
import org.brandao.brutos.web.http.UploadedFile;
import org.brandao.brutos.web.http.Download;

/**
 * Faz o gerenciamento dos tipos de uma aplicação.
 * 
 * @author Brandao
 */
public class TypeManager {

    private final static List staticTypes = new LinkedList();
    private List customTypes = new LinkedList();
    private static Class defaultListType;
    private static Class defaultSetType;
    private static Class defaultMapType;

    static {
        staticTypes.add(new DefaultTypeFactory(BooleanType.class,         Boolean.TYPE));
        staticTypes.add(new DefaultTypeFactory(ByteType.class,            Byte.TYPE));
        staticTypes.add(new DefaultTypeFactory(CharType.class,            Character.TYPE));
        staticTypes.add(new DefaultTypeFactory(DoubleType.class,          Double.TYPE));
        staticTypes.add(new DefaultTypeFactory(FloatType.class,           Float.TYPE));
        staticTypes.add(new DefaultTypeFactory(IntegerType.class,         Integer.TYPE));
        staticTypes.add(new DefaultTypeFactory(LongType.class,            Long.TYPE));
        staticTypes.add(new DefaultTypeFactory(ShortType.class,           Short.TYPE));
        staticTypes.add(new DefaultTypeFactory(StringType.class,          String.class));
        staticTypes.add(new DefaultTypeFactory(UploadedFileType.class,    UploadedFile.class));
        staticTypes.add(new DefaultTypeFactory(FileType.class,            File.class));
        staticTypes.add(new DefaultTypeFactory(BooleanWrapperType.class,  Boolean.class));
        staticTypes.add(new DefaultTypeFactory(ByteWrapperType.class,     Byte.class));
        staticTypes.add(new DefaultTypeFactory(CharacterType.class,       Character.class));
        staticTypes.add(new DefaultTypeFactory(DoubleWrapperType.class,   Double.class));
        staticTypes.add(new DefaultTypeFactory(FloatWrapperType.class,    Float.class));
        staticTypes.add(new DefaultTypeFactory(IntegerWrapperType.class,  Integer.class));
        staticTypes.add(new DefaultTypeFactory(LongWrapperType.class,     Long.class));
        staticTypes.add(new DefaultTypeFactory(ShortWrapperType.class,    Short.class));
        staticTypes.add(new DefaultTypeFactory(DownloadType.class,        Download.class));
        staticTypes.add(new DefaultTypeFactory(ListType.class,            List.class));
        staticTypes.add(new DefaultTypeFactory(SetType.class,             Set.class));
        staticTypes.add(new DefaultTypeFactory(SerializableType.class, Serializable.class));
        staticTypes.add(new DefaultTypeFactory(DefaultDateType.class,     Date.class));
        staticTypes.add(new DefaultTypeFactory(CalendarType.class,        Calendar.class));
        staticTypes.add(new DefaultArrayTypeFactory());
        staticTypes.add(new DefaultEnumTypeFactory());
        staticTypes.add(new DefaultTypeFactory(ClassType.class,           Class.class));
        staticTypes.add(new ObjectTypeFactory());

        defaultListType = ArrayList.class;
        defaultSetType = HashSet.class;
        defaultMapType = HashMap.class;
    }

    /**
     * Registra um novo tipo.
     * @param factory Fábrica do tipo.
     */
    public void register(TypeFactory factory) {
        customTypes.add(factory);
    }

    /**
     * Registra um novo tipo que é compartilhado por todas as aplicações.
     * @param factory Fábrica do tipo.
     */
    public static void registerStaticType(TypeFactory factory) {
        staticTypes.add(factory);
    }
    
    /**
     * Remove um tipo a partir de sua classe.
     * @param type Classe do tipo.
     */
    public void remove(Class type) {
        Iterator i = customTypes.iterator();
        while (i.hasNext()) {
            TypeFactory factory = (TypeFactory) i.next();
            if (factory.getClassType() == type) {
                customTypes.remove(factory);
            }
        }

        i = staticTypes.iterator();
        while (i.hasNext()) {
            TypeFactory factory = (TypeFactory) i.next();
            if (factory.getClassType() == type) {
                staticTypes.remove(factory);
            }
        }
    }
    
    /**
     * Remove um tipo a apartir sua fábrica.
     * @param factory Fábrica do tipo.
     */
    public void remove(TypeFactory factory) {
        customTypes.remove(factory);
    }

    /**
     * Obtém todos os tipos registrados.
     * @return Lista contendo todos os tipos registrados.
     */
    public List getAllTypes(){
        return customTypes;
    }
    
    /**
     * Verifica se a classe representa um tipo padrão.
     * @param clazz Classe do tipo.
     * @return Verdadeiro se for um tipo padrão, caso contrário falso.
     */
    public boolean isStandardType(Class clazz) {
        TypeFactory typeFactory =
                getTypeFactory(clazz);

        return typeFactory != null && typeFactory.getClassType() != Object.class;
    }

    /**
     * Obtém um tipo a partir de sua classe.
     * @param classType Classe do tipo. Pode ser uma {@link java.lang.Class} ou 
     * {@link java.lang.reflect.Type}.
     * @return Tipo.
     */
    public Type getType(Object classType) {
        return getType(classType, EnumerationType.ORDINAL, "dd/MM/yyyy");
    }

    /**
     * Obtém a fábrica do tipo a partir de sua classe.
     * @param classType Classe do tipo. Pode ser uma {@link java.lang.Class} ou 
     * {@link java.lang.reflect.Type}.
     * @return Fábrica do tipo.
     */
    public TypeFactory getTypeFactory(Object classType) {
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
    }

    /**
     * Obtém o tipo a partir de sua classe.
     * @param classType Classe do tipo. Pode ser uma {@link java.lang.Class} ou 
     * {@link java.lang.reflect.Type}.
     * @param enumType Tipo do mapeamento de Enum.
     * @param pattern Formato de uma data.
     * @return Tipo.
     */
    public Type getType(
            Object classType, EnumerationType enumType, String pattern) {

        Class rawType = getRawType(classType);
        TypeFactory factory = getTypeFactory(rawType);
        Type type = factory.getInstance();

        type.setClassType((Class)classType);
        
        if (type instanceof EnumType){
            EnumType tmp = (EnumType)type;
            tmp.setEnumerationType(enumType);
        }

        if(type instanceof DateTimeType){
            DateTimeType tmp = (DateTimeType)type;
            tmp.setPattern(pattern);
        }

        if(type instanceof GenericType){
            GenericType tmp = (GenericType)type;
            
            tmp.setParameters(getParameters(classType));
            tmp.setRawClass(rawType);
        }
        
        if(type instanceof CollectionType){
            Object collectionGenericType = getCollectionType(classType);
            Class collectionType = getRawType(collectionGenericType);
            
            CollectionType tmp = (CollectionType)type;
            tmp.setCollectionType(this.getType(collectionType));
        }

        if(type instanceof ArrayType){
            ArrayType tmp = (ArrayType)type;
            tmp.setComponentType(this.getType(rawType.getComponentType()));
            tmp.setRawClass(rawType);
        }
        
        return type;
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
    
    /**
     * Obtém a classe padrão de uma coleção do tipo {@link java.util.List}.
     * @return Classe.
     */
    public static Class getDefaultListType() {
        return defaultListType;
    }

    /**
     * Define a classe padrão de uma coleção do tipo {@link java.util.List}.
     * @param aDefaultListType Classe. Deve implementar a interface {@link java.util.List}.
     */
    public static void setDefaultListType(Class aDefaultListType) {
        defaultListType = aDefaultListType;
    }

    /**
     * Obtém a classe padrão de uma coleção do tipo {@link java.util.Set}.
     * @return Classe.
     */
    public static Class getDefaultSetType() {
        return defaultSetType;
    }

    /**
     * Define a classe padrão de uma coleção do tipo {@link java.util.Set}.
     * @param aDefaultSetType Classe. Deve implementar a interface {@link java.util.Set}.
     */
    public static void setDefaultSetType(Class aDefaultSetType) {
        defaultSetType = aDefaultSetType;
    }

    /**
     * Obtém a classe padrão de um mapa.
     * @return Classe.
     */
    public static Class getDefaultMapType() {
        return defaultMapType;
    }

    /**
     * Define a classe padrão de um mapa.
     * @param aDefaultMapType Classe. Deve implementar a interface {@link java.util.Map}.
     */
    public static void setDefaultMapType(Class aDefaultMapType) {
        defaultMapType = aDefaultMapType;
    }
}
