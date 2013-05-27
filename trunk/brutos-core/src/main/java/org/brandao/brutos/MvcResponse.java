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
 * Define um objeto que auxilia o controlador a enviar uma resposta ao cliente. 
 * Para o envio de dados por meio de um fluxo binário use o método processStream(). 
 * Para serem enviados caracteres, use o objeto PrintWriter obtido no método getWriter(). 
 * Se a resposta for a mistura de dados binários e texto, use o fluxo binário e 
 * controle manualmente a codificação dos caracteres.
 * <p>A codificação da resposta pode ser definida usando o 
 * método setCharacterEncoding(java.lang.String).</p>
 * 
 * @author Afonso Brandao
 */
public interface MvcResponse {

    /**
     * Envia um determinado objeto ao cliente.
     * @param object Objeto.
     */
    public void process( Object object );

    /**
     * Obtém um objeto OutputStream usado para a gravação dos dados binários da
     * resposta.
     * @return Objeto OutputStream.
     */
    public OutputStream processStream();

    /**
     * Inclui uma nova informação na resposta. Normalmente usado em aplicações
     * web. Ele equivale ao método addHeader(java.land.String.java.lang.String) da 
     * classe HttpServletResponse.
     * @param name Identificação.
     * @param value Valor.
     */
    public void setInfo( String name, String value );

    /**
     * Obtém o tipo da resposta. Normalmente usado em aplicações web. Ele equivale ao
     * método getContentType() da classe ServletResponse.
     * @return Tipo.
     */
    public String getType();

    /**
     * Obtém o tamanho do corpo da resposta. Normalmente usado em aplicações web.
     * Ele equivale ao método getContentLength() da classe ServletResponse.
     * @return Valor inteiro que indica o tamanho do conteudo que será enviado ao
     * cliente.
     */
    public int getLength();

    /**
     * Obtém a codificação usada da resposta que será enviada ao cliente. 
     * Normalmente usado em aplicações web. Equivale ao método getCharacterEncoding()
     * da calsse ServletResponse.
     * @return String que indica a condificação usada. Ela é definida pelo IANA
     * (http://www.iana.org/assignments/character-sets).
     */
    public String getCharacterEncoding();

    /**
     * Obtém um objeto que representa uma região georáfica, política ou cultural 
     * da resposta.
     * @return objeto que representa uma região georáfica, política ou cultural.
     */
    public Locale getLocale();

    /**
     * Define a região georáfica, política ou cultural da resposta.
     * @param value Objeto que representa uma região georáfica, política ou cultural.
     */
    public void setLocale( Locale value );

    /**
     * Define o tipo da resposta. Normalmente usado em aplicações web. Ele equivale ao
     * método setContentType(java.lang.String) da classe ServletResponse.
     * @return Tipo.
     */
    public void setType( String value );

    /**
     * Define o tamanho do corpo da resposta. Normalmente usado em aplicações web.
     * Ele equivale ao método setContentLength(int) da classe ServletResponse.
     * @param value Tamanho.
     */
    public void setLength( int value );

    /**
     * Define a codificação usada da resposta que será enviada ao cliente. 
     * Normalmente usado em aplicações web. Equivale ao método setCharacterEncoding(java.lang.String)
     * da calsse ServletResponse.
     * @param value String que indica a condificação usada. Ela é definida pelo IANA
     * (http://www.iana.org/assignments/character-sets).
     */
    public void setCharacterEncoding( String value );

}
