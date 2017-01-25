package org.brandao.brutos.annotation;

/**
 * Especifica um tipo de mapeamento.
 * 
 * @author Brandao
 *
 */
public @interface MetaValue {
	
	/**
	 * Nome do mapeamento.
	 */
	String name();

	/**
	 * Classe alvo do mapeamento. 
	 */
	Class<?> target();
	
}
