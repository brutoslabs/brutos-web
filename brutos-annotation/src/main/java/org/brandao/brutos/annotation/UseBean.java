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


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.brandao.brutos.type.Type;

/**
 * Usado na especificação do mapeamento de uma propriedade
 * ou parâmetro de uma ação.
 * 
 *  @author Afonso Brandao
 */
@Target({ElementType.ANNOTATION_TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Deprecated
public @interface UseBean {
    

    /**
     * Identificação do valor. Normalmente o valor é obtido de uma
     * requisição.
     * @return Identificação.
     */
    String name() default "";

    /**
     * Nome da properiedade.
     * @return Nome da propriedade.
     */
    String propertyName();

    /**
     * Define o escopo da propriedade. Se não informado, será usado request.
     * @return Escopo.
     */
    String scope() default "request";

    /**
     * Usado no mapeamento de propriedades to tipo enum. Determina o
     * formato do enum. Se for ordinal, então o enum é representado
     * por um inteiro. Se for string, então o enum é representado por
     * uma string.
     * @return Tipo.
     */
    String enumProperty() default "ordinal";

    /**
     * Usado no mapeamento de propriedades to tipo Date. Determina o
     * formato da data. Esta opção não suporta todas as localidades.
     * @return Formato da data.
     */
    String temporalProperty() default "dd/MM/yyyy";

    /**
     * Determina que o valor da propriedade será obtida através de outro
     * mapeamento.
     * @return Nome do mapeamento.
     */
    String mappingName() default "";

    /**
     * Faz o processamento do valor. Normalmento usado quanto não se
     * possui suporte a um determinado tipo de classe.
     */
    Class<? extends Type> factory() default Type.class;
 
}
