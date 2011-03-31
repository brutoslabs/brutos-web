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

import java.lang.reflect.Type;
import org.brandao.brutos.type.json.JSONDecoder;
import org.brandao.brutos.type.json.JSONEncoder;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import org.brandao.brutos.ApplicationContext;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.MvcResponse;

/**
*
* @author vdesai
 *@author Brandao
*/
public class JSONType implements SerializableType {

    private Type classType;
    /*
    public Object getValue(HttpServletRequest arg0, ServletContext arg1, Object arg2) {
        try{
            if( arg2 instanceof String ){
                JSONDecoder decoder = new JSONDecoder( (String)arg2 );
                return decoder.decode( classType );
            }
            else
                return arg2;
        }
        catch( Exception e ){
            throw new BrutosException( e );
        }
    }

    public void setValue(HttpServletResponse arg0, ServletContext arg1, Object arg2) throws IOException {
        arg0.setContentType( "application/json; charset=UTF-8" );
        JSONEncoder encoder = new JSONEncoder( arg0.getOutputStream() );
        encoder.writeObject( arg2 );
        encoder.close();
    }
    */
    public Class getClassType() {
        return getClass(classType);
    }

    public void setClassType(Class classType) {
        this.classType = classType;
    }

    private Class getClass( java.lang.reflect.Type type ){
        if( type instanceof ParameterizedType )
            return (Class)((ParameterizedType)type).getRawType();
        else
            return (Class)type;
    }

    public Object getValue(Object value) {
         try{
            if( value instanceof String ){
                JSONDecoder decoder = new JSONDecoder( (String)value );
                return decoder.decode( classType );
            }
            else
                return value;
        }
        catch( Exception e ){
            throw new BrutosException( e );
        }
   }

    public void setValue(Object value) throws IOException {
        ApplicationContext app = ApplicationContext.getCurrentApplicationContext();
        MvcResponse response = app.getMvcResponse();
        response.setType( "application/json" );
        response.setCharacterEncoding( "UTF-8" );
        JSONEncoder encoder = new JSONEncoder( response.processStream() );
        encoder.writeObject( value );
        encoder.close();
    }

}