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
import java.util.Collection;

/**
 * Usado para fazer o mapeamento de coleções de beans.
 * <pre>
 * Ex:
 * &#64;Controller(...)
 * &#64;MappingCollection(
 *     name="myBean",
 *     target=LinkedList.class,
 *     bean=&#64;MappingUseBean( target=Mybean.class )
 * )
 * public class MyController{
 *
 *     &#64;Action( parameters=&#64;UseBean( mappingName="myBean" ) )
 *     public void myAction( List arg ){
 *         ...
 *     }
 * }
 * </pre>
 * @author Afonso Brandao
 */
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MappingCollection {

    /**
     * Nome do mapeamento. Somente pode ser omitido se usado dentro de
     * outro mapeamento.
     * @return Nome.
     */
    String name() default "";

    /**
     * Mapeamento do bean.
     * @return Mapeamento.
     */
    MappingUseBean bean();

    /**
     * Classe alvo do mapeamento.
     * @return Classe.
     */
    Class<? extends Collection> target() default Collection.class;
    
}