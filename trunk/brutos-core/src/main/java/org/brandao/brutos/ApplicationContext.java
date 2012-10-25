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

import java.util.Properties;

/**
 * Classe que permite a configuração de uma aplicação. Com essa classe
 * é possível:
 * <ul>
 * <li>configurar interceptadores;</li>
 * <li>incluir novos controladores;</li>
 * <li>manipular os controladores;</li>
 * <li>manipular a resolução de controladores;</li>
 * <li>manipular a execução de ações;</li>
 * <li>determinar novas regras de validação.</li>
 * </ul>
 *
 * @author Afonso Brandao
 */
public interface ApplicationContext {

    /**
     * Inicia o processo de configuração da aplicação.
     */
    void configure();

    /**
     * Inicia o processo de configuração da aplicação.
     *
     * @param config Configuração.
     */
    void configure(Properties config);

    /**
     * Método invocado quando a aplicação é finalizada.
     */
    void destroy();

    /**
     * Obtém a configuração da aplicação.
     * @return Configuração da aplicação.
     */
    Properties getConfiguration();

    /**
     * Obtém o objeto responsável por receber a requisição do cliente.
     * @return Requisição.
     */
    MvcRequest getMvcRequest();

    /**
     * Obtém o objeto responsável por enviar a resposta ao cliente.
     * @return Resposta.
     */
    MvcResponse getMvcResponse();

    /**
     * Obtém os escopos da aplicação.
     * @return
     */
    Scopes getScopes();

    Object getController(Class clazz);
    
}
