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
import org.brandao.brutos.EnumerationType;
import org.brandao.brutos.scope.Scope;
import org.brandao.brutos.type.Type;
import org.brandao.brutos.validator.Validator;
import org.brandao.brutos.validator.ValidatorException;

/**
 *
 * @author Brandao
 */
public class DependencyBean {

    protected String parameterName;

    protected Type type;

    protected String mapping;

    protected EnumerationType enumProperty;

    protected String temporalType;

    protected Scope scope;

    protected Validator validator;

    protected Object value;

    protected Bean mappingBean;

    protected boolean nullable;
    
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
            this.mapping != null?
                this.mappingBean.getForm().getBean( mapping ).getClassType() :
                this.type == null? null : this.type.getClassType();
    }

    public Object getValue(String prefix, long index, 
            ValidatorException exceptionHandler){
        return getValue(prefix, index, exceptionHandler, null);
    }
    
    public Object getValue(String prefix, long index, 
            ValidatorException exceptionHandler, Object value){
        
        try{
            return getValue0(prefix, index, exceptionHandler, value);
        }
        catch( ValidatorException e ){
            throw e;
        }
        catch( Exception e ){
            throw new DependencyException(
                String.format("problem to resolve dependency: %s",
                    new Object[]{this.getParameterName()}),
                    e);
        }
        
    }    
    public Object getValue0(String prefix, long index, 
            ValidatorException exceptionHandler, Object value){
        
        Object result;

        if( mapping == null ){
            
            if( isStatic() )
                result = getValue();
            else{
                String pre   = prefix != null? prefix : "";
                String param = getParameterName();
                String idx   = index < 0?
                                    "" :
                                    mappingBean.getIndexFormat().replace(
                                        "$index",
                                        String.valueOf(index) );

                String key = pre + param + idx;

                result = getScope().get(key);
                
            }
            
            result = 
                isNullable()? 
                    null : 
                    type.convert( result );
                    //type.getValue( result );
            
        }
        else{
            Bean dependencyBean =
                this.mappingBean
                    .getForm().getBean( mapping );

            if( dependencyBean == null )
                throw new BrutosException( "mapping not found: " + mapping );

            String newPrefix = null;
            if(mappingBean.isHierarchy()){
                String parameter = getParameterName();
                if(!(prefix == null && parameter == null)){
                    newPrefix = prefix == null? "" : prefix;
                    newPrefix += parameter == null? "" : parameter + mappingBean.getSeparator();
                }
            }

            result = dependencyBean.getValue(
                value,
                newPrefix,
                index,
                exceptionHandler,
                false);
            
        }

        try{
            if( validator != null )
                validator.validate(this, result);
        }
        catch( ValidatorException vex ){
            if( exceptionHandler == null )
                throw vex;
            else{
                exceptionHandler.addCause(vex);
                return null;
            }
        }

        return result;
    }

    public boolean isNullable() {
        return nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }
}
