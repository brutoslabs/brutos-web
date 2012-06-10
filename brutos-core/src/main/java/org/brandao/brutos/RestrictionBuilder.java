/*
 * Brutos Web MVC http://www.brutosframework.com.br/
 * Copyright (C) 2009 Afonso Brandao. (afonso.rbn@gmail.com)
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

import java.util.Properties;
import org.brandao.brutos.validator.RestrictionRules;

/**
 * Classe usada para validar uma determinada propriedade ou parãmentro.
 * 
 * @author Afonso Brandao
 */
public class RestrictionBuilder{

    private Properties config;
    
    public RestrictionBuilder( Properties config){
        this.config = config;
    }

    /**
     * Adiciona uma nova restrição.
     *
     * @param ruleId Tipo da restrição. Os valores estão descritos em RestrictionRules.
     * @param value Valor da restrição.
     * @return Construtor da restrição.
     */
    public RestrictionBuilder addRestriction( RestrictionRules ruleId, Object value ){
        config.put( ruleId.toString(), value );
        return this;
    }

    /**
     * Define a mensagem a ser exibida caso o valor testado não seja considerado
     * válido.
     * @param message Mensagem.
     * @return Construtor da propriedade.
     */
    
    public RestrictionBuilder setMessage( String message ){
        config.setProperty("message", message );
        return this;
    }

}
