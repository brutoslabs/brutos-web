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
import org.brandao.brutos.TypeManager;

/**
 *
 * @author Afonso Brandao
 */
public interface ConfigurableApplicationContext 
        extends ApplicationContext{

    /**
     * Obtém a fábrica de requisições.
     * @return Fábrica.
     */
    MvcRequestFactory getRequestFactory();

    /**
     * Obtém a fábrica de resposta.
     * @return Fábrica.
     */
    MvcResponseFactory getResponseFactory();

    /**
     * Define o gestor de interceptores.
     * @param interceptorManager Gestor de interceptores.
     */
    void setInterceptorManager(InterceptorManager interceptorManager);


    /**
     * Define o renderizador de vista da aplicação.
     * @param renderView Renderizador de vista da aplicação.
     */
    void setRenderView(RenderView renderView);
    
    /**
     * Obtém o renderizador de vista da aplicação.
     * @return Renderizador de vista da aplicação.
     */
    RenderView getRenderView();

    /**
     * Obtém a fábrica de validadores.
     * @return Provedor das regras de validação
     */
    ValidatorFactory getValidatorFactory();

    /**
     * Obtém o responsável por executar as ações.
     * @return Responsável por executar as ações.
     */
    Invoker getInvoker();

    /**
     * Define o responsável por executar as ações.
     * @param value Responsável por executar as ações.
     */
    void setInvoker(Invoker value);

    /**
     * Define as configurações da aplicação.
     * @param Configuração Configuração.
     */
    void setConfiguration(Properties config);

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
     * Obtém o gestor de interceptadores.
     * @return Gestor de interceptadores.
     */
    InterceptorManager getInterceptorManager();

    /**
     * Obtém o gestor de controladores.
     * @return Gestor de controladores.
     */
    ControllerManager getControllerManager();

    /**
     * Obtém a fábrica de objetos da aplicação.
     * @return Fábrica de objetos da aplicação.
     */
    ObjectFactory getObjectFactory();

    /**
     * Obtém o responsável por resolver os controladores.
     * @return Responsável por resolver os controladores.
     */
    ControllerResolver getControllerResolver();

    /**
     * Obtém o responsável por resolver as ações.
     * @return Responsável por resolver as ações.
     */
    ActionResolver getActionResolver();

    CodeGenerator getCodeGenerator();

    void setCodeGenerator(CodeGenerator codeGenerator);

    void setViewResolver(ViewResolver viewResolver);

    ViewResolver getViewResolver();

    TypeManager getTypeManager();
    
    void setParent(ApplicationContext applicationContext);

    ApplicationContext getParent();
    
    void flush();
    
}
