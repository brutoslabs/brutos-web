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

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

/**
 * Representa uma requisição.
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
     * Obtém o stream da requisição.
     * @return Stream.
     * @throws IOException Lançado caso ocorra algum problema ao obter o stream.
     */
    public InputStream getStream() throws IOException;

    /**
     * Tipo da requisição. Normalmente usado em aplicações web.
     * @return Tipo.
     */
    public String getType();

    /**
     * Obtém o tamanho da requisição. Normalmente usado em aplicações web.
     * @return Tamanho.
     */
    public int getLength();

    /**
     * Obtém a codificado da requisição. Normalmente usado
     * em aplicações web.
     * @return Codificação.
     */
    public String getCharacterEncoding();

    /**
     * Obtém a localidade. Normalmente usado em aplicações web.
     * @return Localidade.
     */
    public Locale getLocale();

}
