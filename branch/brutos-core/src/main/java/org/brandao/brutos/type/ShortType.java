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
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Afonso Brandao
 */
public class ShortType implements Type{

    public ShortType() {
    }

    public Object getValue( HttpServletRequest request, ServletContext context, Object value ) {
        if( value instanceof Double )
            return value;
        else
        if( value == null || value instanceof String )
            return toValue( (String)value );
        else
            throw new UnknownTypeException();
    }
    
    public Object toValue( String value ){
        try{
           return Short.parseShort( value );
        }
        catch( Exception e ){
            return 0;
        }
    }

    public void setValue( HttpServletResponse response, ServletContext context, Object value ) throws IOException{
        PrintWriter out = response.getWriter();
        out.print( String.valueOf( value ) );
    }
    
    public Class getClassType() {
        return Short.TYPE;
    }
    
}
