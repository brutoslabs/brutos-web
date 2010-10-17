/*
 * Brutos Web MVC http://brutos.sourceforge.net/
 * Copyright (C) 2009 Afonso Brandao. (afonso.rbn@gmail.com)
 *
 * This library is free software. You can redistribute it 
 * and/or modify it under the terms of the GNU General Public
 * License (GPL) version 3.0 or (at your option) any later 
 * version.
 * You may obtain a copy of the License at
 * 
 * http://www.gnu.org/licenses/gpl.html 
 * 
 * Distributed WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied.
 *
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
