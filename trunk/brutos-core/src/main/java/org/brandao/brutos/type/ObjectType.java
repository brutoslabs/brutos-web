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
import java.io.Serializable;
import org.brandao.brutos.MvcResponse;

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
        this.serializableType = TypeManager.getType( Serializable.class );
        this.classType = classType;
    }

    public Class getClassType() {
        return classType;
    }

    public Object getValue(Object value) {
        return null;
    }
    
    public Object convert(Object value) {
        return value;
    }

    public void setValue(Object value) throws IOException {
    }
    
    public void show(MvcResponse response, Object value) throws IOException {
        serializableType.show(response, value);
    }

    public void setClassType(Class classType) {
        this.classType = classType;
    }
    
}
