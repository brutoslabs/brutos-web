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

import java.lang.annotation.Annotation;
import org.brandao.brutos.ConfigurableApplicationContext;
import org.brandao.brutos.annotation.configuration.AnnotationConfigEntry;
import org.brandao.brutos.annotation.configuration.Converter;

/**
 * Usada na configuração de novos recursos usando anotação ou 
 * "Convention over configuration".
 * 
 * @author Brandao
 */
public interface AnnotationConfig {
    
    /**
     * Define o conversor dos dados de entrada.
     */
    void setSourceConverter(Converter value);
    
    /**
     * Obtém o conversor dos dados de entrada.
     */
    Converter getSourceConverter();
    
    /**
     * Define a configuração.
     */
    void setConfiguration(AnnotationConfigEntry annotation);
    
    /**
     * Verifica se o recurso vai ser aplicado à entidade.
     * @param source Entidade.
     * @return Verdadeiro se o recurso for ser aplicado a entidade, caso
     * contrário falso.
     */
    boolean isApplicable(Object source);

    /**
     * Aplica o recurso na entidade.
     * 
     * @param source Entidade.
     * @param builder Construtor da entidade.
     * @param applicationContext Aplicação.
     * @return Construtor da entidade.
     */
    Object applyConfiguration(Object source, Object builder,
            ConfigurableApplicationContext applicationContext);
    
    /**
     * Obtém a ordem de execução dos recursos do próximo nível.
     */
    Class<? extends Annotation>[] getExecutionOrder();
    
}
