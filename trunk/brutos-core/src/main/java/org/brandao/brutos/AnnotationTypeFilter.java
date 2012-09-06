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
public class AnnotationTypeFilter implements TypeFilter{

    private boolean include;
    private Class annotation;
    
    public void setConfiguration(Properties config) {
        this.include = 
            config.getProperty("filter-type","include").equals("include");
        
        try{
            this.annotation =
                ClassUtil.get(config.getProperty("expression"));
        }
        catch(Exception e){
            throw new BrutosException(e);
        }
    }

    public Boolean accepts(Class classe) {
        return classe.isAnnotationPresent(annotation) ? Boolean.valueOf(include) : null;
    }
    
}
