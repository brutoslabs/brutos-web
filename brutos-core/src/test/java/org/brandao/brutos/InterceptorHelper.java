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

import org.brandao.brutos.interceptor.AbstractInterceptor;
import org.brandao.brutos.interceptor.InterceptedException;
import org.brandao.brutos.interceptor.InterceptorHandler;
import org.brandao.brutos.interceptor.InterceptorStack;
import org.brandao.brutos.scope.Scopes;

/**
 *
 * @author Brandao
 */
public class InterceptorHelper {

    public static class InvalidInterceptor{
    }

    public static class Interceptor extends AbstractInterceptor{

        public void intercepted(InterceptorStack stack,
                InterceptorHandler handler) throws InterceptedException {
            
            Scopes.get(ScopeType.REQUEST)
                    .put("OK", true);
            Scopes.get(ScopeType.REQUEST)
                    .put("testProperty", this.props.get("prop"));
        }

    }


}
