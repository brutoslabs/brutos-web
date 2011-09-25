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

import java.util.Map;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.ioc.IOCProvider;

/**
 *
 * @author Afonso Brandao
 */
public class Interceptor {
    
    private String name;
    
    private Class type;
    
    private Map properties;
    
    boolean def;
    
    private Interceptor parent;
    
    public Interceptor( Interceptor parent ) {
        this.parent = parent;
    }
    
    public Interceptor() {
    }

    public Object getInstance(IOCProvider iocProvider){
        Object instance = iocProvider.getBean(getType());
        instance =
            instance == null? iocProvider.getBean(getName()) : instance;

        if( instance == null )
            throw new BrutosException("can't get instance " + getName() + ":" + getType());
        else
            return instance;
    }

    public String getName() {
        return parent == null? name : parent.getName();
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class getType() {
        return parent == null? type : parent.getType();
    }

    public void setType(Class type) {
        this.type = type;
    }

    public Map getProperties() {
        return properties;
    }

    public void setProperty( String name, Object value ){
        properties.put( name, value );
    }
    
    public void setProperties(Map properties) {
        this.properties = properties;
        
    }
    
    public Object getProperty( String name ){
        Object value = properties.get( name );
        value = value == null && parent != null? parent.getProperty( name ) : value;
        return  value;
    }
    
    public void setDefault( boolean value ){
        this.def = value;
    }
    
    public boolean isDefault(){
        return parent == null? this.def : parent.isDefault();
    }
    
    public boolean equals( Object o ){
        return o instanceof Interceptor?
                    ((Interceptor)o).getName().equals( getName() ) : 
                    false;
    }
}
