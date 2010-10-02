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
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.brandao.brutos.scope.Scope;

/**
 * Allows the creation types.
 *
 * @author Afonso Brandao
 */
public interface Type {

    /**
     * Converts a value to a defined type.
     * 
     * @param request Request object.
     * @param context Context object.
     * @param value Value to be converted. If the
     * value comes from the request is a string
     * otherwise you do not need the conversion.
     * @return Converted value.
     */
    public Object getValue( HttpServletRequest request, ServletContext context, Object value );

    /**
     * Converts the value to be sent by the response object.
     * @param response Response object.
     * @param context Context object.
     * @param value Value to be converted.
     * @throws IOException Thrown if there is a problem to send.
     */
    public void setValue( HttpServletResponse response, ServletContext context, Object value ) throws IOException;

    /**
     * Get the type of class
     * @return Class type.
     */
    public Class getClassType();

    /*
     * Converts a value to a defined type.
     * @since 1.4
     * @param scope Scope.
     * @param name Value name.
     * @param souce Source.
     * @return Converted value.
     */
    //public Object getValue( Scope scope, String name, Object source );

    /*
     * Converts the value to be sent by the response.
     * @since 1.4
     * @param scope Scope.
     * @param name Value name.
     * @param souce Source.
     * @param value Value.
     * @throws IOException Thrown if there is a problem to send.
     */
    //public void setValue( Scope scope, String name, Object source, Object value ) throws IOException;

}
