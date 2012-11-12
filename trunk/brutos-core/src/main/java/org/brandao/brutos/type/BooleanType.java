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
import org.brandao.brutos.ConfigurableApplicationContext;
import org.brandao.brutos.Invoker;
import org.brandao.brutos.MvcResponse;

/**
 *
 * @author Afonso Brandao
 */
public class BooleanType implements Type{

    public BooleanType() {
    }

    public Class getClassType() {
        return Boolean.TYPE;
    }

    /**
     * @deprecated 
     * @param value
     * @return 
     */
    public Object getValue(Object value) {
        if( value instanceof Boolean )
            return value;
        else
        if(value instanceof String)
            return Boolean.valueOf((String)value);
        else
        if( value == null )
            return null;
        else
            throw new UnknownTypeException(value.getClass().getName());
    }

    /**
     * @deprecated 
     * @param value
     * @throws IOException 
     */
    public void setValue(Object value) throws IOException {
        ConfigurableApplicationContext app = 
                (ConfigurableApplicationContext)Invoker.getCurrentApplicationContext();
        MvcResponse response = app.getMvcResponse();
        response.process(value);
    }

    public Object convert(Object value) {
        return getValue(value);
    }

    public void show(MvcResponse response, Object value) {
        response.process(value);
    }
}
