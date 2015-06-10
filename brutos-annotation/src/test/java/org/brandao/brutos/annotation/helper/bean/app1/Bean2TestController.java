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

package org.brandao.brutos.annotation.helper.bean.app1;

import org.brandao.brutos.annotation.Basic;
import org.brandao.brutos.annotation.ImportBeans;
import org.brandao.brutos.annotation.ScopeType;

/**
 *
 * @author Brandao
 */
@ImportBeans({Bean1.class, Bean2.class})
public class Bean2TestController {
    
    public String testeAction(
    		@Basic(bean="bean1", scope=ScopeType.CONTROLLER) Object arg0){
        return ((Bean1)arg0).getProperty();
    }

    public String teste2Action(
    		@Basic(bean="bean", scope=ScopeType.CONTROLLER) Object arg0){
        return ((Bean2)arg0).getProperty();
    }
    
}
