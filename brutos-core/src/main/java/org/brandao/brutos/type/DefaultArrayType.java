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

import java.lang.reflect.Array;

import org.brandao.brutos.BrutosException;
import org.brandao.brutos.MvcResponse;
import org.brandao.brutos.web.http.ParameterList;

/**
 * 
 * @author Brandao
 */
public class DefaultArrayType 
	extends AbstractType 
	implements ArrayType {

	private Type componentType;
	
	private Class<?> rawClass;

	public DefaultArrayType() {
	}

	private Object getArray(Object value) {
		try {
			ParameterList param = (ParameterList) value;
			Object objList = Array.newInstance(
					this.componentType.getClassType(), param.size());

			for (int i = 0; i < param.size(); i++)
				Array.set(objList, i, this.componentType.convert(param.get(i)));

			return objList;
		} catch (Exception e) {
			throw new BrutosException(e);
		}
	}

	public Object convert(Object value) {
		if (value instanceof ParameterList)
			return getArray(value);
		else
			return value;
	}

	public void show(MvcResponse response, Object value){
		response.process(value);
	}

	public void setComponentType(Type type) {
		this.componentType = type;
	}

	public Type getComponentType() {
		return this.componentType;
	}

	public void setRawClass(Class<?> value) {
		this.rawClass = value;
	}

	public Class<?> getRawClass() {
		return this.rawClass;
	}

	public String toString(Object value) {

		try {
			ParameterList list = (ParameterList) value;
			int i = 0;
			StringBuilder r = new StringBuilder("[ ");
			for (Object o: list) {
				
				if(i++ > 0){
					r.append(", ");
				}
				
				String str = this.componentType.toString(o);
				
				if(this.componentType.getClassType() == String.class || 
					this.componentType.getClass() == AnyType.class){
				
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
