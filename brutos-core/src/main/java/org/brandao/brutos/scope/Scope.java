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

package org.brandao.brutos.scope;

/**
 * Permite o acesso aos escopos de uma aplicação.
 * 
 * @author Brandao
 */
public interface Scope {
    
    /**
     * Adiciona um objeto ao escopo.
     * @param name Identificação do objeto.
     * @param value Objeto.
     */
    void put( String name, Object value );
    
    /**
     * Obtém um objeto. 
     * @param name Identificação do objeto.
     * @return Objeto.
     */
    Object get( String name );

    /**
     * Obtém um objeto que representa uma coleção.
     * @param name Identificação do objeto.
     * @return Objeto.
     */
    Object getCollection( String name );

    /**
     * Remove o objeto do escopo.
     * @param name Identificação do objeto.
     */
    void remove( String name );
    
}
