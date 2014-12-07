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

package org.brandao.brutos.type;

import java.io.IOException;
import org.brandao.brutos.MvcResponse;

/**
 *
 * @author Afonso Brandao
 */
public interface Type {

    /**
     * Converte o valor para um tipo predefinido.
     * @param value Valor a ser convertido.
     * @return Valor convertido.
     */
    Object convert(Object value);

    /**
     * Renderiza o valor.
     * @param response Resposta da requisição.
     * @param value Valor a ser renderizado.
     * @throws IOException 
     */
    void show(MvcResponse response, Object value) throws IOException;
    
    /**
     * Obtém o tipo da classe.
     * @return Tipo da classe.
     */
    Class getClassType();

    /**
     * Define o tipo da classe.
     * @param value Tipo da classe.
     */
    void setClassType(Class value);
    
    boolean isAlwaysRender();
    
}
