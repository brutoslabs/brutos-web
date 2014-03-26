/*
 * Brutos Web MVC http://www.brutosframework.com.br/
 * Copyright (C) 2009-2012 Afonso Brandao. (afonso.rbn@gmail.com)
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

package org.brandao.brutos.annotation.configuration;

import org.brandao.brutos.ActionResolver;
import org.brandao.brutos.ControllerManager;
import org.brandao.brutos.ControllerResolver;
import org.brandao.brutos.InterceptorManager;
import org.brandao.brutos.MvcRequestFactory;
import org.brandao.brutos.MvcResponseFactory;
import org.brandao.brutos.Scopes;
import org.brandao.brutos.ViewResolver;
import org.brandao.brutos.codegenerator.CodeGeneratorProvider;
import org.brandao.brutos.ioc.IOCProvider;
import org.brandao.brutos.logger.LoggerProvider;
import org.brandao.brutos.type.TypeManager;
import org.brandao.brutos.validator.ValidatorProvider;
import org.brandao.brutos.view.ViewProvider;

/**
 *
 * @author Brandao
 */
public interface Configurer {
    
    void addInterceptors(InterceptorManager interceptorManager);
    
    void addControllers(ControllerManager controllerManager);
    
    void addScopes(Scopes scopes);
            
    void addTypes(TypeManager typeManager);
    
    IOCProvider getIOCProvider();
    
    InterceptorManager getInterceptorMaanger();
    
    ControllerManager getControllerManager();
    
    ViewProvider getViewProvider();
    
    ValidatorProvider getValidatorProvider();
    
    LoggerProvider getLoggerProvider();
    
    ControllerResolver getControllerResolver();
    
    ActionResolver getActionResolver();
    
    MvcResponseFactory getMvcResponseFactory();
    
    MvcRequestFactory getMvcRequestFactory();
    
    ViewResolver getViewResolver();
    
    CodeGeneratorProvider getCodeGeneratorProvider();
    
}
