package org.brandao.brutos.annotation.web;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.servlet.http.HttpServletResponse;

import org.brandao.brutos.annotation.DispatcherType;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ResponseErrors {

	/**
	 * Código de resposta da solicitação.
	 * @see HttpServletResponse#sendError(int, String)
	 */
	int	code() default 0;

	/**
	 * Alias do {@link #code()}
	 * @see HttpServletResponse#sendError(int, String)
	 */
	int	value() default 0;
	
	/**
	 * Descrição do erro.
	 * @see HttpServletResponse#sendError(int, String)
	 */
	String	reason() default "";
	
    /**
     * Visão da exceção. Se não for informada,
     * será PREFIX_VIEW + CONTROLLER_NAME + SEPARATOR_VIEW
     * [+ ACTION_NAME + SEPARATOR_VIEW] + EXCEPTION_NAME. 
     * Somente será usado o ACTION_NAME se for especificada 
     * na ação.
     */
    String view() default "";
    
    /**
     * Nome da exceção. Se não informado, será assumido
     * <code>exception</code>.
     */
    String name() default "exception";

    /**
     * Define como o fluxo de execução será direcionado para a visão.
     * Os valores estão descritos em {@link DispatcherType}.
     */
    String dispatcher() default "";
    
    /**
     * Determina a renderização, ou não, da vista. 
     * Se verdadeiro a visão será renderizada, caso contrário não.
     */
    boolean rendered() default true;
    
    /**
     * Desabilita a interceptação da exceção.
     * Se verdadeiro, a exceção será interceptada e processada, 
     * caso contrário não.
     */
    boolean enabled() default true;
    
    /**
     * Define se a vista é real ou não. 
     * Se verdadeiro, a vista é real, caso contrário ela 
     * será resolvida.
     */
    boolean resolved() default false;

	ResponseError[] exceptions() default {};
	
}
