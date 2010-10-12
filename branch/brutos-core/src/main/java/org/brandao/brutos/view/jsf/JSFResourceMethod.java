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

package org.brandao.brutos.view.jsf;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import javax.el.ELContext;
import javax.el.MethodExpression;
import javax.el.MethodInfo;
import javax.faces.component.UIComponent;
import javax.faces.component.UIParameter;
import org.brandao.brutos.ResourceMethod;

/**
 *
 * @author Afonso Brandao
 */
public class JSFResourceMethod implements ResourceMethod{

    private MethodExpression methodExpression;
    private Method method;
    private Class resourceClass;
    private ELContext context;
    private MethodInfo methodInfo;
    private UIComponent command;

    public JSFResourceMethod( MethodExpression methodExpression, ELContext context, UIComponent command ){
        this.methodExpression = methodExpression;
        this.context = context;
        this.method = null;
        this.resourceClass = null;
        this.methodInfo = methodExpression.getMethodInfo( context );
        this.command = command;
    }
    
    @Override
    public Object invoke(Object source, Object[] args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        return methodExpression.invoke( context , args == null? getParameters() : args );
    }

    private Object[] getParameters(){
        List<Object> values = new ArrayList<Object>();
        for( UIComponent child: command.getChildren() ){
            if( child instanceof UIParameter ){
                UIParameter param = (UIParameter)child;
                values.add( param.getValue() );
            }
        }
        return values.toArray();
    }

    @Override
    public Method getMethod() {
        return this.method;
    }

    @Override
    public Class returnType() {
        return methodInfo.getReturnType();
    }

    @Override
    public Class[] getParametersType() {
        return methodInfo.getParamTypes();
    }

    @Override
    public Class getResourceClass() {
        return this.resourceClass;
    }

}
