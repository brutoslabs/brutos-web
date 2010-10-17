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

/**
 * Define que a instância do bean será controlada
 * pelo container IOC.
 * <p>Ex:</p>
 * <pre>
 * &#64;Injectable
 * public class MyBean{
 *     ...
 * }
 * </pre>
 * @author Afonso Brandao
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Injectable {
    
    /**
     * Identificação do bean no container IOC. Se não informado,
     * será usado o nome da classe.
     * @return Identificação do bean.
     */
    String name() default "";

    /**
     * Define que só irá existir uma instância do bean em toda aplicação.
     * @return Se verdadeiro, só existirá uma instância do bean.
     */
    boolean singleton() default false;

}
