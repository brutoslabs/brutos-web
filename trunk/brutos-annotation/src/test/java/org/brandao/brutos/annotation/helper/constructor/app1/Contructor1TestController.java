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

import org.brandao.brutos.annotation.Identify;
import org.brandao.brutos.annotation.ImportBeans;
import org.brandao.brutos.annotation.ScopeType;

/**
 *
 * @author Brandao
 */
@ImportBeans({
    ConstructorBean1.class,
    ConstructorBean2.class,
    ConstructorBean3.class})
public class Contructor1TestController {
    
    public ConstructorBean1 test1Action(
        @Identify(bean = "constructorBean1", scope = ScopeType.CONTROLLER) Object arg0){
        return (ConstructorBean1)arg0;
    }

    public ConstructorBean2 test2Action(
        @Identify(bean = "constructorBean2", scope = ScopeType.CONTROLLER) Object arg0){
        return (ConstructorBean2)arg0;
    }

    public ConstructorBean3 test3Action(
        @Identify(bean = "constructorBean3", scope = ScopeType.CONTROLLER) Object arg0){
        return (ConstructorBean3)arg0;
    }
    
}
