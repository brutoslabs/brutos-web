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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.brandao.brutos.mapping.MethodForm;

/**
 *
 * @author Afonso Brandao
 */
public class DefaultResourceAction implements ResourceAction{

    MethodForm method;

    public DefaultResourceAction( MethodForm method ){
        this.method = method;
   }

    public Object invoke(Object source, Object[] args)
        throws IllegalAccessException, IllegalArgumentException,
        InvocationTargetException {

        return method.invoke( source , args);
    }

    public Class getResourceClass() {
        return method.getMethod() == null?
                                null :
                                method.getMethod().getDeclaringClass();
    }

    public Method getMethod() {
        return method.getMethod();
    }

    public Class returnType() {
        return method.getMethod().getReturnType();
    }

    public Class[] getParametersType() {
        return method.getMethod().getParameterTypes();
    }

    public boolean isAbstract() {
        return this.method.isAbstract();
    }
    
}
