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
import org.brandao.brutos.ApplicationContext;
import org.brandao.brutos.DefaultResourceAction;
import org.brandao.brutos.Invoker;
import org.brandao.brutos.StackRequestElementImp;
import org.brandao.brutos.interceptor.ImpInterceptorHandler;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.mapping.MethodForm;

/**
 *
 * @author Brandao
 */
public abstract class ActionHandlerImp implements ActionHandler{

    private Object resource;
    private Controller form;
    private ApplicationContext app;
    private Invoker invoker;

    public ActionHandlerImp(Object resource, Controller form,
            ApplicationContext app, Invoker invoker){
        this.resource = resource;
        this.app = app;
        this.form = form;
        this.invoker = invoker;
    }
    
    public Object invoke(Object self, Method thisMethod, Method proceed,
            Object[] args) throws Throwable {

        MethodForm methodForm = form.getMethod(thisMethod);

        DefaultResourceAction resourceAction =
                new DefaultResourceAction(methodForm);

        ImpInterceptorHandler handler = new ImpInterceptorHandler();

        handler.setContext(app);
        handler.setResourceAction(resourceAction);
        handler.setResource( resource );
        
        StackRequestElementImp stackRequestElementImp =
                new StackRequestElementImp();

        stackRequestElementImp.setAction(resourceAction);
        stackRequestElementImp.setController(form);
        stackRequestElementImp.setHandler(handler);
        stackRequestElementImp.setParameters(args);
        stackRequestElementImp.setResource(resource);

        invoker.invoke(stackRequestElementImp);

        return stackRequestElementImp.getResultAction();
    }
}
