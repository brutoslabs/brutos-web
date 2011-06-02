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


package org.brandao.brutos.helper.controller;

import org.brandao.brutos.ScopeType;
import org.brandao.brutos.Scopes;
import org.brandao.brutos.interceptor.AbstractInterceptor;
import org.brandao.brutos.interceptor.InterceptedException;
import org.brandao.brutos.interceptor.InterceptorHandler;
import org.brandao.brutos.interceptor.InterceptorStack;
import org.brandao.brutos.scope.Scope;
import org.brandao.brutos.web.ContextLoader;

/**
 *
 * @author Brandao
 */
public class SimpleInterceptor extends AbstractInterceptor{

    public void intercepted(InterceptorStack stack, InterceptorHandler handler)
            throws InterceptedException {

        Scopes scopes = ContextLoader.getCurrentWebApplicationContext().getScopes();
        Scope scope = scopes.get(ScopeType.REQUEST);
        for(String key: this.props.keySet()){
            scope.put(key, this.props.get(key));
        }

        stack.next(handler);
    }

}
