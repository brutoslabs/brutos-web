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
import org.brandao.brutos.BrutosConstants;

/**
 * Usado para interceptar e tratar exceções. 
 * Pode ser definida no controlador ou na ação. Se definida no 
 * controlador, todas as exceções lançadas em todas as ações serão 
 * tratadas de acordo com o que foi definida na configuração.
 * Por padrão, todas as exceções declaradas no método executor da ação 
 * que não tenham sido especificadas as configurações, serão interceptadas 
 * e configuradas com as opções padrão da anotação.
 * <pre>
 * Ex1:
 * &#064;ThrowSafe(target=NullPointerException.class)
 * public class MyController{
 * 
 *    public void indexAction(String id){
 *       if(id == null)
 *           throw new NullPointerException();
 *    }
 * }
 * 
 * Ex2:
 * public class MyController{
 * 
 *    &#064;ThrowSafe(target=NullPointerException.class)
 *    public void indexAction(String id){
 *       if(id == null)
 *           throw new NullPointerException();
 *    }
 * }
 * 
 * Ex3:
 * &#064;view(id="/jsp/mycontroller.jsp")
 * public class MyController{
 * 
 *    &#064;ThrowSafe(
 *        target=NullPointerException.class, 
 *        view="/jsp/exception.jsp")
 *    public void indexAction(String id){
 *       if(id == null)
 *           throw new NullPointerException();
 *    }
 * }
 * </pre>
 * 
 * @author Afonso Brandao
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ThrowSafe {
    
    /**
     * Define a visão que será renderizada. Caso não seja informada,
     * será assumido PREFIX_VIEW + CONTROLLER_NAME + SEPARATOR_VIEW
     * [+ ACTION_NAME + SEPARATOR_VIEW] + EXCEPTION_NAME. 
     * Somente será usado o ACTION_NAME se a anotação for especificada 
     * na ação.
     */
    String view() default "";
    
    /**
     * Exceção alvo do mapeamento.
     */
    Class<? extends Throwable> target();
    
    /**
     * Identificação da exceção na visão. Caso não seja informada, será assumido
     * "exception" .
     */
    String name() default BrutosConstants.DEFAULT_EXCEPTION_NAME;

    /**
     * Define como o fluxo de execução será direcionado para a visão.
     * Os valores estão descritos em <a href="DispatcherType.html">DispatcherType</a>.
     */
    String dispatcher() default BrutosConstants.DEFAULT_DISPATCHERTYPE_NAME;
    
    /**
     * Define se a visão deve ser renderizada. 
     * Se verdadeiro a visão será renderizada, caso contrário não.
     */
    boolean rendered() default true;
    
    /**
     * Desabilita a interceptação da exceção.
     * Se verdadeiro a exceção será interceptada e processada, 
     * caso contrário não.
     */
    boolean enabled() default true;
    
}
