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
 * Define uma ação que não possui um método associado (executor).
 * 
 * <pre>
 * Ex1:
 * &#064;AbstractAction(id="index", view="/testcontroller/index.jsp")
 * public class TestController{
 * 
 *    &#064;View(id="/testcontroller/perfil.jsp")
 *    public void perfilAction(){
 *       ...
 *    }
 * }
 * 
 * Ex2:
 * public class TestController{
 * 
 *    public void rootAction(){
 *       ...
 *    }
 * }
 * </pre>
 * 
 * @author Afonso Brandao
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface AbstractAction {
    
    /**
     * Identificação da Ação. 
     */
    String[] id();
    
    /**
     * Identificação da visão. Somente pode ser omitido se for definida a visão
     * do controlador. Normalmente em aplicações web, a 
     * identificação é um URI.
     */
    String view();
    
    /**
     * Define como o fluxo de execução será direcionado para a visão.
     * Os valores estão descritos em <a href="DispatcherType.html">DispatcherType</a>.
     */
    String dispatcher() default "forward";
    
}
