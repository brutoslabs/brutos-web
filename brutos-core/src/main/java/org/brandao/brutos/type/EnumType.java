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

import org.brandao.brutos.EnumerationType;

/**
 * Representa o tipo {@link java.lang.Enum}.
 * 
 * @author Brandao
 */
public interface EnumType extends Type{

    /**
     * Obtém o tipo do mapeamento do {@link java.lang.Enum}.
     * 
     * @return Tipo do mapeamento.
     */
    EnumerationType getEnumType();

    /**
     * Define o tipo do mapeamento do {@link java.lang.Enum}. 
     * Os valores válidos estão descritos em {@link org.brandao.brutos.EnumerationType}.
     * 
     * @param type Tipo do mapeamento.
     */
    void setEnumType(EnumerationType type);
    
    /**
     * Define o tipo da classe do {@link java.lang.Enum}.
     * 
     * @param classType Tipo da classe.
     */
    void setClassType( Class classType );
    
}
