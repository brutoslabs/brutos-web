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

import java.util.Properties;

/**
 *
 * @author Brandao
 */
public class DefaultViewResolver implements ViewResolver{

    public String getView(ControllerBuilder controllerBuilder, 
            ActionBuilder actionBuilder, Class exception, Properties configuration) {
        
        String prefix = configuration
                .getProperty("org.brandao.brutos.view.prefix", 
                BrutosConstants.DEFAULT_PREFIX_VIEW);

        String suffix = configuration
                .getProperty("org.brandao.brutos.view.suffix", 
                BrutosConstants.DEFAULT_SUFFIX_VIEW);

        String indexName = configuration
                .getProperty("org.brandao.brutos.view.index", 
                BrutosConstants.DEFAULT_INDEX_VIEW);

        String separator = configuration
                .getProperty("org.brandao.brutos.view.separator", 
                BrutosConstants.DEFAULT_SEPARATOR_VIEW);
        
        String controllerName = controllerBuilder.getClassType().getSimpleName();
        controllerName = controllerName.replaceAll("Controller$", "");

        String view = prefix;
        view += controllerName.toLowerCase();
        view += separator;
        
        if(actionBuilder != null){
            view += actionBuilder.getName().toLowerCase();
            view += separator;
        }
        
        if(exception != null)
            view += exception.getSimpleName().toLowerCase();
        else
            view += indexName;
        
        view += suffix;
        
        return view;
        
    }
    
}
