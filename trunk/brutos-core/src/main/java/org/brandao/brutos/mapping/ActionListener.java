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

package org.brandao.brutos.mapping;

import java.lang.reflect.Method;

/**
 * 
 * @author Brandao
 */
public class ActionListener {

	private Class classType;

	private Method preAction;

	private Method postAction;

	public ActionListener() {
	}

	public Method getPreAction() {
		return preAction;
	}

	public void setPreAction(Method preAction) {
		this.preAction = preAction;
	}

	public Method getPostAction() {
		return postAction;
	}

	public void setPostAction(Method postAction) {
		this.postAction = postAction;
	}

	public Class getClassType() {
		return classType;
	}

	public void setClassType(Class classType) {
		this.classType = classType;
	}

}
