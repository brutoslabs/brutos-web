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
 * Especifica um interceptador.
 * 
 * <pre>
 * Ex1:
 * &#064;Intercepts
 * public class MyInterceptor
 *       extends AbstractInterceptorController{
 * 
 *    public void intercepted(InterceptorStack stack, InterceptorHandler handler) 
 *       throws InterceptedException {
 *       ...
 *    }
 * }
 * 
 * Ex2:
 * public class ControllerTestInterceptorController
 *       extends AbstractInterceptorController{
 * 
 *    public void intercepted(InterceptorStack stack, InterceptorHandler handler) 
 *       throws InterceptedException {
 *       ...
 *    }
 * }
 * </pre>
 * 
 * @author Afonso Brandao
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Intercepts {

	/**
	 * Nome do interceptador. Se omitido, será usado o nome da classe.
	 */
	String name() default "";

	/**
	 * Indica que é um interceptador global. Se verdadeiro, todos os
	 * controladores serão interceptados, senão terá que ser definido quais
	 * controladores deverão ser interceptados.
	 */
	boolean isDefault() default true;

	/**
	 * Parâmetros de configuração do interceptador.
	 */
	Param[] params() default {};

}
