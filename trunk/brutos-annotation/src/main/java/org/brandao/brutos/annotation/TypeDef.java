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
 * Define um novo tipo.
 *
 * <p>Ex:</p>
 * <pre>
 * &#64;TypeDef( java.util.Locale.class )
 * public class LocaleType implements Type{
 * 
 *    public LocaleType() {
 *     }
 *
 *     public Object getValue( HttpServletRequest request, ServletContext context, Object value ) {
 *         ...
 *     }
 *
 *     public Class getClassType() {
 *         return Locale.class;
 *    }
 *
 *     public void setValue(HttpServletResponse response, ServletContext context, Object value) throws IOException {
 *         ...
 *     }
 * }
 * </pre>
 *
 * Exemplo do uso em um controlador:
 * <pre>
 * &#64;WebFrame
 * public class MyController{
 *
 *     public void defineLocale( Locale arg ){
 *         ...
 *     }
 * }
 * </pre>
 *
 * @author Afonso Brandao
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface TypeDef {

    /**
     * Classe alvo da configuração.
     * @return
     */
    Class value();

}
