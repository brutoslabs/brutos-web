package org.brandao.brutos.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.brandao.brutos.annotation.configuration.MetaValuesDefinition;

/**
 * Permite mapear uma chave para mais de um tipo de entidade.
 * @author Brandao
 *
 */
@Target({ElementType.METHOD,ElementType.PARAMETER,ElementType.FIELD,ElementType.TYPE})
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
