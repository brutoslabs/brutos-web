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
import java.io.Serializable;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Afonso Brandao
 */
public class ObjectType implements Type{

    private Type serializableType;
    private Class classType;

    public ObjectType( Class classType ) {
        this.serializableType = Types.getType( Serializable.class );
        this.classType = classType;
    }

    public Object getValue( HttpServletRequest request, ServletContext context, Object value ) {
        return value;
    }
    
    public void setValue( HttpServletResponse response, ServletContext context, Object value ) throws IOException{
        serializableType.setValue(response, context, value);
    }
    
    public Class getClassType() {
        return classType;
    }

    public Object getValue(Object value) {
        return value;
    }

    public void setValue(Object value) throws IOException {
        serializableType.setValue(value);
    }
    
}
