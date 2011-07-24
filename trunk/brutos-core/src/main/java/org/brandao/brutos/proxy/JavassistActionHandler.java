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

import javassist.util.proxy.MethodHandler;
import org.brandao.brutos.ConfigurableApplicationContext;
import org.brandao.brutos.DispatcherType;
import org.brandao.brutos.Invoker;
import org.brandao.brutos.mapping.Controller;

/**
 *
 * @author Brandao
 */
public class JavassistActionHandler extends ActionHandlerImp
        implements MethodHandler{

    public JavassistActionHandler(Object resource, Controller form,
            ConfigurableApplicationContext context, Invoker invoker,
            DispatcherType dispatcherType){
        super(resource,form,context,invoker,dispatcherType);
    }

}
