/*
 * Brutos Web MVC http://brutos.sourceforge.net/
 * Copyright (C) 2009 Afonso Brandao. (afonso.rbn@gmail.com)
 *
 * This library is free software. You can redistribute it
 * and/or modify it under the terms of the GNU General Public
 * License (GPL) version 3.0 or (at your option) any later
 * version.
 * You may obtain a copy of the License at
 *
 * http://www.gnu.org/licenses/gpl.html
 *
 * Distributed WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied.
 *
 */

package org.brandao.brutos;

import java.util.Properties;
import org.brandao.brutos.ioc.IOCProvider;
import org.brandao.brutos.old.programatic.IOCManager;
import org.brandao.brutos.old.programatic.WebFrameManager;
import org.brandao.brutos.validator.ValidatorProvider;
import org.brandao.brutos.view.ViewProvider;

/**
 *
 * @author Afonso Brandao
 */
public interface HandlerApplicationContext {

    /**
     * Obtém a fábrica de requisição.
     * @return Fábrica.
     */
    public MvcRequestFactory getRequestFactory();

    /**
     * Obtém a fábrica de resposta.
     * @return Fábrica.
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
     * Obtém o provedor da visão.
     * @return Provedor da visão.
     */
    public ViewProvider getViewProvider();

    /**
     * Obtém o provedor das regras de validação.
     * @return Provedor das regras de validação
     */
    public ValidatorProvider getValidatorProvider();

    /**
     * Obtém o responsável por executar as ações.
     * @return Responsável por executar as ações.
     */
    public Invoker getInvoker();

    /**
     * Define as configurações da aplicação.
     * @param config Configuração.
     */
    public void setConfiguration( Properties config );

    /**
     * Obtém a configuração da aplicação.
     * @param config Configuração.
     */
    public Properties getConfiguration();

    /**
     * Define o provedor do container IOC.
     * @param iocProvider Provedor do container IOC.
     */
    public void setIocProvider(IOCProvider iocProvider);


    /**
     * Obtém o gestor de interceptadores.
     * @return Gestor de interceptadores.
     */
    public InterceptorManager getInterceptorManager();

    /**
     * Obtém o gestor de controladores.
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
     * Obtém o provedor do container IOC.
     * @return Provedor do container IOC.
     */
    public IOCProvider getIocProvider();

    /**
     * Obtém o responsável por resolver os controladores.
     * @return Responsável por resolver os controladores.
     */
    public ControllerResolver getControllerResolver();

    /**
     * Obtém o responsável por resolver as ações.
     * @return Responsável por resolver as ações.
     */
    public ActionResolver getActionResolver();

}
