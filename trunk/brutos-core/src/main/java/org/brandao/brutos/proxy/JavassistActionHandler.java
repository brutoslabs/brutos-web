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
import org.brandao.brutos.ApplicationContext;
import org.brandao.brutos.Invoker;
import org.brandao.brutos.mapping.Form;

/**
 *
 * @author Brandao
 */
public class JavassistActionHandler extends ActionHandlerImp
        implements MethodHandler{

    public JavassistActionHandler(Object resource, Form form,
            ApplicationContext app, Invoker invoker){
        super(resource,form,app,invoker);
    }

}
