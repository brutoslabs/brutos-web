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
import org.brandao.brutos.ApplicationContext;
import org.brandao.brutos.Invoker;
import org.brandao.brutos.ScopeType;
import org.brandao.brutos.scope.Scope;

/**
 *
 * @author Brandao
 */
public class SerializableTypeImp implements SerializableType{

    private Class classType;

    public void setClassType(Class classType) {
        this.classType = classType;
    }

    public Object getValue(Object value) {
        return value;
    }

    public void setValue(Object value) throws IOException {
        ApplicationContext applicationContext =
                Invoker.getApplicationContext();
        Scope scope =
                applicationContext.getScopes().get(ScopeType.PARAM);

        scope.put("$actionResult", value);
    }

    public Class getClassType() {
        return this.classType;
    }

}
