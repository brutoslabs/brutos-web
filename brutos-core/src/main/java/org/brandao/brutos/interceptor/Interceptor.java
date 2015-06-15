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
 * Intercepta as ações de um controlador. Todo interceptador deve
 * implementar essa interface.
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
     * Define a configuração inicial do interceptador.
     * @param props Configuração inicial.
     */
    public void setProperties( Map<String,Object> props );

    /**
     * Verifica se o interceptador foi configurado.
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
     * @return Verdadeiro se for permitido o acesso ao recurso interceptado,
     * caso contrário falso.
     */
    public boolean accept( InterceptorHandler handler );
    
}
