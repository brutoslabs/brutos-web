/*
 * Brutos Web MVC http://www.brutosframework.com.br/
 * Copyright (C) 2009 Afonso Brandao. (afonso.rbn@gmail.com)
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
 * Define um controlador. Um controlador pode ter ações, ações com
 * ou sem método associado (executor), várias identificações e uma
 * ação padrão.
 * <p>Uma ação pode ser representada por um método. Esse método pode ter ou não 
 * parâmetros. Os parâmetros podem ser de tipo primitivo ou não. No caso 
 * de tipos não primitivos, podem ser criados mapeamentos para a definição de como 
 * os valores da requisição serão injetados nas propriedades do "bean". 
 * Além de ser possível a configuração de propriedades do tipo Enum e Date. 
 * Se o método retornar algum valor, este será processado e incluído na 
 * requisição, para posteriormente ser usada na visão. As exceções lançadas 
 * dentro do método podem alterar o fluxo lógico da aplicação.</p>
 * 
 * <p>Também é possível definir um controlador sem a necessidade da utilização
 * de anotação. Nesse caso, o nome da classe tem que seguir a nomenclatura
 * <b><code>&lt;nome-do-controlador&gt;Controller</code></b>.</p>
 * 
 * <p>Uma ação padrão é aquela ação que será executada nos casos em que 
 * não for identificada uma ação.</p>
 * 
 * <p>Em aplicações web, quando o controlador possuir identificação, as 
 * identificações das ações não serão consideradas como URIs e terão que ser 
 * informadas como um parâmetro.</p>
 * <pre>
 * Ex1:
 * &#064;Controller(id="/index",defaultActionName="action1")
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
 * 
 * Ex2:
 * &#064;Controller(id="/index/{invoke}")
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
 * 
 * Ex3:
 * &#064;Controller(id="/index{invoke}")
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
 * 
 * Ex4:
 * &#064;Controller
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
 * @author Afonso Brandao
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Controller{

    /**
     * Define a ação que será executada por padrão.
     */
    String defaultActionName() default "";

    /**
     * Nome do controlador.
     * O nome do controlador é a identificação do mesmo no container IoC.
     */
    String name() default "";

    /**
     * Identificação do controlador.
     * <p>Em aplicações web, quando o controlador possui identificação, as 
     * identificações das ações não são consideradas como URIs e terão que ser 
     * informadas como um parâmetro.</p>
     */
    String[] id() default {};

    /**
     * Parâmetro da requisição usada para a identificação da ação a 
     * ser executada.
     */
    String actionId() default "invoke";

}
