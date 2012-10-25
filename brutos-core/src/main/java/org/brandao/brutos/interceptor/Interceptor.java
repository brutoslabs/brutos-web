/*
 * Brutos Web MVC http://www.brutosframework.com.br/
 * Copyright (C) 2009-2012 Afonso Brandao. (afonso.rbn@gmail.com)
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
