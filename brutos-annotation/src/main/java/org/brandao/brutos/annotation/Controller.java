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
 * Indica que uma classe é um controlador. Um controlador pode ter ações, com ou
 * sem métodos, várias identificações e uma ação padrão.
 * <p>
 * Uma ação pode ser representada por um método que pode ter ou não parâmetros
 * que podem ser objetos ou tipos primitivos. Se forem objetos, podem ser
 * criados mapeamentos para se definir como os valores de uma solicitação serão
 * injetados em suas propriedades. Se o método produzir um resultado, este será
 * processado e incluído na solicitação para posteriormente ser usada na visão.
 * </p>
 * <p>
 * As exceções lançadas dentro do método podem alterar o fluxo lógico da
 * aplicação.
 * </p>
 * 
 * <pre>
 * Ex1:
 * &#064;Controller(value="/index",defaultActionName="action1")
 * &#064;ActionStrategy(ActionStrategyType.PARAMETER)
 * public class Index{
 * 
 *    &#064;Action
 *    public void action1(){
 *       ...
 *    }
 * 
 *    &#064;Action
 *    public void action2(){
 *       ...
 *    }
 * 
 * }
 * </pre>
 * 
 * <b>Mapeamentos:</b>
 * 
 * <table border="1">
 * <tr>
 * <td><b>Ação</b></td>
 * <td><b>Método</b></td>
 * </tr>
 * <tr>
 * <td>/index</td>
 * <td>Index.action1()</td>
 * </tr>
 * <tr>
 * <td>/index?invoke=action1</td>
 * <td>Index.action1()</td>
 * </tr>
 * <tr>
 * <td>/index?invoke=action2</td>
 * <td>Index.action2()</td>
 * </tr>
 * </table>
 * 
 * <pre>
 * Ex2:
 * &#064;Controller(value="/index/{invoke}")
 * &#064;ActionStrategy(ActionStrategyType.PARAMETER)
 * public class Index{
 * 
 *    &#064;Action
 *    public void action1(){
 *       ...
 *    }
 * 
 *    &#064;Action
 *    public void action2(){
 *       ...
 *    }
 * 
 * }
 * </pre>
 * 
 * <b>Mapeamentos:</b>
 * 
 * <table border="1">
 * <tr>
 * <td><b>Ação</b></td>
 * <td><b>Método</b></td>
 * </tr>
 * <tr>
 * <td>/index/action1</td>
 * <td>Index.action1()</td>
 * </tr>
 * <tr>
 * <td>/index/action2</td>
 * <td>Index.action2()</td>
 * </tr>
 * </table>
 * 
 * <pre>
 * Ex3:
 * &#064;Controller
 * &#064;ActionStrategy(ActionType.DETACHED)
 * public class Index{
 * 
 *    &#064;Action("/index/action1")
 *    public void action1(){
 *       ...
 *    }
 * 
 *    &#064;Action("/index/action2")
 *    public void action2(){
 *       ...
 *    }
 * 
 * }
 * </pre>
 * 
 * <b>Mapeamentos:</b>
 * 
 * <table border="1">
 * <tr>
 * <td><b>Ação</b></td>
 * <td><b>Método</b></td>
 * </tr>
 * <tr>
 * <td>/index/action1</td>
 * <td>Index.action1()</td>
 * </tr>
 * <tr>
 * <td>/index/action2</td>
 * <td>Index.action2()</td>
 * </tr>
 * </table>
 * 
 * <pre>
 * Ex4:
 * &#064;Controller("/index")
 * &#064;ActionStrategy(ActionType.COMPLEMENT)
 * public class Index{
 * 
 *    &#064;Action("/action1")
 *    public void action1(){
 *       ...
 *    }
 * 
 *    &#064;Action("/action2")
 *    public void action2(){
 *       ...
 *    }
 * 
 * }
 * </pre>
 * 
 * <b>Mapeamentos:</b>
 * 
 * <table border="1">
 * <tr>
 * <td><b>Ação</b></td>
 * <td><b>Método</b></td>
 * </tr>
 * <tr>
 * <td>/index/action1</td>
 * <td>Index.action1()</td>
 * </tr>
 * <tr>
 * <td>/index/action2</td>
 * <td>Index.action2()</td>
 * </tr>
 * </table>
 * 
 * @author Afonso Brandao
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Controller {

	/**
	 * Ação que será executada por padrão.
	 */
	String defaultActionName() default "";

	/**
	 * Nome do controlador. O nome do controlador é o mesmo da instância no
	 * container IoC.
	 */
	String name() default "";

	/**
	 * Identificação do controlador.
	 */
	String[] value() default {};

	/**
	 * Parâmetro que indica qual acção será executada.
	 */
	String actionId() default "invoke";

}
