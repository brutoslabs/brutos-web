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

package org.brandao.brutos;

import java.util.*;
import org.brandao.brutos.mapping.Interceptor;

/**
 * Classe usada para criar interceptadores ou pilhas de interceptadores.
 * O uso de interceptadores é necessário quando precisamos executar tarefas
 * antes e/ou depois do controlador ser executado, normalmente usado para
 * controle de acesso, validação de dados, controle de transação e geração
 * de log.
 * <p>É possível trabalhar com um ou mais interceptadores, podendo definir
 * quais recursos serão interceptados, a ordem com que serão executados e os
 * parâmetros necessários para a sua configuração.</p>
 * <p>Sua instância é controlada pelo container IOC, podendo assim, receber a
 * injeção por construtor ou método.</p>
 *
 * <pre>
 * Ex:
 *
 * public class MyInterceptor extends AbstractInterceptor{
 *
 *     public void intercepted( InterceptorStack stack,
 *        InterceptorHandler handler ) throws InterceptedException{
 *         ...
 *     }
 *
 * }
 * interceptorManager
 *     .addInterceptor( "myInterceptorName", MyInterceptor.class, false );
 * </pre>
 * 
 * <pre>
 * Ex2:
 * 
 * interceptorManager
 *     .addInterceptor( "myInterceptorName", MyInterceptor.class, false );
 * interceptorManager
 *     .addInterceptorStack( "myStack", false )
 *     .addInterceptor( "myInterceptorName" );
 * </pre>
 *
 * @author Afonso Brandao
 */
public interface InterceptorManager {
    
    /**
     * Cria uma pilha de interceptadores com uma determinada identificação.
     * @param name Identificação da pilha de interceptadores.
     * @param isDefault Se verdadeiro, todos os controladores e suas ações serão
     * interceptadas.
     * @return Construtor da pilha de interceptadores.
     */
    InterceptorStackBuilder addInterceptorStack( String name, boolean isDefault );
    
    /**
     * Cria um interceptador com uma determinada identificação.
     * @param name Identificação do interceptador.
     * @param isDefault Se verdadeiro, todos os controladores e suas ações serão
     * interceptadas.
     * @return Construtor do interceptador.
     */
    InterceptorBuilder addInterceptor( String name, Class<?> interceptor, boolean isDefault );

    /**
     * Obtém um interceptador a partir do nome.
     * @param name Identificação do interceptador.
     * @return Mapeamento.
     */
    Interceptor getInterceptor( String name );

    /**
     * Verifica se existe um determinado interceptor
     * @param name Nome do interceptor.
     * @return Verdadeiro se existir um interceptor. Caso contrário, falso.
     */
    boolean containsInterceptor(String name);
    
    /**
     * Obtém um interceptador a partir de sua classe.
     * @param clazz Classe do interceptador.
     * @return Mapeamento.
     */
    Interceptor getInterceptor( Class<?> clazz );

    /**
     * Verifica se existe um determinado interceptor
     * @param clazz Nome do interceptor.
     * @return Verdadeiro se existir um interceptor. Caso contrário, falso.
     */
    boolean containsInterceptor(Class<?> clazz);
    
    /**
     * Obtém os interceptadores globais.
     * @return Interceptadores globais.
     */
    List<Interceptor> getDefaultInterceptors();
    
    /**
     * Define o gestor de interceptador associado ao atual.
     * @param parent Gestor de interceptador.
     */
    void setParent(InterceptorManager parent);
    
    /**
     * Obtém o gestor de interceptador associado ao atual.
     * @return Gestor de interceptador.
     */
    InterceptorManager getParent();
    
    
}
