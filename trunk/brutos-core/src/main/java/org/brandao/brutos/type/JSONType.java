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

import org.brandao.brutos.type.json.JSONDecoder;
import org.brandao.brutos.type.json.JSONEncoder;
import java.io.IOException;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.ConfigurableApplicationContext;
import org.brandao.brutos.Invoker;
import org.brandao.brutos.MvcResponse;

/**
*
* @author vdesai
 *@author Brandao
*/
public class JSONType implements SerializableType {

    private Class classType;

    public Class getClassType() {
        return getClass(classType);
    }

    public void setClassType(Class classType) {
        this.classType = classType;
    }

    private Class getClass( Class type ){
        return Types.getRawType(type);
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
        ConfigurableApplicationContext app =
                (ConfigurableApplicationContext)Invoker.getApplicationContext();
        MvcResponse response = app.getMvcResponse();
        response.setType( "application/json" );
        response.setCharacterEncoding( "UTF-8" );
        JSONEncoder encoder = new JSONEncoder( response.processStream() );
        encoder.writeObject( value );
        encoder.close();
    }

}