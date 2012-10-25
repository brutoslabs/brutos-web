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

import java.io.OutputStream;
import java.util.Locale;

/**
 * Representa uma resposta.
 * 
 * @author Afonso Brandao
 */
public interface MvcResponse {

    /**
     * Processa a resposta a partir de um objeto.
     * @param object Objeto.
     */
    public void process( Object object );

    /**
     * Processa a resposta a partir de um stream.
     * @return Stream.
     */
    public OutputStream processStream();

    /**
     * Inclui uma nova informa��o na resposta. Normalmente usado em aplica��es
     * web, equilave ao addHeader(...)
     * @param name Identifica��o.
     * @param value Valor.
     */
    public void setInfo( String name, String value );

    /**
     * Obt�m o tipo da resposta. Normalmente usado em aplica��es web.
     * @return Tipo.
     */
    public String getType();

    /**
     * Obt�m o tamanho da resposta. Normalmente usado em aplica��es web.
     * @return Tamanho.
     */
    public int getLength();

    /**
     * Obt�m a codifica��o da resposta. Normalmente usado em aplica��es
     * web.
     * @return Codifica��o.
     */
    public String getCharacterEncoding();

    /**
     * Obt�m a localidade. Normalmente usado em aplica��es web.
     * @return Localidade.
     */
    public Locale getLocale();

    /**
     * Define a localidade. Normalmente usado em aplica��es web.
     * @param value Localidade.
     */
    public void setLocale( Locale value );

    /**
     * Define o tipo da resposta. Normalmente usado em aplica��es web.
     * @param value Tipo.
     */
    public void setType( String value );

    /**
     * Define o tamanho da resposta. Normalmente usado em aplica��es web.
     * @param value Tamanho.
     */
    public void setLength( int value );

    /**
     * Define a codifica��o. Normalmente usado em aplica��es web.
     * @param value Codifica��o.
     */
    public void setCharacterEncoding( String value );


}
