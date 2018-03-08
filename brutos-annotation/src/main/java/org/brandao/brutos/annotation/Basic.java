/*
 * Brutos Web MVC http://www.brutosframework.com.br/
 * Copyright (C) 2009-2017 Afonso Brandao. (afonso.rbn@gmail.com)
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
 * Pode ser usada para especificar o parâmetro de uma ação, propriedade de uma
 * entidade ou um controlador e o argumento de um construtor.
 * 
 * <pre>
 * Ex1:
 * public class MyController{
 * 
 *    public void myAction(
 *       &#064;Basic(bean="indice")
 *       int index){
 *       ...
 *    }
 * }
 * </pre>
 * 
 * <pre>
 * Ex2:
 * &#064;Bean
 * public class MyBean{
 *    
 *    &#064;Basic(bean="indice")
 *    private int index;
 * 
 * }
 * </pre>
 * 
 * <pre>
 * Ex3:
 * &#064;Bean
 * public class MyBean{
 *    
 *    private int index;
 * 
 *    &#064;Basic(bean="indice")
 *    public void setIndex(int value){
 *       this.index = value;
 *    }
 * 
 *    ...
 * }
 * </pre>
 * 
 * <pre>
 * Ex4:
 * &#064;Bean
 * public class MyBean{
 *    
 *    private int index;
 * 
 *    public MyBean(
 *         &#064;Basic(bean="indice")
 *         int value){
 *       this.index = value;
 *    }
 * 
 *    ...
 * }
 * </pre>
 * 
 * @author Brandao
 */

@Target({ ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Basic {

	/**
	 * Nome da entidade.
	 * 
	 */
	String bean() default "";

	/**
	 * Escopo da entidade. Os escopos estão descritos em {@link ScopeType}
	 * 
	 */
	String scope() default ScopeType.PARAM;

	/**
	 * Define a estratégia a ser utilizada para obter os dados de uma solicitação. 
	 * Os valores estão descritos em {@link FetchTypes}
	 */
	FetchTypes fetchType() default FetchTypes.EAGER;
	
	/**
	 * Tipo do mapeamento da entidade. Os tipos estão descritos em
	 * {@link MappingTypes}
	 */
	MappingTypes mappingType() default MappingTypes.AUTO;

}
