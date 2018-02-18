package org.brandao.brutos.annotation.web;

import org.brandao.brutos.annotation.ActionStrategyType;

/**
 * Descreve as estratégias de mapeamento de uma ação.
 * 
 * @author Afonso Brandao
 */
public interface WebActionStrategyType extends ActionStrategyType{

	/**
	 * A ação é mapeada em um nível inferior ao do controlador.
	 */
	public static final String HIERARCHY = "HIERARCHY";

	/**
	 * A ação é mapeada no mesmo nível do controlador.
	 */
	public static final String DETACHED = "DETACHED";

	/**
	 * A ação é mapeada como um valor do cabeçalho.
	 */
	public static final String HEADER = "HEADER";
	
}
