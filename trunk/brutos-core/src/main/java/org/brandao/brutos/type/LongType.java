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
import java.io.PrintWriter;
import org.brandao.brutos.ApplicationContext;
import org.brandao.brutos.MvcResponse;

/**
 *
 * @author Afonso Brandao
 */
public class LongType implements Type{

    public LongType() {
    }

    /*
    public Object getValue( HttpServletRequest request, ServletContext context, Object value ) {
        if( value instanceof Long )
            return value;
        else
        if( value == null || value instanceof String )
            return toValue( (String)value );
        else
            throw new UnknownTypeException();
    }
    */
    public Object toValue( String value ){
        try{
           return Long.parseLong( value );
        }
        catch( Exception e ){
            return 0L;
        }
    }
    /*
    public void setValue( HttpServletResponse response, ServletContext context, Object value ) throws IOException{
        PrintWriter out = response.getWriter();
        out.print( String.valueOf( value ) );
    }
    */
    public Class getClassType() {
        return Long.TYPE;
    }

    public Object getValue(Object value) {
        if( value instanceof Long )
            return value;
        else
        if( value == null || value instanceof String )
            return toValue( (String)value );
        else
            throw new UnknownTypeException();
    }

    public void setValue(Object value) throws IOException {
        ApplicationContext app = ApplicationContext.getCurrentApplicationContext();
        MvcResponse response = app.getMvcResponse();
        response.process(value);
    }
    
}
