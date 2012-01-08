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

/**
 *
 * @author Afonso Brandao
 */
public class ObjectType implements SerializableType{

    private Type serializableType;
    private Class classType;

    public ObjectType() {
        this(null);
    }
    
    public ObjectType(Class classType) {
        this.serializableType = Types.getType( Serializable.class );
        this.classType = classType;
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

    public void setClassType(Class classType) {
        this.classType = classType;
    }
    
}
