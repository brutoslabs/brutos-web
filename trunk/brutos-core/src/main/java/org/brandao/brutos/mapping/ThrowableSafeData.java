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

package org.brandao.brutos.mapping;

import org.brandao.brutos.DispatcherType;

/**
 *
 * @author Afonso Brandao
 */
public class ThrowableSafeData {
    
    private Class<? extends Throwable> target;
    
    private String uri;
    
    private String parameterName;

    private boolean redirect;

    private DispatcherType dispatcher;

    public ThrowableSafeData() {
    }

    public Class<? extends Throwable> getTarget() {
        return target;
    }

    public void setTarget(Class<? extends Throwable> target) {
        this.target = target;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public boolean isRedirect() {
        return redirect;
    }

    public void setRedirect(boolean redirect) {
        this.redirect = redirect;
    }

    public DispatcherType getDispatcher() {
        return dispatcher;
    }

    public void setDispatcher(DispatcherType dispatcher) {
        this.dispatcher = dispatcher;
    }

}
