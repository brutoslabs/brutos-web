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
     * Inclui uma nova informação na resposta. Normalmente usado em aplicações
     * web, equilave ao addHeader(...)
     * @param name Identificação.
     * @param value Valor.
     */
    public void setInfo( String name, String value );

    /**
     * Obtém o tipo da resposta. Normalmente usado em aplicações web.
     * @return Tipo.
     */
    public String getType();

    /**
     * Obtém o tamanho da resposta. Normalmente usado em aplicações web.
     * @return Tamanho.
     */
    public int getLength();

    /**
     * Obtém a codificação da resposta. Normalmente usado em aplicações
     * web.
     * @return Codificação.
     */
    public String getCharacterEncoding();

    /**
     * Obtém a localidade. Normalmente usado em aplicações web.
     * @return Localidade.
     */
    public Locale getLocale();

    /**
     * Define a localidade. Normalmente usado em aplicações web.
     * @param value Localidade.
     */
    public void setLocale( Locale value );

    /**
     * Define o tipo da resposta. Normalmente usado em aplicações web.
     * @param value Tipo.
     */
    public void setType( String value );

    /**
     * Define o tamanho da resposta. Normalmente usado em aplicações web.
     * @param value Tamanho.
     */
    public void setLength( int value );

    /**
     * Define a codificação. Normalmente usado em aplicações web.
     * @param value Codificação.
     */
    public void setCharacterEncoding( String value );


}
