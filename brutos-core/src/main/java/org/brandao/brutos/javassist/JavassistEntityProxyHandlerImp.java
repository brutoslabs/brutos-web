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

package org.brandao.brutos.javassist;

import javassist.util.proxy.MethodHandler;
import org.brandao.brutos.ConfigurableApplicationContext;
import org.brandao.brutos.Invoker;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.proxy.ActionEntityProxyHandler;

/**
 * 
 * @author Brandao
 */
public class JavassistEntityProxyHandlerImp extends ActionEntityProxyHandler implements
		MethodHandler {

	public JavassistEntityProxyHandlerImp(Object resource, Controller form,
			ConfigurableApplicationContext context, Invoker invoker) {
		super(resource, form, context, invoker);
	}

}
