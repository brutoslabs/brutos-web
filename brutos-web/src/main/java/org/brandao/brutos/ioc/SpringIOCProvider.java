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

import java.util.Properties;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

/**
 *
 * @author Afonso Brandao
 */
public class SpringIOCProvider extends IOCProvider{

    WebApplicationContext context;

    public Object getBean(String name) {
        return context.containsBeanDefinition(name)? context.getBean(name) : null;
    }

    public void configure(Properties properties) {
        this.context = ContextLoader.getCurrentWebApplicationContext();
    }

    public void destroy() {
    }

    @Override
    public Object getBean(Class clazz) {
        return context.getBean(clazz);
    }
    

}