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

package org.brandao.brutos.annotation.helper;

import org.brandao.brutos.annotation.Intercept;
import org.brandao.brutos.annotation.InterceptedBy;
import org.brandao.brutos.annotation.Param;
import org.brandao.brutos.annotation.helper.interceptor.Test1InterceptorController;
import org.brandao.brutos.annotation.helper.interceptor.Test3Interceptor;

/**
 *
 * @author Brandao
 */

@InterceptedBy(
        value={
            @Intercept(
                interceptor=Test3Interceptor.class,
                params={
                    @Param(name="param1",value="value1"),
                    @Param(name="param2",value="value2")
                }
            ),
            @Intercept(interceptor=Test1InterceptorController.class)
        }
)
public class InterceptorTest4Controller {
    
    public Object myFirstAction(){
        return null;
    }

}
