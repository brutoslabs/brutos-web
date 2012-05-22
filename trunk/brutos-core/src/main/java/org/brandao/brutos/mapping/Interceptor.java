/*
 * Brutos Web MVC http://www.brutosframework.com.br/
 * Copyright (C) 2009 Afonso Brandao. (afonso.rbn@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
        Object instance = getName() == null? null : iocProvider.getBean(getName());
        instance = instance == null? iocProvider.getBean(getType()) : instance;

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
