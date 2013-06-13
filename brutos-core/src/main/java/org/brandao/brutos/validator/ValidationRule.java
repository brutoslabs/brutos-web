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


package org.brandao.brutos.validator;

import java.util.Properties;

/**
 * Define uma regra de validação.
 * O método {@link #validate(java.util.Properties, java.lang.Object, java.lang.Object) validate} 
 * é responsável por validar um objeto e suas dependências. Ele recebe como parâmetro 
 * a configuração, origem do valor e o valor a ser validado.
 * A configuração é representada pela classe {@link java.util.Properties Properties} e nele
 * contém o nome da regra e seu valor. Pode-se conter mais de uma regra de validação.
 * A origem do valor pode ser {@link org.brandao.brutos.mapping.UseBeanData UseBeanMapping} 
 * quando for uma propriedade do controlador ou um parâmetro de uma ação ou 
 * {@link org.brandao.brutos.mapping.DependencyBean DependencyBean} se for um argumento 
 * de um construtor ou a propriedade de um bean.
 * 
 * @author Brandao
 */
public interface ValidationRule {

    /**
     * Faz a validação de um objeto.
     * @param config Configuração da regra de validação.
     * @param source Origem do valor a ser validado.
     * @param value Valor a ser validado.
     * @throws ValidatorException Lançado quando o valor for considerado inválido.
     */
    public void validate(Properties config, Object source, Object value)
            throws ValidatorException;
}
