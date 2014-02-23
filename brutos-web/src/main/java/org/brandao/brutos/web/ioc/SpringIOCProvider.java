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

package org.brandao.brutos.web.ioc;

import java.util.Properties;
import org.brandao.brutos.ioc.IOCProvider;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

/**
 * 
 * @author Brandao
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