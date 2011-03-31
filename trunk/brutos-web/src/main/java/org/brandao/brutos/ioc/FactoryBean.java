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

package org.brandao.brutos.ioc;

/**
 * Deprecated. Use the factory method and bean factory.
 * <pre>Factory:
 * public class MybeanFactory{
 *     public Object getInstance(){
 *         return new MyBean();
 *    }
 * }
 *
 * Config:
 * &lt;bean name=&quot;myBeanFactory&quot; class=&quot;MyBeanFactory&quot;/&gt;
 * &lt;bean name=&quot;myBean&quot; class=&quot;MyBean&quot; factory-method=&quot;getInstance&quot; factory-bean=&quot;myBeanFactory&quot;/&gt;</pre>
 * @author Afonso Brandao
 */
@Deprecated
public interface FactoryBean<T> {
    
    public T createInstance();
    
    public Class<T> getClassType();
    
}
