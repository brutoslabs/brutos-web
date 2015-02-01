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

package org.brandao.brutos.annotation.helper.constructor.app1;

import org.brandao.brutos.annotation.Bean;
import org.brandao.brutos.annotation.Constructor;
import org.brandao.brutos.annotation.Transient;

/**
 *
 * @author Brandao
 */
@Bean
public class ConstructorBean3 {
    
    @Transient
    private String property;

    @Transient
    private String property2;
    
    @Constructor
    public ConstructorBean3(String arg0, String arg1){
        this.property = arg0;
        this.property2 = arg1;
    }

    public String getProperty() {
        return property;
    }

    public String getProperty2() {
        return property2;
    }

}
