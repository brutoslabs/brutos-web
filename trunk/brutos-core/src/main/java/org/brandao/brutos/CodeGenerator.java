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
 * Responsável por gerenciar e prover as fábricas de proxy da aplicação. 
 * 
 * @author Brandao
 */
public interface CodeGenerator {
    
    /**
     * Aplica a configuração da aplicação.
     * @param properties Configuração da aplicação.
     */
    void configure( Properties properties );

    /**
     * Desliga o gerador de código e proxy.
     */
    void destroy();
    
    /**
     * Obtém e instancia quando necessário da fabrica de proxy de um determinado
     * componenete.
     * @param componentClass Componente.
     * @return Fábrica de proxy do componente.
     * @throws BrutosException Lançada se ocorrer algum problema ao tentar 
     * obter ou criar a fábrica de proxy do componente.
     */
    ProxyFactory getProxyFactory( Class componentClass )
            throws BrutosException;
    
}
