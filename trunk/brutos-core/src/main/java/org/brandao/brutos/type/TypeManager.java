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
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.brandao.brutos.EnumerationType;
import org.brandao.brutos.web.http.UploadedFile;
import org.brandao.brutos.web.http.Download;

/**
 * Faz o gerenciamento dos tipos da aplicação.
 * 
 * @author Brandao
 */
public class TypeManager {

    private final static List staticTypes;
    
    private final List customTypes;

    private final ConcurrentMap cache;
    
    static {
        staticTypes = new LinkedList();
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
        staticTypes.add(new DefaultTypeFactory(SerializableType.class,    Serializable.class));
        staticTypes.add(new DefaultTypeFactory(DefaultDateType.class,     Date.class));
        staticTypes.add(new DefaultTypeFactory(CalendarType.class,        Calendar.class));
        staticTypes.add(new DefaultArrayTypeFactory());
        staticTypes.add(new DefaultEnumTypeFactory());
        staticTypes.add(new DefaultTypeFactory(ClassType.class,           Class.class));
        staticTypes.add(new ObjectTypeFactory());
    }

    public TypeManager(){
        this.customTypes = new LinkedList();
        this.cache = new ConcurrentHashMap();
    }
    
    /**
     * Registra um novo tipo.
     * @param factory Fábrica do tipo.
     */
    public void register(TypeFactory factory) {
        customTypes.add(factory);
        this.cache.clear();
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
        
        this.cache.clear();
    }
    
    /**
     * Remove um tipo a apartir sua fábrica.
     * @param factory Fábrica do tipo.
     */
    public void remove(TypeFactory factory) {
        customTypes.remove(factory);
        this.cache.clear();
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
        
        TypeFactory factory = (TypeFactory) this.cache.get(classType);
        
        if(factory != null)
            return factory;
        else{
            synchronized(this){
                factory = (TypeFactory) this.cache.get(classType);
                if(factory != null)
                    return factory;
             
                factory = getInternalTypeFactory(classType);
                this.cache.put(classType, factory);
                return factory;
            }
        }
    }
    
    private TypeFactory getInternalTypeFactory(Object classType) {
        Class rawType = TypeUtil.getRawType(classType);

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

        Class rawType = TypeUtil.getRawType(classType);
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
            
            tmp.setParameters(TypeUtil.getParameters(classType));
            tmp.setRawClass(rawType);
        }
        
        if(type instanceof CollectionType){
            Object collectionGenericType = TypeUtil.getCollectionType(classType);
            Class collectionType = TypeUtil.getRawType(collectionGenericType);
            
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

}
