/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
 * @author Brandao
 */
public class ConfigurableApplicationContextImp extends ApplicationContextWrapper
        implements ConfigurableApplicationContext{

    public ConfigurableApplicationContextImp( ApplicationContext app ){
        super(app);
    }
    
    public MvcRequestFactory getRequestFactory() {
        return this.requestFactory;
    }

    public MvcResponseFactory getResponseFactory() {
        return this.responseFactory;
    }

    public void setIocManager(IOCManager iocManager) {
        this.iocManager = iocManager;
    }

    public void setWebFrameManager(WebFrameManager webFrameManager) {
        this.webFrameManager = webFrameManager;
    }

    public void setInterceptorManager(InterceptorManager interceptorManager) {
        this.interceptorManager = interceptorManager;
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

    public void setConfiguration(Properties config) {
        this.configuration = config;
    }

    public Properties getConfiguration() {
        return this.configuration;
    }

    public void setIocProvider(IOCProvider iocProvider) {
        this.iocProvider = iocProvider;
    }

    public InterceptorManager getInterceptorManager() {
        return this.interceptorManager;
    }

    public ControllerManager getControllerManager() {
        return this.controllerManager;
    }

    public IOCManager getIocManager() {
        return this.iocManager;
    }

    public WebFrameManager getWebFrameManager() {
        return this.webFrameManager;
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
