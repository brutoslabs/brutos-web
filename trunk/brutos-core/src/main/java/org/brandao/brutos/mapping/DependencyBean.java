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
import org.brandao.brutos.scope.Scope;
import org.brandao.brutos.type.Type;
import org.brandao.brutos.validator.Validator;

/**
 *
 * @author Brandao
 */
public class DependencyBean {

    private String parameterName;

    private Type type;

    private String mapping;

    private EnumerationType enumProperty;

    private String temporalType;

    private Scope scope;

    private Validator validator;

    private Object value;

    private Bean mappingBean;

    public DependencyBean(Bean mappingBean) {
        this.mappingBean = mappingBean;
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

    public Bean getMappingBean() {
        return mappingBean;
    }

    public void setMappingBean(Bean mappingBean) {
        this.mappingBean = mappingBean;
    }

    public Scope getScope() {
        return scope;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }

    public Class getClassType(){
        return
            this.mappingBean != null?
                this.mappingBean.getClassType() :
                this.type == null? null : this.type.getClassType();
    }

    public Object getValue(String prefix, long index){
        Object result;

        if( mapping == null ){
            Object val = isStatic()?
                getValue() :
                getScope().get(
                        (prefix != null? prefix : "") +
                        getParameterName() +
                            //( index < 0? "" : "[" + index + "]" ) );
                            ( index < 0?
                                "" :
                                mappingBean.getIndexFormat().replace(
                                    "$index",
                                    String.valueOf(index) ) ) );

            result = type.getValue( val );
        }
        else{
            Bean dependencyBean =
                this.mappingBean
                    .getForm().getMappingBean( mapping );

            if( dependencyBean == null )
                throw new BrutosException( "mapping not found: " + mapping );

            result = dependencyBean.getValue(
                null,
                mappingBean.isHierarchy()?
                    prefix != null?
                        prefix + parameterName + mappingBean.getSeparator() :
                        parameterName + mappingBean.getSeparator()
                    :
                    null );

        }

        if( validator != null )
            validator.validate(this, result);

        return result;
    }
}
