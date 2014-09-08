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
    
    protected String nome;
    
    protected ScopeType scopeType;

    protected Bean mapping;

    protected Object staticValue;

    protected Type type;

    protected Validator validate;

    protected boolean nullable;

    public UseBeanData() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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
            if( mapping != null )
                value = mapping.getValue(nome == null? null : nome + mapping.getSeparator());
            else
            if(staticValue!= null)
                value = type.convert( staticValue );
            else
            if( type instanceof CollectionType || type instanceof ArrayType ){
                value = nome == null? null : getScope().getCollection(nome);
                value = type.convert( value );
            }
            else{
                value = nome == null? null : getScope().get(nome);
                value = type.convert( value );
            }
        }

        this.validate(source, value);
        
        return value;
    }

    protected abstract void validate(Object source, Object value);
    
    
    public Class getClassType(){
        //if( type != null )
            return type == null? null : type.getClassType();
        //else
        //if( mapping != null )
        //    return mapping.getClassType();
        //else
        //    return null;
    }
    /*
    public Object getValue( ServletContext context, HttpServletRequest request ) throws InstantiationException, IllegalAccessException, ParseException{
        if( scope == ScopeType.REQUEST ){
            if( mapping == null ){
                return type.getValue( request, context, nome == null? null : getValue( request, nome ) );
            }
            else
                return mapping.getValue( request );
        }
        else
        if( scope == ScopeType.SESSION ){
            if( mapping == null )
                //return type.getValue( request, context, nome == null? null : getValue( request.getSession(), nome ) );
                return getValue( request.getSession(), nome );
            else
                return mapping.getValue( request.getSession() );
        }
        else
        if( scope == ScopeType.APPLICATION ){
            if( mapping == null )
                //return type.getValue( request, context, nome == null? null : getValue( context, nome ) );
                return getValue( context, nome );
            else
                return mapping.getValue( request.getSession() );
        }
        else{
            if( mapping == null )
                return type.getValue( request, context, nome == null? null : getValue( context, nome ) );
            else
                return mapping.getValue( context );
        }
            
    }

    private Object getValue( HttpServletRequest request, String name ){
        if( type instanceof CollectionType || type instanceof ArrayType )
            return ((BrutosRequest)request).getObjects( name );
        else
            return ((BrutosRequest)request).getObject(name);
    }

    private Object getValue( ServletContext context, String name ){
        return context.getAttribute(name);
    }

    private Object getValue( HttpSession session, String name ){
        return session.getAttribute(name);
    }
    */
    
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
}
