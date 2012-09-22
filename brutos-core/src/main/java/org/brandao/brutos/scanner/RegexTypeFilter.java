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

package org.brandao.brutos.scanner;

import java.util.Properties;
import org.brandao.brutos.BrutosConstants;
import org.brandao.brutos.scanner.vfs.Vfs;

/**
 *
 * @author Brandao
 */
public class RegexTypeFilter implements TypeFilter{

    protected boolean include;
    protected String regex;
    
    public void setConfiguration(Properties config) {
        this.include = 
            config.getProperty(
                "filter-type",BrutosConstants.INCLUDE)
                    .equals(BrutosConstants.INCLUDE);
        this.regex =
            config.getProperty("expression",".*");
    }

    public Boolean accepts(String resource) {
        String className = Vfs.toClass(resource);
        return className.matches(regex)? Boolean.valueOf(include) : null;
    }
    
}
