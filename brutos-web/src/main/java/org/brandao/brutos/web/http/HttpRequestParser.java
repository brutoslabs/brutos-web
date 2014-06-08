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

import java.io.IOException;
import java.util.Properties;

/**
 * Analisador de solicitações HTTP.
 *
 * @author Brandao
 */
public interface HttpRequestParser {

    /**
     * Verifica se na requisição existe arquivos.
     * @param request Requisição.
     * @param uploadListener Ouvinte da requisição.
     * @return Verdadeiro se existir arquivos, caso contrário falso.
     */
    boolean isMultipart( BrutosRequest request, 
            UploadListener uploadListener ) throws IOException;
    
    /**
     * Analisa uma requisição que contém arquivos.
     * @param request Requisição.
     * @param config Configuração.
     * @param uploadListener Ouvinte da requisição.
     */
    void parserMultipart( BrutosRequest request, Properties config,
            UploadListener uploadListener ) throws IOException;

    /**
     * Analisa uma requisição de acordo com o tipo do conteudo.
     * @param request Requisição.
     * @param contentType Tipo do conteudo.
     */
    void parserContentType( BrutosRequest request, 
            String contentType ) throws IOException;

    /**
     * Obtém o observador da requisição.
     * @param request Requisição.
     * @return Observador.
     */
    UploadEvent getUploadEvent( BrutosRequest request );
    
}
