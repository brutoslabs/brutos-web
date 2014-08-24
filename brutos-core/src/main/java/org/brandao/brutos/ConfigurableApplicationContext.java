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


package org.brandao.brutos;

import java.util.Properties;
import org.brandao.brutos.codegenerator.CodeGeneratorProvider;
import org.brandao.brutos.type.TypeManager;
import org.brandao.brutos.validator.ValidatorProvider;
import org.brandao.brutos.view.ViewProvider;

/**
 *
 * @author Afonso Brandao
 */
public interface ConfigurableApplicationContext 
        extends ApplicationContext{

    /**
     * Obt�m a f�brica de requisi��o.
     * @return F�brica.
     */
    MvcRequestFactory getRequestFactory();

    /**
     * Obt�m a f�brica de resposta.
     * @return F�brica.
     */
    MvcResponseFactory getResponseFactory();

    /**
     * Define o gestor de interceptadores.
     * @param interceptorManager Gestor de interceptadores.
     */
    void setInterceptorManager(InterceptorManager interceptorManager);


    /**
     * Obt�m o provedor da vis�o.
     * @return Provedor da vis�o.
     */
    ViewProvider getViewProvider();

    /**
     * Obt�m o provedor das regras de valida��o.
     * @return Provedor das regras de valida��o
     */
    ValidatorProvider getValidatorProvider();

    /**
     * Obt�m o respons�vel por executar as a��es.
     * @return Respons�vel por executar as a��es.
     */
    Invoker getInvoker();

    /**
     * Define o responsável por executar as ações.
     * @param value Responsável por executar as ações.
     */
    void setInvoker(Invoker value);

    /**
     * Define as configura��es da aplica��o.
     * @param config Configura��o.
     */
    void setConfiguration( Properties config );

    /**
     * Obtém a configuração da aplicação.
     * @return Configuração.
     */
    Properties getConfiguration();

    /**
     * Define a fábrica de objetos da aplicação.
     * @param objectFactory  Fábrica de objetos da aplicação.
     */
    void setObjectFactory(ObjectFactory objectFactory);

    /**
     * Obt�m o gestor de interceptadores.
     * @return Gestor de interceptadores.
     */
    InterceptorManager getInterceptorManager();

    /**
     * Obt�m o gestor de controladores.
     * @return Gestor de controladores.
     */
    ControllerManager getControllerManager();

    /**
     * Obtém a fábrica de objetos da aplicação.
     * @return Fábrica de objetos da aplicação.
     */
    ObjectFactory getObjectFactory();

    /**
     * Obt�m o respons�vel por resolver os controladores.
     * @return Respons�vel por resolver os controladores.
     */
    ControllerResolver getControllerResolver();

    /**
     * Obt�m o respons�vel por resolver as a��es.
     * @return Respons�vel por resolver as a��es.
     */
    ActionResolver getActionResolver();

    CodeGeneratorProvider getCodeGeneratorProvider();

    void setCodeGeneratorProvider(CodeGeneratorProvider codeGeneratorProvider);

    void setViewResolver(ViewResolver viewResolver);

    ViewResolver getViewResolver();

    TypeManager getTypeManager();
    
    void setParent(ApplicationContext applicationContext);

    ApplicationContext getParent();
    
    void flush();
    
}
