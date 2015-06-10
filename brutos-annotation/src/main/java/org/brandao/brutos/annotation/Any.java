package org.brandao.brutos.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.brandao.brutos.BrutosConstants;
import org.brandao.brutos.annotation.configuration.MetaValuesDefinition;

/**
 * Define uma associação que permite mais de um tipo de bean.
 * 
 * @author Brandao
 * 
 */
@Target({ElementType.METHOD,ElementType.PARAMETER,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Any {

	/**
	 * Variável que representa as informações de meta dados.
	 */
	Basic metaBean() default @Basic;

	/**
	 * Tipo da variável que representa as informações de meta dados.
	 */
	Class<?> metaType() default String.class;

    /**
     * Usado em tipos enum. Os valores estão 
     * descritos em {@link EnumerationType}.
     */
    EnumerationType metaEnumerated() default EnumerationType.ORDINAL;
    
    /**
     * Usado em tipos {@link java.util.Date} e {@link java.util.Calendar}.
     * Deve seguir o padrão definido em {@link java.text.SimpleDateFormat}.
     */
    String metaTemporal() default BrutosConstants.DEFAULT_TEMPORALPROPERTY;
	
	/**
	 * Associa um valor de informação de meta dados a um bean.
	 * @return
	 */
	MetaValue[] metaValues() default {};

	/**
	 * Permite em tempo de execução associar um valor de informação de meta dados a um bean.
	 */
	Class<? extends MetaValuesDefinition> metaValuesDefinition() default MetaValuesDefinition.class;
	
}
