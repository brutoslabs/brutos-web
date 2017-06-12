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

import org.brandao.brutos.EnumerationType;
import org.brandao.brutos.MvcResponse;
import org.brandao.brutos.bean.EnumUtil;

/**
 * 
 * @author Brandao
 */
public class DefaultEnumType extends AbstractType implements EnumType {

	private EnumerationType type;
	
	//private Type intType;
	
	//private Type stringType;
	
	private EnumUtil enumUtil;

	public DefaultEnumType() {
		//intType    = new IntegerType();
		//stringType = new StringType();
	}

	public void setClassType(Class<?> classType) {
		super.setClassType(classType);
		this.enumUtil = new EnumUtil(classType);
	}

	public Object convert(Object value) {
		try {
			if (value == null)
				return null;
			else if (this.classType.isAssignableFrom(value.getClass()))
				return value;
			else if (value instanceof String) {
				String tmp = (String) value;

				if (tmp.isEmpty()) {
					return null;
				}

				Object result = null;

				if (type == EnumerationType.AUTO) {
					result = this.enumUtil.valueByIndex(tmp);

					if (result == null)
						result = this.enumUtil.valueByName(tmp);
				} else if (type == EnumerationType.ORDINAL) {
					result = this.enumUtil.valueByIndex(tmp);
				} else
					result = this.enumUtil.valueByName(tmp);

				if (result == null)
					throw new UnknownTypeException("enum not found: "
							+ this.classType.getName() + "." + value);
				else
					return result;
			} else
				throw new UnknownTypeException(value.getClass().getName());
		} catch (UnknownTypeException e) {
			throw e;
		} catch (Exception e) {
			throw new UnknownTypeException(e);
		}
	}

	public void show(MvcResponse response, Object value){
		response.process(value);
	}

	public EnumerationType getEnumerationType() {
		return this.type;
	}

	public void setEnumerationType(EnumerationType type) {
		this.type = type;
	}

	public String toString(Object value) {
		try {
			if(value == null){
				return null;
			}
			else
			if (this.classType.isAssignableFrom(value.getClass())){
				if(type == EnumerationType.AUTO || type == EnumerationType.STRING){
					return ((Enum<?>)value).name();
				}
				else{
					return String.valueOf(((Enum<?>)value).ordinal());
				}
			}
			else 
			if (value instanceof String) {
				value = this.convert(value);
				return this.toString(value);
			}
			else
				throw new UnknownTypeException(value.getClass().getName());
		}
		catch (UnknownTypeException e) {
			throw e;
		}
		catch (Exception e) {
			throw new UnknownTypeException(e);
		}
	}
	
}
