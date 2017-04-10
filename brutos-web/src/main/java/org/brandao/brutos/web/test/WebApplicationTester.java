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

package org.brandao.brutos.web.test;

import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.brandao.brutos.web.ConfigurableWebApplicationContext;

/**
 * 
 * @author Brandao
 */
public interface WebApplicationTester {
    
    void prepareContext(Map<String,String> parameters);

    void prepareRequest(Map<String,String> parameters);
    
    void prepareSession(Map<String,String> parameters);

    void checkException(Throwable e);
    
    void checkResult(HttpServletRequest request, 
                HttpServletResponse response, 
                ServletContext context,
                ConfigurableWebApplicationContext applicationContext);
    
}
