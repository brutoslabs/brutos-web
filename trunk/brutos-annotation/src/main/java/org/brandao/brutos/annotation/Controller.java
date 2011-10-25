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
 * Indica que a classe é um controlador.
 * <p>Ex:</p>
 * <pre>
 * &#64;Controller( id="/myController.jbrs", view="/myController.jsp" )
 * public class MyController{
 *     ...
 * }
 * </pre>
 * <p>Ex2:</p>
 * <pre>
 * &#64;Controller
 * public class MyController{
 *     ...
 * }
 * </pre>
 * No exemplo acima a id será /MyController.jbrs e a view será
 * /MyController/MyController.jsp.
 * 
 * @author Afonso Brandao
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Controller{

    /**
     * Ação a ser executada caso não seja especificada
     * nenhuma ação.
     * @return Nome da ação.
     */
    String defaultActionName() default "";

    /**
     * Identificação da visão. Se não informado, será a concatenação
     * de /, nome da classe, /, nome da classe e .jsp.
     * @return Nome da visão. A visão é representada normalmente
     * por um URI.
     */
    String view() default "";

    /**
     * Nome do controlador. Usado para identificar o controlador no
     * container IOC. Se não informado, é usado o nome da classe.
     * @return Nome do controlador.
     */
    String name() default "";

    /**
     * Identificação do controlador. Se não informado, será usado a concatenação
     * do nome da classe e .jbrs.
     * @return Arranjo de identificações.
     */
    String id() default "";

    /**
     * Nome do parâmetro que contém a identificação da ação.
     * @return Nome do parâmetro. Por padrão é <b>invoke</b>.
     */
    String actionId() default "invoke";

    /**
     * Determina como a visão será processada.
     */
    String dispatcher() default "forward";

}
