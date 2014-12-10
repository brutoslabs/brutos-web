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

import org.brandao.brutos.type.*;
import java.util.*;

/**
 * Faz o gerenciamento dos tipos da aplicação.
 * 
 * @author Brandao
 */
public interface TypeManager {
    
    /**
     * Registra um novo tipo.
     * @param factory Fábrica do tipo.
     */
    void register(TypeFactory factory);

    /**
     * Remove um tipo a partir de sua classe.
     * @param type Classe do tipo.
     */
    void remove(Class type);
    
    /**
     * Remove um tipo a apartir sua fábrica.
     * @param factory Fábrica do tipo.
     */
    void remove(TypeFactory factory);

    /**
     * Obtém todos os tipos registrados.
     * @return Lista contendo todos os tipos registrados.
     */
    List getAllTypes();
    
    /**
     * Verifica se a classe representa um tipo padrão.
     * @param clazz Classe do tipo.
     * @return Verdadeiro se for um tipo padrão, caso contrário falso.
     */
    boolean isStandardType(Class clazz);

    /**
     * Obtém um tipo a partir de sua classe.
     * @param classType Classe do tipo. Pode ser uma {@link java.lang.Class} ou 
     * {@link java.lang.reflect.Type}.
     * @return Tipo.
     */
    Type getType(Object classType);

    /**
     * Obtém a fábrica do tipo a partir de sua classe.
     * @param classType Classe do tipo. Pode ser uma {@link java.lang.Class} ou 
     * {@link java.lang.reflect.Type}.
     * @return Fábrica do tipo.
     */
    TypeFactory getTypeFactory(Object classType);
    
    /**
     * Obtém o tipo a partir de sua classe.
     * @param classType Classe do tipo. Pode ser uma {@link java.lang.Class} ou 
     * {@link java.lang.reflect.Type}.
     * @param enumType Tipo do mapeamento de Enum.
     * @param pattern Formato de uma data.
     * @return Tipo.
     */
    Type getType(Object classType, EnumerationType enumType, String pattern);

}
