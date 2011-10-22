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

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 *
 * @author Brandao
 */
public class GenericTypeImp implements ParameterizedType{


        private Type rawType;
        private Type[] typeArguments;

        public GenericTypeImp( Class rawType, Class[] typeArguments ){
            this.rawType = rawType;
            this.typeArguments = typeArguments;
        }

        public Type[] getActualTypeArguments() {
            return typeArguments;
        }

        public Type getRawType() {
            return rawType;
        }

        public Type getOwnerType() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

}
