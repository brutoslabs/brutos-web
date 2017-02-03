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
import org.brandao.brutos.interceptor.InterceptorController;

/**
 * Indica que um interceptor em particular pertence a uma determinada pilha de
 * interceptadores.
 * 
 * <pre>
 * Ex:
 * &#064;Intercepts
 * &#064;InterceptsStack(name="stackA")
 * public class Interceptor1
 *       extends AbstractInterceptorController{
 *    ...
 * }
 * 
 * &#064;Intercepts
 * &#064;InterceptsStack(name="stackA",executeAfter=Interceptor1.class)
 * public class Interceptor2
 *       extends AbstractInterceptorController{
 *    ...
 * }
 * 
 * &#064;InterceptsStack(name="stackA",executeAfter=Interceptor2.class)
 * public class Interceptor3InterceptorController
 *       extends AbstractInterceptorController{
 *    ...
 * }
 * 
 * &#064;Controller
 * &#064;InterceptedBy(
 *    name="stackA",
 *    params={
 *       &#064;Param(name="interceptor1.name1",value="value1"),
 *       &#064;Param(name="interceptor1.name2",value="value2")
 *    }
 * )
 * public class MyController{
 *    ...
 * }
 * 
 * </pre>
 * 
 * @author Brandao
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface InterceptsStack {

	/**
	 * Nome da pilha de interceptadores.
	 */
	String name();

	/**
	 * Indica que o interceptador será executado depois de um determinado
	 * interceptador.
	 */
	Class<? extends InterceptorController> executeAfter() default InterceptorController.class;

	/**
	 * Parâmetros de configuração da pilha de interceptadores.
	 */
	Param[] params() default {};

	/**
	 * indica que o inteceptador é global.
	 */
	boolean isdefault() default false;

}
