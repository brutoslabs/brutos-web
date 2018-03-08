package org.brandao.brutos.annotation;

/**
 * Descreve as estratégia a serem utilizadas para obter os dados de uma solicitação.
 * @author Brandao
 *
 */
public enum FetchTypes {

	/**
	 * Indica que o valor tem que ser disponibilizado imediatamente.
	 */
	EAGER,
	
	/**
	 * Indica que o valor tem que ser disponibilizado no primeiro acesso.
	 */
	LAZY;
	
}
