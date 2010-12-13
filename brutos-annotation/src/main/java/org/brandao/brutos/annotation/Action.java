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
 * Especifica uma determinada ação.
 * <pre>
 * Ex:
 * &#64;Controller( id="/myController.jbrs" )
 * public class MyController{
 *
 *     &#64;Action( name="test" )
 *     public void testAction(){
 *         ...
 *     }
 * }
 * </pre>
 * <pre>
 * Ex2:
 * &#64;Controller
 * public class MyController{
 *
 *     &#64;Action
 *     public void testAction(){
 *         ...
 *     }
 * }
 * </pre>
 * No exemplo acima a ação terá o nome de testAction e a view será
 * /MyController/testAction.jsp.
 * 
 * <pre>
 * Ex3:
 * &#64;Controller
 * public class MyController{
 *
 *     &#64;Action( id="/testAction.jbrs" )
 *     public void testAction(){
 *         ...
 *     }
 * }
 * </pre>
 * 
 * @author Afonso Brandao
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Action {
    
    /**
     * Identificação da ação. Se não informado, será usado o nome do método.
     * @return Identificação da ação.
     */
    String id();

    /**
     * Parâmetros da ação. Se não informado, os parâmetros serão automaticamente
     * configurados.
     * @return
     */
    UseBean[] parameters() default {};

    /**
     * Se a ação retornar algun valor, essa será sua
     * identificação. Se não informado, será usado <b>result</b>
     * @return
     */
    String resultName() default "result";

    /**
     * Identificação da visão. Se não informado, será usado a concatenação de
     * /, nome da classe, /, nome da ação e .jsp.
     * @return Nome da visão. A visão é representada normalmente
     * por um URI.
     */
    String view() default "";
    
}
