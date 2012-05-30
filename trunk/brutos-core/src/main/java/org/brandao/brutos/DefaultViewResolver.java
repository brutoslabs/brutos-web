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

package org.brandao.brutos;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Properties;

/**
 *
 * @author Brandao
 */
public class DefaultViewResolver implements ViewResolver{

    public String getView(Class controllerClass, Method action, 
            Properties configuration) {
        
        String prefix = configuration
                .getProperty("org.brandao.brutos.view.prefix", 
                BrutosConstants.DEFAULT_PREFIX_VIEW);

        String suffix = configuration
                .getProperty("org.brandao.brutos.view.suffix", 
                BrutosConstants.DEFAULT_SUFFIX_VIEW);

        String controller = configuration
                .getProperty("org.brandao.brutos.view.controller", 
                BrutosConstants.DEFAULT_CONTROLLER_VIEW);
        
        String separator = File.separator;

        String view = prefix;
        view += controllerClass.getSimpleName();
        view += separator;
        view += action == null? controller : action.getName();
        view += suffix;
        return view.toLowerCase();
    }
    
}
