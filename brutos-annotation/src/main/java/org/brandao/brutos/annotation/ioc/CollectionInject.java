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

package org.brandao.brutos.annotation.ioc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Collection;

/**
 * Usado para definir uma coleção de beans.
 * <pre>
 * &#64;Injectable
 * &#64;CollectionInject(
 *   name="myCollection",
 *   type=ArrayList.class,
 *   values={
 *       &#64;Inject( value="Name1" ),
 *       &#64;Inject( value="Name2" ),
 *       &#64;Inject( value="Name3" ),
 *       &#64;Inject( value="Name4" )
 *   }
 * )
 * public class MyBean{
 *
 *     &#64;ContructorInject( &#64;Inject( ref="myCollection" ))
 *     public MyBean( List arg0 ){
 *         ...
 *     }
 * }
 * </pre>
 * @author Afonso Brandao
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CollectionInject {
    
    /**
     * Identificação do bean no container IOC.
     * @return Identificação do bean.
     */
    String name();

    /**
     * Classe que representa a coleção de beans.
     * @return Classe.
     */
    Class<? extends Collection> type() default Collection.class;

    /**
     * Beans que serão injetados na coleção.
     * @return Beans.
     */
    Inject[] values() default {};

    /**
     * Fábrica da coleção. Se informado, a fábrica irá controlar a
     * instância da coleção.
     * @return Identificação da fábrica.
     */
    String factory() default "";
    
}
