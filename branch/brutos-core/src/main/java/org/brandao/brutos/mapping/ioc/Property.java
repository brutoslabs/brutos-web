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

/**
 *
 * @author Afonso Brandao
 */
public class Property {
    
    private Injectable key;
    
    private Injectable value;
    
    public Property() {
    }

    public Property( Injectable key, Injectable value ) {
        this.setKey(key);
        this.setValue(value);
    }

    public Injectable getKey() {
        return key;
    }

    public void setKey(Injectable key) {
        this.key = key;
    }

    public Injectable getValue() {
        return value;
    }

    public void setValue(Injectable value) {
        this.value = value;
    }
    
}
