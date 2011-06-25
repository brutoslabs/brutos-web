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
import org.brandao.brutos.AbstractApplicationContext;
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

    /*
    public Object getValue( HttpServletRequest request, ServletContext context, Object value ) {
        if( value instanceof Boolean )
            return value;
        else
            return "true".equals( value );
    }
    */
    public Class getClassType() {
        return Boolean.TYPE;
    }

    /*
    public void setValue(HttpServletResponse response, ServletContext context, Object value) throws IOException {
        if( value instanceof Boolean ){
            PrintWriter out = response.getWriter();
            out.print( String.valueOf( value ) );
        }
    }
    */
    public Object getValue(Object value) {
        if( value instanceof Boolean )
            return value;
        else
            return "true".equals( value );
    }

    public void setValue(Object value) throws IOException {
        ConfigurableApplicationContext app = Invoker.getCurrentApplicationContext();
        MvcResponse response = app.getMvcResponse();
        response.process(value);
    }
}
