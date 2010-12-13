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

import org.brandao.brutos.BrutosException;
import org.brandao.brutos.bean.BeanInstance;
import org.brandao.brutos.bean.GetterProperty;
import org.brandao.brutos.bean.SetterProperty;

/**
 *
 * @author Afonso Brandao
 */
public class FieldForm {

    private UseBeanData bean;
    
    private boolean request;
    
    private boolean response;
    
    private boolean persistenceContext;

    private String name;
    
    public FieldForm() {
    }

    public Object getValue( Object source ){
        BeanInstance instance = null;
        try{
            instance = new BeanInstance( source );
            GetterProperty get = instance.getGetter(name);
            return get.get();
        }
        catch( Exception e ){
            throw new BrutosException(
                    "can not get property: " +
                    instance.getClassType().getName() + "." + name, e );
        }
    }

    public void setValue( Object source, Object value ){

        if( this.name == null )
            return;
        
        BeanInstance instance = null;
        try{
            instance = new BeanInstance( source );
            SetterProperty set = instance.getSetter(name);
            set.set(value);
        }
        catch( Exception e ){
            throw new BrutosException(
                    "can not set property: " +
                    instance.getClassType().getName() + "." + name, e );
        }
    }

    public UseBeanData getBean() {
        return bean;
    }

    public void setBean(UseBeanData bean) {
        this.bean = bean;
    }

    public boolean isRequest() {
        return request;
    }

    public void setRequest(boolean request) {
        this.request = request;
    }

    public boolean isResponse() {
        return response;
    }

    public void setResponse(boolean response) {
        this.response = response;
    }

    public boolean isPersistenceContext() {
        return persistenceContext;
    }

    public void setPersistenceContext(boolean persistenceContext) {
        this.persistenceContext = persistenceContext;
    }
    
    public boolean equals( Object o ){
        return o instanceof FieldForm? 
            ((FieldForm)o).name.equals( name ) :
            false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
