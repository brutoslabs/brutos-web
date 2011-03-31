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

/**
 *
 * @author Afonso Brandao
 */
public class NullType implements Type{

    public NullType() {
    }

    /*
    public Object getValue( HttpServletRequest request, ServletContext context, Object value ) {
        return null;
    }

    public void setValue( HttpServletResponse response, ServletContext context, Object value ) throws IOException{
    }
    */
    public Class getClassType() {
        return null;
    }

    public Object getValue(Object value) {
        return null;
    }

    public void setValue(Object value) throws IOException {
    }
    
}
