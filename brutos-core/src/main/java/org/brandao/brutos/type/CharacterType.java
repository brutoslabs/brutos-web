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
import org.brandao.brutos.AbstractApplicationContext;
import org.brandao.brutos.Invoker;
import org.brandao.brutos.MvcResponse;

/**
 *
 * @author Afonso Brandao
 */
public class CharacterType implements Type{

    public CharacterType() {
    }
    /*
    public Object getValue( HttpServletRequest request, ServletContext context, Object value ) {
        if( value instanceof Character )
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
            return value == null? 
                (char)0 : 
                value.length() == 0?
                    (char)0 :
                    value.charAt( 0 );
        }
        catch( Exception e ){
            return null;
        }
    }
    /*
    public void setValue( HttpServletResponse response, ServletContext context, Object value ) throws IOException{
        if( value instanceof Character ){
            PrintWriter out = response.getWriter();
            out.print( String.valueOf( value ) );
        }
    }
    */
    public Class getClassType() {
        return Character.TYPE;
    }

    public Object getValue(Object value) {
        if( value instanceof Byte )
            return value;
        else
        if( value == null || value instanceof String )
            return toValue( (String)value );
        else
            throw new UnknownTypeException();
    }

    public void setValue(Object value) throws IOException {
        AbstractApplicationContext app = Invoker.getCurrentApplicationContext();
        MvcResponse response = app.getMvcResponse();
        response.process(value);
    }
    
}
