/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.brandao.brutos;

import org.brandao.brutos.io.Resource;
import org.brandao.brutos.io.ResourceLoader;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.mapping.Interceptor;

/**
 *
 * @author Cliente
 */
public class ComponentRegistryAdapter implements ComponentRegistry{

    private final ConfigurableApplicationContext configurableApplicationContext;
    
    public ComponentRegistryAdapter(
            ConfigurableApplicationContext configurableApplicationContext){
        this.configurableApplicationContext = configurableApplicationContext;
    }
    public ControllerBuilder registerController(Class classtype) {
        return this.configurableApplicationContext.getControllerManager().addController(classtype);
    }

    public ControllerBuilder registerController(String id, Class classType) {
        return this.configurableApplicationContext.getControllerManager().addController(id, classType);
    }

    public ControllerBuilder registerController(String id, String view, Class classType) {
        return this.configurableApplicationContext.getControllerManager().addController(id, view, classType);
    }

    public ControllerBuilder registerController(String id, String view, String name, Class classType, String actionId) {
        return this.configurableApplicationContext.getControllerManager().addController(id, view, name, classType, actionId);
    }

    public ControllerBuilder registerController(String id, String view, DispatcherType dispatcherType, String name, Class classType, String actionId) {
        return this.configurableApplicationContext.getControllerManager().addController(id, view, dispatcherType, name, classType, actionId);
    }

    public ControllerBuilder registerController(String id, String view, DispatcherType dispatcherType, String name, Class classType, String actionId, ActionType actionType) {
        return this.configurableApplicationContext.getControllerManager().addController(id, view, dispatcherType, name, classType, actionId, actionType);
    }

    public Controller getRegisteredController(Class clazz) {
        return this.configurableApplicationContext.getControllerManager().getController(clazz);
    }

    public Controller getRegisteredController(String name) {
        return this.configurableApplicationContext.getControllerManager().getController(name);
    }

    public InterceptorStackBuilder registerInterceptorStack(String name, boolean isDefault) {
        return this.configurableApplicationContext.getInterceptorManager().addInterceptorStack(name, isDefault);
    }

    public InterceptorBuilder registerInterceptor(String name, Class interceptor, boolean isDefault) {
        return this.configurableApplicationContext.getInterceptorManager().addInterceptor(name, interceptor, isDefault);
    }

    public Interceptor getRegisteredInterceptor(Class clazz) {
        return this.configurableApplicationContext.getInterceptorManager().getInterceptor(clazz);
    }

    public Interceptor getRegisteredInterceptor(String name) {
        return this.configurableApplicationContext.getInterceptorManager().getInterceptor(name);
    }

    public Resource getResource(String path) {
        return ((ResourceLoader)this.configurableApplicationContext).getResource(path);
    }

    public ClassLoader getClassloader() {
        return ((ResourceLoader)this.configurableApplicationContext).getClassloader();
    }
    
}
