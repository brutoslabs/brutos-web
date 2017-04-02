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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Define os valores padrões dos mapeamentos de uma ação ou controlador.
 * 
 * @author Brandão
 * @see ThrowSafe
 * @version 2.0
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface DefaultThrowSafe {

	/**
	 * @see ThrowSafe#view()
	 */
	String view() default "";

	/**
	 * @see ThrowSafe#name()
	 */
	String name() default "exception";

	/**
	 * @see ThrowSafe#dispatcher()
	 */
	String dispatcher() default "forward";

	/**
	 * @see ThrowSafe#rendered()
	 */
	boolean rendered() default true;

	/**
	 * @see ThrowSafe#enabled()
	 */
	boolean enabled() default true;

	/**
	 * @see ThrowSafe#resolved()
	 */
	boolean resolved() default false;

}
