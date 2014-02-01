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

package org.brandao.brutos.type;

import java.io.IOException;
import java.lang.reflect.Array;
import org.brandao.brutos.EnumerationType;
import org.brandao.brutos.MvcResponse;
import org.brandao.brutos.bean.EnumUtil;

/**
 * Implementação padrão do tipo {@link java.lang.Enum}
 * 
 * @author Brandao
 */
public class DefaultEnumType implements EnumType{

    private EnumerationType type;
    private Class classType;
    private Type intType;
    private Type stringType;
    private EnumUtil enumUtil;

    public DefaultEnumType() {
        intType    = new IntegerType();
        stringType = new StringType();
    }

    public EnumerationType getEnumType() {
        return type;
    }

    public void setEnumType(EnumerationType type) {
        this.type = type;
    }
    
    public Class getClassType() {
        return classType;
    }

    public void setClassType(Class classType) {
        this.classType = classType;
        this.enumUtil = new EnumUtil(classType);
    }

    public Object getValue(Object value) {
        return null;
    }
    
    public Object convert(Object value) {
        try{
            if( value == null )
                return null;
            else
            if( this.classType.isAssignableFrom(value.getClass()) )
                return value;
            else
            if( type == EnumerationType.ORDINAL ){
                Object constants =
                    this.enumUtil.getEnumConstants();

                return Array.get(
                    constants,
                    ((Integer)intType.convert( value )).intValue());
            }
            else
                return enumUtil
                    .valueOf( (String)stringType.convert( value ) );
        }
        catch( Exception e ){
            throw new UnknownTypeException(e);
        }
    }

    public void setValue(Object value) throws IOException {
    }
    
    public void show(MvcResponse response, Object value) throws IOException {
        response.process(value);
    }

}
