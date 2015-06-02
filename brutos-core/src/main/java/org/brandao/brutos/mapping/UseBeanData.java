/*
 * Brutos Web MVC http://www.brutosframework.com.br/
 * Copyright (C) 2009-2012 Afonso Brandao. (afonso.rbn@gmail.com)
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

import org.brandao.brutos.ScopeType;
import org.brandao.brutos.scope.Scope;
import org.brandao.brutos.Scopes;
import org.brandao.brutos.type.ArrayType;
import org.brandao.brutos.type.CollectionType;
import org.brandao.brutos.type.Type;
import org.brandao.brutos.validator.Validator;

/**
 *
 * @author Afonso Brandao
 */
public abstract class UseBeanData {
    
    protected String name;
    
    protected ScopeType scopeType;

    protected Bean mapping;

    protected MetaBean metaBean;
    
    protected Object staticValue;

    protected Type type;

    protected Validator validate;

    protected boolean nullable;

    public UseBeanData() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bean getMapping() {
        return mapping;
    }

    public void setMapping(Bean mapping) {
        this.mapping = mapping;
    }

    public Object getValue(Object source){

        Object value = null;

        if( !isNullable() ){
        	if(this.metaBean != null){
                value = this.metaBean.getValue(this.name == null? null : this.name + mapping.getSeparator());
                value = this.type.convert(value);
        	}
        	else
            if( this.mapping != null ){
                value = this.mapping.getValue(this.name == null? null : this.name + mapping.getSeparator());
                value = this.type.convert(value);
            }
            else
            if(this.staticValue!= null)
                value = this.type.convert( this.staticValue );
            else
            if( this.type instanceof CollectionType || this.type instanceof ArrayType ){
                value = this.name == null? null : getScope().getCollection(this.name);
                value = this.type.convert( value );
            }
            else{
                value = this.name == null? null : getScope().get(this.name);
                value = this.type.convert( value );
            }
        }

        this.validate(source, value);
        
        return value;
    }

    protected abstract void validate(Object source, Object value);
    
    
    public Class getClassType(){
        if(type != null)
            return type.getClassType();
        else
        if(this.mapping != null)
            return this.mapping.getClassType();
        else
            return null;
    }
    
    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Scope getScope() {
        return Scopes.getCurrentScope(scopeType);
        /*
        Scopes scopes = Invoker.getCurrentApplicationContext().getScopes();
        Scope objectScope = scopes.get( scopeType.toString() );

        if( objectScope == null )
            throw new BrutosException( "scope not allowed in context: " + scopeType );

        return objectScope;
        */
    }

    public void setScopeType(ScopeType scope) {
        this.scopeType = scope;
    }

    public ScopeType getScopeType() {
        return this.scopeType;
    }

    public Validator getValidate() {
        return validate;
    }

    public void setValidate(Validator validate) {
        this.validate = validate;
    }

    public Object getStaticValue() {
        return staticValue;
    }

    public void setStaticValue(Object staticValue) {
        this.staticValue = staticValue;
    }

    public boolean isNullable() {
        return nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

	public MetaBean getMetaBean() {
		return metaBean;
	}

	public void setMetaBean(MetaBean metaBean) {
		this.metaBean = metaBean;
	}
    
}
