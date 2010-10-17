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

/**
 * Especifica um interceptador.
 * <pre>
 * Ex:
 * &#64;Intercepts( name="myInterceptor" )
 * public class MyInterceptor extends AbstractInterceptor{
 *    ...
 * }
 *
 * Ex:
 * &#64;Intercepts(
 *     name="myInterceptor",
 *     params={
 *         Param( name="excludeMethods", value="load, view" ),
 *         Param( name="redirectPage", value="/index/" )
 *     }
 * )
 * public class MyInterceptor extends AbstractInterceptor{
 *    ...
 * }
 * </pre>
 *
 *  @author Afonso Brandao
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Intercepts {

    /**
     * Identificação do interceptador. Se não informado, será usado o nome da
     * classe.
     * @return Identificação.
     */
    String name() default "";

    /**
     * Determina se o interceptador é padrão. Se verdadeiro, o interceptador
     * irá interceptar todos os controladores.
     * @return Tipo de interceptador.
     */
    boolean isDefault() default false;

    /**
     * Parâmetros de configuração do interceptador.
     * @return Parâmetros.
     */
    Param[] params() default{};
}
