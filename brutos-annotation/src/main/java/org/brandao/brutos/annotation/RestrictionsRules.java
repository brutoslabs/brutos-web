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

package org.brandao.brutos.annotation;

/**
 * Descreve as regras de validação pré-definidas.
 * 
 * @author Brandao
 */
public interface RestrictionsRules {
    
    /**
     * Somente são permitidos valores maiores que o valor definido. Somente permitido
     * para valores numéricos.
     */
    public final String MIN       = org.brandao.brutos.validator.RestrictionRules.MIN.toString();
    
    /**
     * Somente são permitidos textos com tamanho maior que o valor definido.
     * Somente permitido para tipos texto.
     */
    public final String MINLENGTH = org.brandao.brutos.validator.RestrictionRules.MINLENGTH.toString();
    
    /**
     * Somente são permitidos valores menores que o valor definido. Somente permitido
     * para valores numéricos.
     */
    public final String MAX       = org.brandao.brutos.validator.RestrictionRules.MAX.toString();
    
    /**
     * Somente são permitidos textos com tamanho menor que o valor definido.
     * Somente permitido para tipos texto.
     */
    public final String MAXLENGTH = org.brandao.brutos.validator.RestrictionRules.MAXLENGTH.toString();
    
    /**
     * Somente são permitidos valores que correspondam a expressão regular definida.
     * Somente permitido para tipos texto.
     */
    public final String MATCHES   = org.brandao.brutos.validator.RestrictionRules.MATCHES.toString();
    /**
     * Somente são permitidos valores diferente de null.
     */
    public final String REQUIRED  = org.brandao.brutos.validator.RestrictionRules.REQUIRED.toString();
    
    /**
     * Somente são permitidos valores iguais ao valor definido.
     */
    public final String EQUAL     = org.brandao.brutos.validator.RestrictionRules.EQUAL.toString();
    
}
