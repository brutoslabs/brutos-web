/*
 * Brutos Web MVC http://brutos.sourceforge.net/
 * Copyright (C) 2009 Afonso Brandao. (afonso.rbn@gmail.com)
 *
 * This library is free software. You can redistribute it 
 * and/or modify it under the terms of the GNU General Public
 * License (GPL) version 3.0 or (at your option) any later 
 * version.
 * You may obtain a copy of the License at
 * 
 * http://www.gnu.org/licenses/gpl.html 
 * 
 * Distributed WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied.
 *
 */

package org.brandao.brutos.interceptor;

import java.util.Map;

/**
 * Intercepta as a��es de um controlador. Todo interceptador deve
 * implementar essa classe.
 * <pre>
 * Ex:
 * public class MyInterceptor implements Interceptor{
 *     ...
 * }
 * </pre>
 * @deprecated 
 * @author Afonso Brandao
 */
public interface Interceptor {

    /**
     * Define a configura��o inicial do interceptador.
     * @param props Configura��o inicial
     */
    public void setProperties( Map props );

    /**
     * Verifica se o interceptador foi configurado.
     * @return Verdadeiro se o interceptador foi configurado,
     * caso contr�rio falso.
     */
    public boolean isConfigured();

    /**
     * Methodo executado na intercepta��o da a��o.
     * @param stack Pilha de interceptadores.
     * @param handler Manipulador da requisi��o.
     * @throws InterceptedException Lan�ada caso ocorra algum problema
     * na execu��o do interceptador.
     */
    public void intercepted( InterceptorStack stack, InterceptorHandler handler ) throws InterceptedException;

    /**
     * Verifica se o interceptador ser� executado na a��o interceptada.
     * @param handler Manipulador da requisi��o.
     * @return Verdadeiro se � permitido o acesso ao recurso interceptado,
     * caso contr�rio falso.
     */
    public boolean accept( InterceptorHandler handler );
    
}
