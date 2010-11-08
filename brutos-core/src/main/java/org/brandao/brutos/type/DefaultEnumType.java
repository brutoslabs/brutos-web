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
import java.io.PrintWriter;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.brandao.brutos.ApplicationContext;
import org.brandao.brutos.EnumerationType;
import org.brandao.brutos.MvcResponse;

/**
 *
 * @author Afonso Brandao
 */
public class DefaultEnumType implements EnumType{

    private EnumerationType type;
    
    private Class<?> classType;
    
    private Type intType;
    
    private Type stringType;
    
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

    public Object getValue( HttpServletRequest request, ServletContext context, Object value ) {
        try{
            if( type == EnumerationType.ORDINAL )
                return classType.getEnumConstants()[ (Integer)intType.getValue( request, context, value ) ];
            else
                return Enum.valueOf( (Class)classType, (String)stringType.getValue( request, context, value ) );
        }
        catch( Exception e ){
            return null;
        }
    }

    public void setValue( HttpServletResponse response, ServletContext context, Object value ) throws IOException{
        PrintWriter out = response.getWriter();
        out.print( String.valueOf( value ) );
    }
    
    public Class<?> getClassType() {
        return classType;
    }

    public void setClassType(Class classType) {
        this.classType = classType;
    }

    public Object getValue(Object value) {
        try{
            if( type == EnumerationType.ORDINAL )
                return classType.getEnumConstants()[ (Integer)intType.getValue( value ) ];
            else
                return Enum.valueOf( (Class)classType, (String)stringType.getValue( value ) );
        }
        catch( Exception e ){
            return null;
        }
    }

    public void setValue(Object value) throws IOException {
        ApplicationContext app = ApplicationContext.getCurrentApplicationContext();
        MvcResponse response = app.getMvcResponse();
        response.process(value);
    }

}
