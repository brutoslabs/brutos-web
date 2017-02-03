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

import org.brandao.brutos.annotation.configuration.MetaValuesDefinition;

/**
 * Permite criar mais de um mapeamento para as chaves de uma coleção.
 * 
 * @author Brandao
 *
 */
@Target({ ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD,
		ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface AnyKeyCollection {

	/**
	 * Mapeamento dos metadados.
	 */
	Basic metaBean();

	/**
	 * Tipo dos metadados.
	 */
	Class<?> metaType();

	/**
	 * Especificação dos tipos de mapeamentos.
	 */
	MetaValue[] metaValues() default {};

	/**
	 * Permite, em tempo de execução, definir os tipos de mapeamento.
	 */
	Class<? extends MetaValuesDefinition> metaValuesDefinition() default MetaValuesDefinition.class;

}
