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
 * Representa uma requisi��o.
 * 
 * @author Afonso Brandao
 */
public interface MvcRequest {

    /**
     * Obt�m um valor.
     * @param name Identifica��o
     * @return Valor.
     */
    public Object getValue( String name );

    /**
     * Obt�m uma propriedade.
     * @param name Identifica��o.
     * @return Propriedade.
     */
    public Object getProperty( String name );

    /**
     * Obt�m o stream da requisi��o.
     * @return Stream.
     * @throws IOException Lan�ado caso ocorra algum problema ao obter o stream.
     */
    public InputStream getStream() throws IOException;

    /**
     * Tipo da requisi��o. Normalmente usado em aplica��es web.
     * @return Tipo.
     */
    public String getType();

    /**
     * Obt�m o tamanho da requisi��o. Normalmente usado em aplica��es web.
     * @return Tamanho.
     */
    public int getLength();

    /**
     * Obt�m a codificado da requisi��o. Normalmente usado
     * em aplica��es web.
     * @return Codifica��o.
     */
    public String getCharacterEncoding();

    /**
     * Obt�m a localidade. Normalmente usado em aplica��es web.
     * @return Localidade.
     */
    public Locale getLocale();

}
