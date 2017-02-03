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

package org.brandao.brutos.validator;

import java.util.Properties;

public class CustomValidationRule implements ValidationRule {

	private Integer start;

	private Integer end;

	public void validate(Object source, Object value) throws ValidatorException {

		if (!(value instanceof Number))
			throw new ValidatorException("invalid type: " + value.getClass());

		Integer val = new Integer(((Number) value).intValue());
		if (val.intValue() < start.intValue()
				|| val.intValue() > end.intValue())
			throw new ValidatorException();
	}

	public void setConfiguration(Properties config) {
		String range = config.getProperty("range");

		if (!range.matches("\\d+-\\d+"))
			throw new ValidatorException("invalid range syntax: " + range);

		String[] vals = range.split("-");
		this.start = Integer.valueOf(vals[0]);
		this.end = Integer.valueOf(vals[1]);
	}

}
