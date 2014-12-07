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

package org.brandao.brutos.web;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Locale;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import org.brandao.brutos.*;

/**
 *
 * @author Afonso Brandao
 */
public class WebMvcResponseImp implements WebMvcResponse{

    private ServletResponse response;

    public WebMvcResponseImp( ServletResponse response ){
        this.response = response;
    }

    public void process( Object object ){
        try{
            if( object == null )
                return;
            
            PrintWriter out = response.getWriter();
            out.print( String.valueOf( object ) );
        }
        catch( Exception e ){
            throw new BrutosException( e );
        }
    }

    public OutputStream processStream(){
        try{
            return response.getOutputStream();
        }
        catch( Exception e ){
            throw new BrutosException( e );
        }
    }

    public void setInfo(String name, String value) {
        if( response instanceof HttpServletResponse )
            ((HttpServletResponse)response).addHeader(name, value);
    }

    public String getType() {
        return response.getContentType();
    }

    public int getLength() {
        return -1;
    }

    public String getCharacterEncoding() {
        return response.getCharacterEncoding();
    }

    public Locale getLocale() {
        return response.getLocale();
    }

    public void setLocale(Locale value) {
        response.setLocale(value);
    }

    public void setType(String value) {
        response.setContentType(value);
    }

    public void setLength(int value) {
        response.setContentLength(value);
    }

    public void setCharacterEncoding(String value) {
        response.setCharacterEncoding(value);
    }

    public ServletResponse getServletResponse() {
        return response;
    }
}
