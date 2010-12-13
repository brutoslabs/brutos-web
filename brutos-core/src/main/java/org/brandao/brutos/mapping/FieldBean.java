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
import org.brandao.brutos.EnumerationType;
import org.brandao.brutos.ScopeType;
import org.brandao.brutos.scope.Scope;
import org.brandao.brutos.scope.Scopes;
import org.brandao.brutos.type.Type;
import org.brandao.brutos.validator.Validator;

/**
 *
 * @author Afonso Brandao
 */
public class FieldBean {

    private String name;

    private String parameterName;
    
    private Type type;
    
    private String mapping;
    
    private EnumerationType enumProperty;
    
    private String temporalType;
    
    private ScopeType scopeType;

    private Validator validator;

    private Object value;
    
    public FieldBean() {
    }

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getMapping() {
        return mapping;
    }

    public void setMapping(String mapping) {
        this.mapping = mapping;
    }

    public EnumerationType getEnumProperty() {
        return enumProperty;
    }

    public void setEnumProperty(EnumerationType enumProperty) {
        this.enumProperty = enumProperty;
    }

    public String getTemporalType() {
        return temporalType;
    }

    public void setTemporalType(String temporalType) {
        this.temporalType = temporalType;
    }

    public Scope getScope() {
        Scope objectScope = Scopes.get( scopeType.toString() );

        if( objectScope == null )
            throw new BrutosException( "scope not allowed in context: " + scopeType );

        return objectScope;
    }

    public void setScopeType(ScopeType scope) {
        this.scopeType = scope;
    }

    public ScopeType getScopeType() {
        return this.scopeType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Validator getValidator() {
        return validator;
    }

    public void setValidator(Validator validator) {
        this.validator = validator;
    }

    public boolean isStatic(){
        return getValue() != null;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
