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
 * Intercepta as ações de um controlador. Todo interceptador deve
 * implementar essa classe.
 * <pre>
 * Ex:
 * public class MyInterceptor implements Interceptor{
 *     ...
 * }
 * </pre>
 * @author Afonso Brandao
 */
public interface Interceptor {

    /**
     * Define a configuração inicial do interceptador.
     * @param props Configuração inicial
     */
    public void setProperties( Map<String,Object> props );

    /**
     * Veqifica se o interceptador está configurado.
     * @return Verdadeiro se o interceptador foi configurado,
     * caso contrário falso.
     */
    public boolean isConfigured();

    /**
     * Methodo executado na interceptação da ação.
     * @param stack Pilha de interceptadores.
     * @param handler Manipulador da requisição.
     * @throws InterceptedException Lançada caso ocorra algum problema
     * na execução do interceptador.
     */
    public void intercepted( InterceptorStack stack, InterceptorHandler handler ) throws InterceptedException;

    /**
     * Verifica se o interceptador será executado na ação interceptada.
     * @param handler Manipulador da requisição.
     * @return Verdadeiro se é permitido o acesso ao recurso interceptado,
     * caso contrário falso.
     */
    public boolean accept( InterceptorHandler handler );
    
}
