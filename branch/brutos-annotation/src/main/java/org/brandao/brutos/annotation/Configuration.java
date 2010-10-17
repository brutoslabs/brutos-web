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

package org.brandao.brutos.annotation;

import java.util.List;

/**
 *
 * @author Afonso Brandao
 */
public abstract class Configuration<T> {

    protected T resource;
    protected List<Class> source;

    public void setResource( T resource ){
        this.resource = resource;
    }

    public T getResource(){
        return this.resource;
    }

    public void setSource( List<Class> source ){
        this.source = source;
    }

    public List<Class> getSource(){
        return this.source;
    }
    
    public abstract void configure();
    
}
