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
        return applicationContext.requestFactory;
    }

    public MvcResponseFactory getResponseFactory() {
        return applicationContext.responseFactory;
    }

    public void setIocManager(IOCManager iocManager) {
        applicationContext.iocManager = iocManager;
    }

    public void setWebFrameManager(WebFrameManager webFrameManager) {
        applicationContext.webFrameManager = webFrameManager;
    }

    public void setInterceptorManager(InterceptorManager interceptorManager) {
        applicationContext.interceptorManager = interceptorManager;
    }

    public ViewProvider getViewProvider() {
        return applicationContext.viewProvider;
    }

    public ValidatorProvider getValidatorProvider() {
        return applicationContext.validatorProvider;
    }

    public Invoker getInvoker() {
        return applicationContext.invoker;
    }

    public void setConfiguration(Properties config) {
        applicationContext.configuration = config;
    }

    public Properties getConfiguration() {
        return applicationContext.configuration;
    }

    public void setIocProvider(IOCProvider iocProvider) {
        applicationContext.iocProvider = iocProvider;
    }

    public InterceptorManager getInterceptorManager() {
        return applicationContext.interceptorManager;
    }

    public ControllerManager getControllerManager() {
        return applicationContext.controllerManager;
    }

    public IOCManager getIocManager() {
        return applicationContext.iocManager;
    }

    public WebFrameManager getWebFrameManager() {
        return applicationContext.webFrameManager;
    }

    public IOCProvider getIocProvider() {
        return applicationContext.iocProvider;
    }

    public ControllerResolver getControllerResolver() {
        return applicationContext.controllerResolver;
    }

    public ActionResolver getActionResolver() {
        return applicationContext.actionResolver;
    }

}
