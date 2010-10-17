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
 * Define a injeção de dependência por método.
 * <pre>
 * Injeção de dependência por referência.
 *
 * &#64;Injectable( name="depBean" )
 * public class DepBean{
 *     ...
 * }
 *
 * &#64;Injectable
 * public class MyBean{
 *
 *     &#64;PropertyInject( &#64;Inject( ref="depBean" ) )
 *     public void setProperty( DepBean property ){
 *         ...
 *     }
 *
 * }
 *
 * Injeção de dependência por valor.
 *
 * &#64;Injectable
 * public class MyBean{
 *
 *     &#64;PropertyInject( &#64;Inject( value="1200" ) )
 *     public void setProperty( int property ){
 *         ...
 *     }
 *
 * }
 *
 * </pre>
 * @author Afonso Brandao
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PropertyInject {

    /**
     * Dependência que será injetada.
     * @return Dependência.
     */
    Inject value();
    
}
