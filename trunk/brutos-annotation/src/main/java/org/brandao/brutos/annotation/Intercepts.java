/*
 * Brutos Web MVC http://www.brutosframework.com.br/
 * Copyright (C) 2009 Afonso Brandao. (afonso.rbn@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.brandao.brutos.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Define um interceptador. Com ela é possível determinar o nome 
 * e a configuração do mesmo.
 * <p>Também é possível definir um interceptador sem a necessidade do uso da
 * anotação. Nesse caso, além da implementação da interface
 * InterceptorController, é necessário que o nome da classe siga a nomenclatura
 * <b><code>&lt;nome-do-interceptador&gt;InterceptorController</code></b>.</p>
 * 
 * <pre>
 * Ex1:
 * &#064;Intercepts
 * public class MyInterceptor
 *       extends AbstractInterceptorController{
 * 
 *    public void intercepted(InterceptorStack stack, InterceptorHandler handler) 
 *       throws InterceptedException {
 *       ...
 *    }
 * }
 * 
 * Ex2:
 * public class ControllerTestInterceptorController
 *       extends AbstractInterceptorController{
 * 
 *    public void intercepted(InterceptorStack stack, InterceptorHandler handler) 
 *       throws InterceptedException {
 *       ...
 *    }
 * }
 * </pre>
 * 
 * @author Afonso Brandao
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Intercepts {

    /**
     * Nome do interceptador. Se for omitido, será usado o nome da 
     * classe.
     */
    String name() default "";

    /**
     * Indica que o interceptador é global. Se verdadeiro,
     * todos os controladores serão interceptados. Caso contrário, 
     * terá que ser definido quais controldores deverão ser 
     * interceptados.
     */
    boolean isDefault() default true;

    /**
     * Parâmetros de configuração do interceptador.
     */
    Param[] params() default{};
    
}
