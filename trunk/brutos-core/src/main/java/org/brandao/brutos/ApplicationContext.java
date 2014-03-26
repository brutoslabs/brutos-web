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
 * Classe que permite a configuração de uma aplicação.
 *
 * @author Afonso Brandao
 */
public interface ApplicationContext {

    /**
     * Destroi a aplicação.
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

    /**
     * Obtém a instância de um controlador.
     * @param clazz Classe que representa o controlador.
     * @return Controlador.
     */
    Object getController(Class clazz);
    
    /**
     * Obtém a instância de um bean a partir de sua classe.
     * @param clazz Classe que representa o bean.
     * @return Bean.
     */
    Object getBean(Class clazz);

    /**
     * Obtém a instância de um bean a partir de seu nome.
     * @param clazz Nome do bean.
     * @return Bean.
     */
    Object getBean(String name);
    
}
