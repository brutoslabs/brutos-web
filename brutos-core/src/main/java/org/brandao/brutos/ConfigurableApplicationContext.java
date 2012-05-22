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

package org.brandao.brutos;

import java.util.Properties;
import org.brandao.brutos.codegenerator.CodeGeneratorProvider;
import org.brandao.brutos.ioc.IOCProvider;
import org.brandao.brutos.old.programatic.IOCManager;
import org.brandao.brutos.old.programatic.WebFrameManager;
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
    public MvcRequestFactory getRequestFactory();

    /**
     * Obt�m a f�brica de resposta.
     * @return F�brica.
     */
    public MvcResponseFactory getResponseFactory();

    /**
     * @deprecated
     * @param iocManager
     */
    public void setIocManager(IOCManager iocManager);

    /**
     * @deprecated
     * @param webFrameManager
     */
    public void setWebFrameManager(WebFrameManager webFrameManager);

    /**
     * Define o gestor de interceptadores.
     * @param interceptorManager Gestor de interceptadores.
     */
    public void setInterceptorManager(InterceptorManager interceptorManager);


    /**
     * Obt�m o provedor da vis�o.
     * @return Provedor da vis�o.
     */
    public ViewProvider getViewProvider();

    /**
     * Obt�m o provedor das regras de valida��o.
     * @return Provedor das regras de valida��o
     */
    public ValidatorProvider getValidatorProvider();

    /**
     * Obt�m o respons�vel por executar as a��es.
     * @return Respons�vel por executar as a��es.
     */
    public Invoker getInvoker();

    /**
     * Define o responsável por executar as ações.
     * @param value Responsável por executar as ações.
     */
    public void setInvoker(Invoker value);

    /**
     * Define as configura��es da aplica��o.
     * @param config Configura��o.
     */
    public void setConfiguration( Properties config );

    /**
     * Obt�m a configura��o da aplica��o.
     * @param config Configura��o.
     */
    public Properties getConfiguration();

    /**
     * Define o provedor do container IOC.
     * @param iocProvider Provedor do container IOC.
     */
    public void setIocProvider(IOCProvider iocProvider);


    /**
     * Obt�m o gestor de interceptadores.
     * @return Gestor de interceptadores.
     */
    public InterceptorManager getInterceptorManager();

    /**
     * Obt�m o gestor de controladores.
     * @return Gestor de controladores.
     */
    public ControllerManager getControllerManager();

    /**
     * @deprecated
     * @return .
     */
    public IOCManager getIocManager();

    /**
     * @deprecated
     * @return .
     */
    public WebFrameManager getWebFrameManager();

    /**
     * Obt�m o provedor do container IOC.
     * @return Provedor do container IOC.
     */
    public IOCProvider getIocProvider();

    /**
     * Obt�m o respons�vel por resolver os controladores.
     * @return Respons�vel por resolver os controladores.
     */
    public ControllerResolver getControllerResolver();

    /**
     * Obt�m o respons�vel por resolver as a��es.
     * @return Respons�vel por resolver as a��es.
     */
    public ActionResolver getActionResolver();

    public CodeGeneratorProvider getCodeGeneratorProvider();

    public void setCodeGeneratorProvider(CodeGeneratorProvider codeGeneratorProvider);

}
