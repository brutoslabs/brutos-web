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

import java.util.Collection;

import org.brandao.brutos.BrutosException;
import org.brandao.brutos.ClassUtil;
import org.brandao.brutos.MvcResponse;
import org.brandao.brutos.web.http.ParameterList;

/**
 * 
 * @author Brandao
 */
public abstract class AbstractCollectionType 
	extends AbstractType 
	implements CollectionType {

	private Type collectionType;

	private Class<?> rawClass;

	private Object[] parameters;

	public void setCollectionType(Type type) {
		this.collectionType = type;
	}

	public Type getCollectionType() {
		return this.collectionType;
	}

	public void setRawClass(Class<?> value) {
		this.rawClass = value;
	}

	public Class<?> getRawClass() {
		return this.rawClass;
	}

	public void setParameters(Object[] value) {
		this.parameters = value;
	}

	public Object[] getParameters() {
		return this.parameters;
	}

	public Object convert(Object value) {
		if (value instanceof ParameterList)
			return getCollection(value);
		else
			return value;
	}

	public void show(MvcResponse response, Object value){
		response.process(value);
	}

	protected abstract Class<?> getCollectionClass();

	@SuppressWarnings("unchecked")
	protected Collection<?> getCollection(Object value) {

		try {
			Collection<Object> collection = (Collection<Object>) ClassUtil
					.getInstance(getCollectionClass());

			ParameterList list = (ParameterList) value;
			int size = list.size();
			for (int i = 0; i < size; i++) {
				Object o = list.get(i);
				collection.add(this.collectionType.convert(o));
			}
			return collection;
		} catch (Throwable e) {
			throw new BrutosException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public String toString(Object value) {

		try {
			Collection<Object> collection = (Collection<Object>) value;
			int i = 0;
			StringBuilder r = new StringBuilder("[ ");
			for (Object o: collection) {
				
				if(i++ > 0){
					r.append(", ");
				}
				
				String str = this.collectionType.toString(o);
				
				if(this.collectionType.getClassType() == String.class || 
					this.collectionType.getClass() == AnyType.class){
				
					r.append("\"").append(str).append("\"");
				}
				else{
					r.append(str);
				}
			}
			r.append(" ]");
			return r.toString();
			
		}
		catch (Throwable e) {
			throw new BrutosException(e);
		}
		
	}
	
}
