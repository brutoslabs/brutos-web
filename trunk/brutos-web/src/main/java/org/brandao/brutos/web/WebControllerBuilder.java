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

package org.brandao.brutos.web;

import org.brandao.brutos.*;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.util.WebUtil;
import org.brandao.brutos.validator.ValidatorProvider;

/**
 *
 * @author Brandao
 */
public class WebControllerBuilder extends ControllerBuilder{
    
    public WebControllerBuilder( Controller controller, ControllerManager controllerManager,
            InterceptorManager interceptorManager, ValidatorProvider validatorProvider,
            ConfigurableApplicationContext applicationContext ){
        super( controller, controllerManager, interceptorManager, 
                validatorProvider, applicationContext );
    }
    
    public ControllerBuilder addAlias( String id ){
        return super.addAlias(WebUtil.fixURI(id));
    }
    
    public ActionBuilder addAction( String id, String resultId, String view, 
            DispatcherType dispatcher, String executor ){
        return 
            super.addAction( 
                WebUtil.fixURI(id), resultId, WebUtil.fixURI(view), 
                dispatcher, executor );
    }
    
}
