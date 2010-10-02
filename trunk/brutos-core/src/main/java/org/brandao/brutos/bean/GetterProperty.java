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

package org.brandao.brutos.bean;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 *
 * @author Afonso Brandao
 */
public class GetterProperty {

    private Field field;
    private Object object;
    private Method method;

    @Deprecated
    public GetterProperty( Field field, Object object ){
        this.field = field;
        this.object = object;
    }

    public GetterProperty( Method method, Object object ){
        this.method = method;
        this.object = object;
    }

    public Object get() throws Exception{
        /*
        String fieldName = field.getName();
        String pre = field.getType() == Boolean.TYPE? "is" : "get";
        String methodName = pre +
                            String.valueOf( fieldName.charAt( 0 ) ).toUpperCase() +
                            fieldName.substring( 1, fieldName.length() );

        Method getter = field.getDeclaringClass().getMethod( methodName );
        */
        return method.invoke( object );
    }

    public Method getMethod(){
        return method;
    }
}
