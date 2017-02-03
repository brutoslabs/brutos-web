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

import org.brandao.brutos.BrutosConstants;
import org.brandao.brutos.annotation.configuration.MetaValuesDefinition;

/**
 * Permite criar mais de um mapeamento para uma entidade.
 * 
 * <pre>
 * Ex:
 * 
 * &#064;Controller("/controller")
 * public class PropertyController{
 * 
 *    public void saveProperty(
 *        &#064;Basic(bean="property")
 *        &#064;Any(
 *            metaBean=&#064;Basic(bean="property_type")
 *            metaValues={
 *                &#064;MetaValue(name="Decimal", target=DecimalProperty.class),
 *                &#064;MetaValue(name="Set”, target=SetProperty.class)
 *            }
 *        )
 *        Property property){
 *        ...
 *    }
 * }
 * 
 * public interface Property{
 * 
 *     Object getName();
 * 
 * }
 * 
 * public abstract class AbstractProperty{
 *    
 *     private String name;
 * 
 *     public Stirng getName(){
 *         return this.name;
 *     }
 * 
 * }
 * 
 * public class DecimalProperty extends AbstractProperty{
 *     
 *     private int length;
 * 
 *     private int decimals;
 *     ...
 * }
 * 
 * public class SetProperty extends AbstractProperty {
 *     
 *     private List &#60;String&#62; values;
 *     ...
 * }
 *
 * </pre>
 * 
 * @author Brandao
 * 
 */
@Target({ ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Any {

	/**
	 * Mapeamento dos metadados.
	 */
	Basic metaBean();

	/**
	 * Tipo dos metadados.
	 */
	Class<?> metaType() default void.class;

	/**
	 * Usado se os metadados forem do tipo enum. Os valores estão descritos em
	 * {@link EnumerationType}.
	 */
	EnumerationType metaEnumerated() default EnumerationType.ORDINAL;

	/**
	 * Usado se os metadados forem do tipo {@link java.util.Date} ou
	 * {@link java.util.Calendar}. Deve seguir o padrão definido em
	 * {@link java.text.SimpleDateFormat}.
	 */
	String metaTemporal() default BrutosConstants.DEFAULT_TEMPORALPROPERTY;

	/**
	 * Especificação dos tipos de mapeamentos.
	 */
	MetaValue[] metaValues() default {};

	/**
	 * Permite, em tempo de execução, definir os tipos de mapeamento.
	 */
	Class<? extends MetaValuesDefinition> metaValuesDefinition() default MetaValuesDefinition.class;

	/**
	 * Define o uso de um tipo específico.
	 */
	Class<? extends org.brandao.brutos.type.Type> metaTypeDef() default org.brandao.brutos.type.Type.class;

}
