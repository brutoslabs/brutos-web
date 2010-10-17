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
 * Usado para fazer o mapeamento de um bean.
 * <pre>
 * Ex:
 *
 * &#64;Controller
 * &#64;MappingUseBean(
 *     name="myBean",
 *     target=MyBean.class,
 *     properties={
 *        &#64;PropertyBean( name="id_bean", propertyName="id" ),
 *        &#64;PropertyBean( name="name_bean", propertyName="name" )
 *     }
 * )
 * public class MyController{
 *
 *     &#64;Action( parameters=&#64;UseBean( mappingName="myBean" ) )
 *     public void action( MyBean bean ){
 *         ...
 *     }
 * }
 * </pre>
 *
 * @author Afosno Brandao
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MappingUseBean {
    
    String name() default "";
    
    Class<?> target();
    
    PropertyBean[] properties() default {};
    
}
