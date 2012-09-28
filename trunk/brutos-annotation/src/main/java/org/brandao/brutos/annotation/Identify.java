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
 * Pode definir o parâmetro de uma ação, propriedade de um bean, 
 * propriedade de um controlador e o argumento de um construdor de um bean.
 * O item representado pode ser de tipo primitivo ou não. 
 * No caso de tipo não primitivo, pode ser criado um mapeamento 
 * para definir como os valores da requisição serão injetados nas 
 * propriedades do objeto. Além de ser possível a configuração de 
 * propriedades do tipo Enum e Date.
 * 
 * <pre>
 * Ex1:
 * public class MyController{
 * 
 *    public void myAction(
 *       &#064;Identify(bean="indice")
 *       int index){
 *       ...
 *    }
 * }
 * </pre>
 * 
 * <pre>
 * Ex2:
 * &#064;Bean
 * public class MyBean{
 *    
 *    &#064;Identify(bean="indice")
 *    private int index;
 * 
 * }
 * </pre>
 * 
 * <pre>
 * Ex3:
 * &#064;Bean
 * public class MyBean{
 *    
 *    private int index;
 * 
 *    &#064;Identify(bean="indice")
 *    public void setIndex(int value){
 *       this.index = value;
 *    }
 * 
 *    ...
 * }
 * </pre>

 * <pre>
 * Ex4:
 * &#064;Bean
 * public class MyBean{
 *    
 *    private int index;
 * 
 *    public MyBean(
 *         &#064;Identify(bean="indice")
 *         int value){
 *       this.index = value;
 *    }
 * 
 *    ...
 * }
 * </pre>
 * 
 * @author Brandao
 */

@Target({ElementType.METHOD,ElementType.PARAMETER,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Identify {
    
    /**
     * Identificação do bean que será injetado.
     * Caso seja omitido, será usado o nome da propriedade ou 
     * o nome genérico do item.
     * 
     */
    String bean() default "";
    
    /**
     * Escopo do valor a ser injetado. Os valores estão 
     * descritos em <a href="ScopeType.html">ScopeType</a>
     * 
     */
    String scope() default "param";

    /**
     * Usado em tipos não primitivos. Força o uso do mapeamento do bean.
     */
    boolean useMapping() default false;
    
}
