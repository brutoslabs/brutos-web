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

/**
 * Representa o resutlado de uma ação.
 * 
 * @author Brandao
 */
public interface ResultAction {
    
    /**
     * Usa a vista padrão da ação
     */
    ResultParam use();

    /**
     * Define a vista da ação a partir do nome.
     * 
     * @param view Nome da vista.
     */
    ResultParam use(String view);

    /**
     * Define a vista da ação.
     * 
     * @param view Vista.
     * @param resolved Define se a vista informada é real. Se verdadeiro, a vista
     * é real. Caso contrário, a vista será resolvida.
     */
    ResultParam use(String view, boolean resolved);

    /**
     * Define o tipo da vista.
     * @param type Tipo.
     */
    ResultTypeParam use(Class type);
    
    public static interface ResultTypeParam{
        
        /**
         * Inclui uma nova informação no resultado.
         * 
         * @param name Nome da informação.
         * @param o Informação.
         */
        ResultTypeParam includeInfo(String name, String o);
        
        /**
         * Define o valor que representa a vista.
         * 
         * @param o Valor.
         */
        void include(Object o);
        
    }

    public static interface ResultParam{
        
        /**
         * Inclui uma nova informação no resultado.
         * 
         * @param name Nome da informação.
         * @param o Informação.
         */
        ResultParam includeInfo(String name, String o);

        /**
         * Inclui um novo valor no resultado.
         * 
         * @param name Nome do valor.
         * @param o Valor.
         */
        ResultParam include(String name, Object o);
        
    }
    
}
