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

package org.brandao.brutos;

import java.util.Properties;

/**
 * Classe central que permite a configuração de um aplicativo. Com essa classe
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

}
