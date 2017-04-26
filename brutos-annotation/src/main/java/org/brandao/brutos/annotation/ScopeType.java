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

package org.brandao.brutos.annotation;

/**
 * Descreve os escopos pré-definidos.
 * 
 * @author Brandao
 */
public interface ScopeType {

	/**
	 * O contexto é a requisição.
	 */
	public static final String REQUEST = "request";

	/**
	 * O contexto é o caebçalho de uma solicitação.
	 */
	public static final String HEADER = "header";
	
	/**
	 * O contexto são os parâmetros da requisição.
	 */
	public static final String PARAM = "param";

	/**
	 * O contexto é uma <code>Thread</code>.
	 */
	public static final String THREAD = "thread";

	/**
	 * O contexto é definido pelo container IoC.
	 */
	public static final String IOC = "ioc";

	/**
	 * O contexto é um controlador.
	 */
	public static final String CONTROLLER = "controller";

	/**
	 * O contexto é uma sessão.
	 */
	public static final String SESSION = "session";

	/**
	 * O contexto é a aplicação.
	 */
	public static final String APPLICATION = "application";

}
