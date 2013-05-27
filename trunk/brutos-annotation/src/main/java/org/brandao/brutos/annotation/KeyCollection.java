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
 * Especifica as chaves de uma coleção de "beans".
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
     * Identificação do "bean" que será injetado.
     * Caso seja omitido, será usado o termo "key".
     */
    String bean() default "";
    
    /**
     * Escopo do valor a ser injetado. Os valores estão 
     * descritos em <a href="ScopeType.html">ScopeType</a>
     * 
     */
    String scope() default "param";

    /**
     * Usado em tipos não primitivos. Força o uso do mapeamento do "bean".
     */
    boolean useMapping() default false;
    
    /**
     * Class alvo do mapeamento.
     */
    Class<?> target() default void.class;
    
    /**
     * Usado em tipos Enum. Os valores estão 
     * descritos em <a href="EnumerationType.html">EnumerationType</a>
     */
    EnumerationType enumerated() default EnumerationType.ORDINAL;
    
    /**
     * Usado em tipos <a href="http://java.sun.com/j2se/1.5/docs/api/java/util/Date.html">Date</a></code>
     * e <a href="http://java.sun.com/j2se/1.5/docs/api/java/util/Calendar.html">Calendar</a>.
     * Deve seguir o padrão definido em  
     * <a href="http://java.sun.com/j2se/1.5/docs/api/java/text/SimpleDateFormat.html">SimpleDateFormat</a>.
     */
    String temporal() default BrutosConstants.DEFAULT_TEMPORALPROPERTY;
    
    /**
     * Define o uso de um tipo específico de dados.
     */
    Class<? extends org.brandao.brutos.type.Type> type() default org.brandao.brutos.type.Type.class;
    
}
