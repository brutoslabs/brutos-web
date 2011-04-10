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

package org.brandao.brutos.web;

import java.util.Properties;
import org.brandao.brutos.ActionResolver;
import org.brandao.brutos.ConfigurableApplicationContext;
import org.brandao.brutos.ControllerManager;
import org.brandao.brutos.ControllerResolver;
import org.brandao.brutos.InterceptorManager;
import org.brandao.brutos.Invoker;
import org.brandao.brutos.MvcRequestFactory;
import org.brandao.brutos.MvcResponseFactory;
import org.brandao.brutos.ioc.IOCProvider;
import org.brandao.brutos.old.programatic.IOCManager;
import org.brandao.brutos.old.programatic.WebFrameManager;
import org.brandao.brutos.validator.ValidatorProvider;
import org.brandao.brutos.view.ViewProvider;

/**
 *
 * @author Brandao
 */
public  abstract class ConfigurableWebApplicationContext
        extends WebApplicationContext implements ConfigurableApplicationContext{

    protected void loadIOCManager(IOCManager iocManager) {
    }

    protected void loadWebFrameManager(WebFrameManager webFrameManager) {
    }

    protected void loadInterceptorManager(InterceptorManager interceptorManager) {
    }

    protected void loadController(ControllerManager controllerManager) {
    }


    public void setIocManager(IOCManager iocManager) {
        this.iocManager = iocManager;
    }

    public WebFrameManager getWebFrameManager() {
        return webFrameManager;
    }

    public void setWebFrameManager(WebFrameManager webFrameManager) {
        this.webFrameManager = webFrameManager;
    }

    public InterceptorManager getInterceptorManager() {
        return interceptorManager;
    }

    public void setInterceptorManager(InterceptorManager interceptorManager) {
        this.interceptorManager = interceptorManager;
    }

    public ControllerManager getControllerManager() {
        return controllerManager;
    }

    public void setConfiguration( Properties config ){
        this.configuration = config;
    }

    public void setIocProvider(IOCProvider iocProvider) {
        this.iocProvider = iocProvider;
    }

    public MvcRequestFactory getRequestFactory() {
        return this.requestFactory;
    }

    public MvcResponseFactory getResponseFactory() {
        return this.responseFactory;
    }

    public ViewProvider getViewProvider() {
        return this.viewProvider;
    }

    public ValidatorProvider getValidatorProvider() {
        return this.validatorProvider;
    }

    public Invoker getInvoker() {
        return this.invoker;
    }

    public IOCManager getIocManager() {
        return this.iocManager;
    }

    public IOCProvider getIocProvider() {
        return this.iocProvider;
    }

    public ControllerResolver getControllerResolver() {
        return this.controllerResolver;
    }

    public ActionResolver getActionResolver() {
        return this.actionResolver;
    }

}
