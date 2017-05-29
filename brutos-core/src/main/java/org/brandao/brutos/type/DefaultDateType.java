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

import java.text.SimpleDateFormat;
import java.util.Date;

import org.brandao.brutos.MvcResponse;

/**
 * 
 * @author Brandao
 */
public class DefaultDateType 
	extends AbstractType 
	implements DateTimeType {

	protected SimpleDateFormat sdf;

	public DefaultDateType(String pattern) {
		this.setPattern(pattern);
	}

	public DefaultDateType() {
	}

	public void setPattern(String value) {
		sdf = new SimpleDateFormat(value);
		sdf.setLenient(false);
	}

	private Object toValue(String value) {
		try {
			return sdf.parse(value);
		} catch (Throwable e) {
			return null;
		}
	}

	public Class<?> getClassType() {
		return Date.class;
	}

	public Object convert(Object value) {
		if (value instanceof Date)
			return value;
		else if (value instanceof String)
			return ((String) value).isEmpty() ? null : toValue((String) value);
		else if (value == null)
			return null;
		else
			throw new UnknownTypeException(value.getClass().getName());
	}

	public void show(MvcResponse response, Object value){
		response.process(value);
	}

	public String getPattern() {
		return this.sdf.toPattern();
	}

	public String toString(Object value){
		if (value instanceof Date)
			return this.sdf.format((Date)value);
		else 
		if (value instanceof String){
			Object o = this.convert(value);			
			return ((String) value).isEmpty() ? null : this.sdf.format((Date)o);
		}
		else
		if (value == null)
			return null;
		else
			throw new UnknownTypeException(value.getClass().getName());
		
	}
}
