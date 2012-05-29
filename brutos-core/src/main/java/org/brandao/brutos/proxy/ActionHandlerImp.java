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


package org.brandao.brutos.proxy;

import java.lang.reflect.Method;
import org.brandao.brutos.ConfigurableApplicationContext;
import org.brandao.brutos.DefaultResourceAction;
import org.brandao.brutos.DispatcherType;
import org.brandao.brutos.Invoker;
import org.brandao.brutos.RedirectException;
import org.brandao.brutos.StackRequestElementImp;
import org.brandao.brutos.interceptor.ImpInterceptorHandler;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.mapping.Action;

/**
 *
 * @author Brandao
 */
public abstract class ActionHandlerImp implements ActionHandler{

    private Object resource;
    private Controller controller;
    private ConfigurableApplicationContext context;
    private Invoker invoker;

    public ActionHandlerImp(Object resource, Controller controller,
            ConfigurableApplicationContext context, Invoker invoker){
        this.resource = resource;
        this.context = context;
        this.controller = controller;
        this.invoker = invoker;
    }
    
    public Object invoke(Object self, Method thisMethod, Method proceed,
            Object[] args) throws Throwable {
        Action action = controller.getMethod(thisMethod);
        return invoker.invoke(
            controller,
            context.getActionResolver().getResourceAction(action),
            resource,
            args);
    }
}
