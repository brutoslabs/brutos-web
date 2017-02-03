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
 * Define a visão de um determinado controlador ou ação.
 * 
 * <pre>
 * Ex:
 * &#064;Controller
 * &#064;View("/jsp/index/index.jsp")
 * public class Index{
 * 
 *    &#064;View("/jsp/index/myAction.jsp")
 *    public void myAction(
 *        ...
 *    }
 * 
 *    public void my2Action(
 *        ...
 *    }
 * 
 *    ...
 * }
 * </pre>
 * 
 * @author Brandao
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface View {

	/**
	 * Visão.
	 */
	String value() default "";

	/**
	 * Define como o fluxo de execução será direcionado para a visão. Os valores
	 * estão descritos em {@link DispatcherType}.
	 */
	String dispatcher() default "";

	/**
	 * Determina a renderização, ou não, da vista. Se verdadeiro a visão será
	 * renderizada, caso contrário não.
	 */
	boolean rendered() default true;

	/**
	 * Define se a vista é real ou não. Se verdadeiro, a vista é real, caso
	 * contrário ela será resolvida.
	 */
	boolean resolved() default false;

}
