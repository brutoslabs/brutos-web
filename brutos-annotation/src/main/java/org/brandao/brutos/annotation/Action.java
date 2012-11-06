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
 * Define uma ação do controlador. Uma ação pode ser representada por um método. 
 * Esse método pode ter ou não parâmetros. Os parâmetros podem ser de tipo 
 * primitivo ou não. No caso de tipos não primitivos, podem ser criados 
 * mapeamentos para a definição de como os valores da requisição serão 
 * injetados nas propriedades do "bean". Além de ser possível a configuração 
 * de propriedades do tipo Enum e Date. Se o método retornar algum valor, 
 * este será processado e incluído na requisição, para posteriormente ser 
 * usada na visão. As exceções lançadas dentro do método podem alterar o 
 * fluxo lógico da aplicação.
 * 
 * <p>Também é possível definir uma ação sem a utilização da anotação.
 * Nesse caso, o nome do método terá que seguir a nomenclatura 
 * <b><code>&lt;nome-da-ação&gt;Action</code></b>.</p>
 * 
 * <pre>
 * Ex1:
 * public class TestController{
 * 
 *    &#064;Action
 *    public void root(){
 *       ...
 *    }
 * }
 * 
 * Ex2:
 * public class TestController{
 * 
 *    public void rootAction(){
 *       ...
 *    }
 * }
 * 
 * </pre>
 * 
 * @author Afonso Brandao
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Action {
    
    /**
     * Identificação da Ação.
     */
    String[] value() default {};
    
}
