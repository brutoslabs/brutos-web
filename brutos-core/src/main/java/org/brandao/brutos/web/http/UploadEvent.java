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

package org.brandao.brutos.web.http;

/**
 * 
 * @author Brandao
 */
public interface UploadEvent {

    /**
     * Verifica a existência de arquivos no formulário.
     *
     * @return Verdadeiro se existir arquivos no formulário, caso contrário
     * falso.
     */
    public boolean isMultipart();

    /**
     * Obtém o tamanho total da requisição.
     *
     * @return Tamanho total da requisição.
     */
    public long getContentLength();

    /**
     * Obtém a quantidade de bytes carregados até o momento.
     *
     * @return Quantidade de bytes carregados.
     */
    public long getBytesRead();

}
