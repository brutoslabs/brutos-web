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
import org.brandao.brutos.BrutosConstants;

/**
 * Define a cheve de um elemento de uma coleção.
 * 
 * <pre>
 * Ex:
 * public class MyController{
 * 
 *    &#064;KeyCollection(bean="myKey")
 *    private Map&lt;String,BeanConstructorTest&gt; property;
 * 
 *    ...
 * 
 * }
 * </pre>
 * 
 * @author Brandao
 */
@Target({ElementType.METHOD,ElementType.PARAMETER,ElementType.FIELD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface KeyCollection {
    
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
    
    /**
     * Class alvo do mapeamento.
     */
    Class<?> target() default void.class;
    
    /**
     * Usado em tipos enum. Os valores estão 
     * descritos em <a href="EnumerationType.html">EnumerationType</a>
     */
    String enumerated() default BrutosConstants.DEFAULT_ENUMERATION_TYPE;
    
    /**
     * Usado em tipos Date e Calendar. Define o formato da data.
     */
    String temporal() default BrutosConstants.DEFAULT_TEMPORALPROPERTY;
    
    /**
     * Define o uso de um tipo específico de dados.
     */
    Class<? extends org.brandao.brutos.type.Type> type() default org.brandao.brutos.type.Type.class;
    
}
