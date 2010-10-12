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

package org.brandao.brutos.mapping.ioc;

import java.lang.reflect.Method;

/**
 *
 * @author Afonso Brandao
 */
public class PropertyInject {
    
    private Injectable property;
    
    private String name;
    
    private Method method;
    
    public PropertyInject() {
    }

    public PropertyInject( String name, Injectable property, Method method ) {
        this.name = name;
        this.property = property;
        this.method = method;
    }
    
    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Injectable getProperty() {
        return property;
    }

    public void setProperty(Injectable property) {
        this.property = property;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
