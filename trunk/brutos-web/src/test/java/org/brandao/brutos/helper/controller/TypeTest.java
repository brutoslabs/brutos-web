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

package org.brandao.brutos.helper.controller;

import java.io.IOException;
import org.brandao.brutos.MvcResponse;
import org.brandao.brutos.type.AbstractType;
import org.brandao.brutos.type.Type;
import org.brandao.brutos.type.TypeFactory;

/**
 *
 * @author Brandao
 */
public class TypeTest 
    extends AbstractType implements TypeFactory{

    public Object convert(Object value) {
        return new SimpleBean();
    }

    public void show(MvcResponse response, Object value) throws IOException {
    }

    public Class getClassType() {
        return SimpleBean.class;
    }

    public Type getInstance() {
        return this;
    }

    public boolean matches(Class type) {
        return type == SimpleBean.class;
    }

}
