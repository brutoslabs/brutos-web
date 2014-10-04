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

package org.brandao.brutos.web.http;

import java.io.File;

/**
 * Representa um arquivo em uma requisição HTTP.
 * 
 * @author Afonso Brandao
 */
public interface UploadedFile {

    /**
     * Obtém o arquivo.
     * @return Arquivo.
     */
    File getFile();

    /**
     * Define o arquivo.
     * @param file Arquivo.
     */
    void setFile(File file);

    /**
     * Obtém o tipo de dados do arquivo.
     * @return Tipo de dados do arquivo.
     */
    String getContentType();

    /**
     * Define o tipo de dados do arquivo.
     * @param contentType Tipo de dados do arquivo.
     */
    void setContentType(String contentType);

    /**
     * Obtém o nome do arquivo original.
     * @return Nome do arquivo original.
     */
    String getFileName();

    /**
     * Define o nome original do arquivo.
     * @param fileName Nome original do arquivo.
     */
    void setFileName(String fileName);
    
}
