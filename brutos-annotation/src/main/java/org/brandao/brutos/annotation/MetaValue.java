package org.brandao.brutos.annotation;

/**
 * Representa o tipo que uma propriedade pode assumir.
 * 
 * @author Brandao
 *
 */
public @interface MetaValue {
	
	/**
	 * Identificação do tipo.
	 */
	String name();

	/**
	 * Classe que representa o tipo. 
	 */
	Class<?> target();
	
}
