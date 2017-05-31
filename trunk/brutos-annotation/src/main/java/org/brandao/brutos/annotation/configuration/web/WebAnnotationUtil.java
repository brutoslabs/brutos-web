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

package org.brandao.brutos.annotation.configuration.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.brandao.brutos.annotation.configuration.ThrowableEntry;
import org.brandao.brutos.annotation.web.ResponseError;
import org.brandao.brutos.annotation.web.ResponseErrors;

/**
 * 
 * @author Brandao
 *
 */
public class WebAnnotationUtil {

	public static List<ResponseError> toList(ResponseErrors value) {
		return Arrays.asList(value.exceptions());
	}
	
	public static ThrowableEntry toEntry(ResponseError value) {
		return new WebThrowableEntry(value);
	}

	public static List<ThrowableEntry> toList(List<ResponseError> list) {

		List<ThrowableEntry> result = new ArrayList<ThrowableEntry>();

		for (ResponseError t : list)
			result.add(toEntry(t));

		return result;
	}
	
}
