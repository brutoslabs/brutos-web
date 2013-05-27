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

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

/**
 * Define um objeto que fornece informações do cliente 
 * para um controlador. Ele fornece os parâmetros, valores, atributos 
 * e o fluxo de entrada. Em aplicações web também fornece informações 
 * sobre a requisição HTTP.
 * 
 * @author Afonso Brandao
 */
public interface MvcRequest {

    /**
     * Obtém um valor.
     * @param name Identificação
     * @return Valor.
     */
    public Object getValue( String name );

    /**
     * Obtém uma propriedade.
     * @param name Identificação.
     * @return Propriedade.
     */
    public Object getProperty( String name );

    /**
     * Obté o fluxo de dados binários.
     * @return Stream.
     * @throws IOException Lançado caso ocorra algum problema ao obter o fluxo.
     */
    public InputStream getStream() throws IOException;

    /**
     * Obtém o tipo da requisição. Normalmente usado em aplicações web.
     * @return Tipo.
     */
    public String getType();

    /**
     * Obtém o tamanho da requisição. Normalmente usado em aplicações web.
     * @return Tamanho.
     */
    public int getLength();

    /**
     * Obtém a codificado dos dados enviados na requisição. Normalmente usado
     * em aplicações web.
     * @return Codificação.
     */
    public String getCharacterEncoding();

    /**
     * Obtém a localidade de origem da requisição. Normalmente usado em 
     * aplicações web.
     * @return Localidade.
     */
    public Locale getLocale();

}
