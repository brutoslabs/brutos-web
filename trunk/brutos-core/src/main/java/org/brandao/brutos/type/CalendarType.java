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

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 
 * @author Brandao
 */
public class CalendarType extends DefaultDateType {

	public CalendarType() {
	}

	public CalendarType(String pattern) {
		super.setPattern(pattern);
	}

	private Object toValue(Object value) {
		Calendar cal = GregorianCalendar.getInstance();

		cal.setTime((Date) value);
		return cal;
	}

	public Class<?> getClassType() {
		return Calendar.class;
	}

	public Object convert(Object value) {
		if (value instanceof Calendar)
			return value;
		else 
		if (value instanceof String) {
			Object o = super.convert(value);
			return ((String) value).isEmpty() ? null : toValue(o);
		}
		else if (value == null)
			return null;
		else
			throw new UnknownTypeException();
	}

	public String toString(Object value) {
		if (value instanceof Calendar)
			return this.sdf.format(((Calendar)value).getTime());
		else 
		if (value instanceof String) {
			Object o = super.convert(value);
			return ((String) value).isEmpty() ? null : this.sdf.format((Date)o);
		}
		else 
		if (value == null)
			return null;
		else
			throw new UnknownTypeException();
	}
	
}
