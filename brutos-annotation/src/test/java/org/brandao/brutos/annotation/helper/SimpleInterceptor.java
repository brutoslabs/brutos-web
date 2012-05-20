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

package org.brandao.brutos.annotation.helper;

import java.util.Map;
import org.brandao.brutos.annotation.Intercepts;
import org.brandao.brutos.interceptor.InterceptedException;
import org.brandao.brutos.interceptor.Interceptor;
import org.brandao.brutos.interceptor.InterceptorHandler;
import org.brandao.brutos.interceptor.InterceptorStack;

/**
 *
 * @author Brandao
 */
@Intercepts
public class SimpleInterceptor implements Interceptor{

    public void setProperties(Map map) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isConfigured() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void intercepted(InterceptorStack is, InterceptorHandler ih) throws InterceptedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean accept(InterceptorHandler ih) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
