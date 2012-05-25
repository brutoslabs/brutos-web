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

import org.brandao.brutos.BrutosException;
import org.brandao.brutos.bean.BeanInstance;

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
            /*GetterProperty get = instance.getGetter(name);
            return get.get();*/
            return instance.get(name);
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
            /*SetterProperty set = instance.getSetter(name);
            set.set(value);
            */
            instance.set(name, value);
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
