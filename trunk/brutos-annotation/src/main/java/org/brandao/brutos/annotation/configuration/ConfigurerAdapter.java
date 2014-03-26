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
public abstract class ConfigurerAdapter implements Configurer{

    public void addInterceptors(InterceptorManager interceptorManager) {
    }

    public void addControllers(ControllerManager controllerManager) {
    }

    public void addScopes(Scopes scopes) {
    }

    public void addTypes(TypeManager typeManager) {
    }

    public IOCProvider getIOCProvider() {
        return null;
    }

    public InterceptorManager getInterceptorMaanger() {
        return null;
    }

    public ControllerManager getControllerManager() {
        return null;
    }

    public ViewProvider getViewProvider() {
        return null;
    }

    public ValidatorProvider getValidatorProvider() {
        return null;
    }

    public LoggerProvider getLoggerProvider() {
        return null;
    }

    public ControllerResolver getControllerResolver() {
        return null;
    }

    public ActionResolver getActionResolver() {
        return null;
    }

    public MvcResponseFactory getMvcResponseFactory() {
        return null;
    }

    public MvcRequestFactory getMvcRequestFactory() {
        return null;
    }

    public ViewResolver getViewResolver() {
        return null;
    }

    public CodeGeneratorProvider getCodeGeneratorProvider() {
        return null;
    }
    
}
