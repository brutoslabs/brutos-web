/*
 * Brutos Web MVC http://www.brutosframework.com.br/
 * Copyright (C) 2009-2017 Afonso Brandao. (afonso.rbn@gmail.com)
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

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.brandao.brutos.BrutosConstants;
import org.brandao.brutos.EnumerationType;
import org.brandao.brutos.ResultAction;
import org.brandao.brutos.TypeManager;

/**
 * 
 * @author Brandao
 */
public class TypeManagerImp implements TypeManager {

	private final List<TypeFactory> defaultTypes;

	private final List<TypeFactory> customTypes;

	private final ConcurrentMap<Class<?>, TypeFactory> cache;

	public TypeManagerImp() {
		this.customTypes = new LinkedList<TypeFactory>();
		this.cache = new ConcurrentHashMap<Class<?>, TypeFactory>();
		defaultTypes = new LinkedList<TypeFactory>();
		defaultTypes
				.add(new DefaultTypeFactory(BooleanType.class, Boolean.TYPE));
		defaultTypes.add(new DefaultTypeFactory(ByteType.class, Byte.TYPE));
		defaultTypes
				.add(new DefaultTypeFactory(CharType.class, Character.TYPE));
		defaultTypes.add(new DefaultTypeFactory(DoubleType.class, Double.TYPE));
		defaultTypes.add(new DefaultTypeFactory(FloatType.class, Float.TYPE));
		defaultTypes
				.add(new DefaultTypeFactory(IntegerType.class, Integer.TYPE));
		defaultTypes.add(new DefaultTypeFactory(LongType.class, Long.TYPE));
		defaultTypes.add(new DefaultTypeFactory(ShortType.class, Short.TYPE));
		defaultTypes
				.add(new DefaultTypeFactory(StringType.class, String.class));
		defaultTypes.add(new DefaultTypeFactory(BooleanWrapperType.class,
				Boolean.class));
		defaultTypes.add(new DefaultTypeFactory(ByteWrapperType.class,
				Byte.class));
		defaultTypes.add(new DefaultTypeFactory(CharacterType.class,
				Character.class));
		defaultTypes.add(new DefaultTypeFactory(DoubleWrapperType.class,
				Double.class));
		defaultTypes.add(new DefaultTypeFactory(FloatWrapperType.class,
				Float.class));
		defaultTypes.add(new DefaultTypeFactory(IntegerWrapperType.class,
				Integer.class));
		defaultTypes.add(new DefaultTypeFactory(LongWrapperType.class,
				Long.class));
		defaultTypes.add(new DefaultTypeFactory(ShortWrapperType.class,
				Short.class));
		defaultTypes.add(new DefaultTypeFactory(ListType.class, List.class));
		defaultTypes.add(new DefaultTypeFactory(SetType.class, Set.class));
		defaultTypes.add(new DefaultTypeFactory(SerializableType.class,
				Serializable.class));
		defaultTypes.add(new DefaultTypeFactory(DefaultDateType.class,
				Date.class));
		defaultTypes.add(new DefaultTypeFactory(CalendarType.class,
				Calendar.class));
		defaultTypes.add(new DefaultTypeFactory(BigDecimalType.class,
				BigDecimal.class));
		defaultTypes.add(new DefaultTypeFactory(BigIntegerType.class,
				BigInteger.class));
		defaultTypes.add(new DefaultArrayTypeFactory());
		defaultTypes.add(new DefaultEnumTypeFactory());
		defaultTypes.add(new DefaultTypeFactory(ClassType.class, Class.class));
		defaultTypes.add(new DefaultTypeFactory(ResultActionType.class, ResultAction.class));
		defaultTypes.add(new ObjectTypeFactory());
	}

	public void register(TypeFactory factory) {
		customTypes.add(factory);
		this.cache.clear();
	}

	public void remove(Class<?> type) {
		List<TypeFactory> factoryToRemove = new ArrayList<TypeFactory>();

		Iterator<TypeFactory> i = customTypes.iterator();
		
		while (i.hasNext()) {
			TypeFactory factory = (TypeFactory) i.next();
			if (factory.getClassType() == type) {
				factoryToRemove.add(factory);
			}
		}

		for (int k = 0; k < factoryToRemove.size(); k++) {
			Object factory = factoryToRemove.get(k);
			customTypes.remove(factory);
		}

		factoryToRemove.clear();

		i = defaultTypes.iterator();
		while (i.hasNext()) {
			TypeFactory factory = (TypeFactory) i.next();
			if (factory.getClassType() == type) {
				factoryToRemove.add(factory);
			}
		}

		for (int k = 0; k < factoryToRemove.size(); k++) {
			Object factory = factoryToRemove.get(k);
			defaultTypes.remove(factory);
		}

		this.cache.clear();
	}

	public void remove(TypeFactory factory) {
		customTypes.remove(factory);
		this.cache.clear();
	}

	public List<TypeFactory> getAllTypes() {
		return customTypes;
	}

	public boolean isStandardType(Class<?> clazz) {
		TypeFactory typeFactory = getTypeFactory(clazz);

		return (clazz == Object.class && typeFactory != null)
				|| (typeFactory != null && typeFactory.getClassType() != Object.class);
	}

	public Type getType(Object classType) {
		return getType(classType, BrutosConstants.DEFAULT_ENUMERATIONTYPE, BrutosConstants.DEFAULT_TEMPORALPROPERTY);
	}

	public TypeFactory getTypeFactory(Object classType) {

		TypeFactory factory = this.cache.get(classType);

		if (factory != null)
			return factory;
		else {
			synchronized (this) {
				factory = (TypeFactory) this.cache.get(classType);
				if (factory != null)
					return factory;

				factory = getInternalTypeFactory(classType);
				this.cache.put((Class<?>)classType, factory);
				return factory;
			}
		}
	}

	private TypeFactory getInternalTypeFactory(Object classType) {
		Class<?> rawType        = TypeUtil.getRawType(classType);
		Iterator<TypeFactory> i = customTypes.iterator();
		
		while (i.hasNext()) {
			TypeFactory factory = (TypeFactory) i.next();
			if (factory.matches(rawType)) {
				return factory;
			}
		}

		i = defaultTypes.iterator();
		while (i.hasNext()) {
			TypeFactory factory = (TypeFactory) i.next();
			if (factory.matches(rawType)) {
				return factory;
			}
		}

		return null;
	}

	public Type getType(Object classType, EnumerationType enumType,
			String pattern) {

		Class<?> rawType    = TypeUtil.getRawType(classType);
		TypeFactory factory = getTypeFactory(rawType);
		Type type           = factory.getInstance();

		type.setClassType(
				classType instanceof Class ? 
					(Class<?>) classType :
					rawType);

		if (type instanceof EnumType) {
			EnumType tmp = (EnumType) type;
			tmp.setEnumerationType(enumType);
		}

		if (type instanceof DateTimeType) {
			DateTimeType tmp = (DateTimeType) type;
			tmp.setPattern(pattern);
		}

		if (type instanceof GenericType) {
			GenericType tmp = (GenericType) type;

			tmp.setParameters(TypeUtil.getParameters(classType));
			tmp.setRawClass(rawType);
		}

		if (type instanceof CollectionType) {
			Object collectionGenericType = TypeUtil
					.getCollectionType(classType);

			if (collectionGenericType == null)
				collectionGenericType = Object.class;

			Class<?> collectionType = TypeUtil.getRawType(collectionGenericType);

			CollectionType tmp = (CollectionType) type;
			tmp.setCollectionType(this.getType(collectionType));
		}

		if (type instanceof ArrayType) {
			ArrayType tmp = (ArrayType) type;
			tmp.setComponentType(this.getType(rawType.getComponentType()));
			tmp.setRawClass(rawType);
		}

		return type;
	}

}
