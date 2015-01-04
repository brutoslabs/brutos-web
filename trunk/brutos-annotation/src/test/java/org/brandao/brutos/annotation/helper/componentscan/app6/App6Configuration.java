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


package org.brandao.brutos.annotation.helper.componentscan.app6;

import org.brandao.brutos.annotation.ComponentScan;
import org.brandao.brutos.annotation.Configuration;

/**
 *
 * @author Brandao
 */
@Configuration
@ComponentScan(
    basePackage = App6Configuration.class,
    useDefaultFilters = false)
public class App6Configuration {
    
}
