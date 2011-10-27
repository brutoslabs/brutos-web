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

package org.brandao.brutos.type;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.ConfigurableApplicationContext;
import org.brandao.brutos.EnumerationType;
import org.brandao.brutos.Invoker;
import org.brandao.brutos.MvcResponse;
import org.brandao.brutos.bean.EnumUtil;

/**
 *
 * @author Afonso Brandao
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
        try{
            if( value == null )
                return null;
            else
            if( type == EnumerationType.ORDINAL ){
                Object constants =
                    this.enumUtil.getEnumConstants();

                return Array.get(
                    constants,
                    ((Integer)intType.getValue( value )).intValue());
            }
            else
                return enumUtil
                    .valueOf( (String)stringType.getValue( value ) );
        }
        catch( Exception e ){
            return null;
        }
    }

    public void setValue(Object value) throws IOException {
        ConfigurableApplicationContext app =
                (ConfigurableApplicationContext)Invoker.getApplicationContext();
        MvcResponse response = app.getMvcResponse();
        response.process(value);
    }

}
