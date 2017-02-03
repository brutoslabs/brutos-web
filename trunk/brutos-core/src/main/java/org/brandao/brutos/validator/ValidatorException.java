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

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import org.brandao.brutos.BrutosException;

public class ValidatorException extends BrutosException implements Serializable {

	public List exceptions = new LinkedList();

	public ValidatorException() {
		super();
	}

	public ValidatorException(String message) {
		super(message);
	}

	public ValidatorException(String message, Throwable cause) {
		super(message, cause);
	}

	public ValidatorException(Throwable cause) {
		super(cause);
	}

	public void addCause(ValidatorException vex) {
		exceptions.add(vex);
	}

	public void addCauses(List vex) {
		exceptions.addAll(vex);
	}

	public List getCauses() {
		return this.exceptions;
	}

}
