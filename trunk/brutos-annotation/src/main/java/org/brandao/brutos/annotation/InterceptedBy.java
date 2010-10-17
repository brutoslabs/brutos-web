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
 * Determina quais interceptadores irão
 * interceptar o controlador.
 * <p>Ex:</p>
 * <pre>
 * &#64;Controller(...)
 * &#64;InterceptedBy( 
 *     &#64;Intercept( name="myInterceptor" ) )
 * public class MyController{
 *     ...
 * }
 * </pre>
 *  @author Afonso Brandao
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface InterceptedBy {

    /**
     * Lista de interceptadores.
     * @return Interceptadores.
     */
    Intercept[] value();
    
}
