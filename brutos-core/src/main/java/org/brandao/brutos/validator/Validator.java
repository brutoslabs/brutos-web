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

package org.brandao.brutos.validator;

import java.util.Properties;

/**
 * Define o objeto que faz a validação dos dados da requisição.
 * 
 * @author Afonso Brandao
 */
public interface Validator {

    /**
     * Aplica a configuração no validador.
     * @param config Configuração.
     */
    void configure( Properties config );

    /**
     * Obtém a validação do validador.
     * @return Configuração.
     */
    Properties getConfiguration();
    
    /**
     * Faz a validação de um objeto.
     * 
     * @param source Origem do valor.
     * @param value Valor a ser validado.
     * @throws ValidatorException Lançado caso o valor seja considerado inválido.
     */
    void validate(Object source, Object value) throws ValidatorException;

}
