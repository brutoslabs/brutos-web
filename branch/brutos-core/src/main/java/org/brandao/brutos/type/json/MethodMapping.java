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

package org.brandao.brutos.type.json;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 *
 * @author Afonso Brandao
 */
class MethodMapping {

    private String id;

    private Method getter;

    private Method setter;

    MappingBean parent;

    private Type type;

    public MethodMapping(){
        this( null, null, null, null, null );
    }
    
    public MethodMapping( String id, Method getter, Method setter, MappingBean parent, Type type ){
        this.id = id;
        this.getter = getter;
        this.setter = setter;
        this.parent = parent;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getValue( Object obj )
            throws IllegalAccessException, IllegalArgumentException,
            InvocationTargetException{
        return getter.invoke(obj);
    }

    public Object setValue( Object value, Object obj )
            throws IllegalAccessException, IllegalArgumentException,
            InvocationTargetException{
        return setter.invoke(obj, value );
    }

    public MappingBean getParent() {
        return parent;
    }

    public void setParent(MappingBean parent) {
        this.parent = parent;
    }

    public Method getGetter() {
        return getter;
    }

    public void setGetter(Method getter) {
        this.getter = getter;
    }

    public Method getSetter() {
        return setter;
    }

    public void setSetter(Method setter) {
        this.setter = setter;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
