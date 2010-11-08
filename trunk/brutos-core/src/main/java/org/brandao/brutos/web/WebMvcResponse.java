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
import java.util.Map;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import org.brandao.brutos.*;

/**
 *
 * @author Afonso Brandao
 */
public class WebMvcResponse implements MvcResponse{

    private ServletResponse response;

    /*
    public static final String CONTENT_TYPE       = "ContentType";
    public static final String CONTENT_LENGTH     = "ContentLength";
    public static final String BUFFER_SIZE        = "BufferSize";
    public static final String CHARACTER_ENCODING = "CharacterEncoding";
    public static final String LOCALE             = "Locale";
    */
    
    public WebMvcResponse( ServletResponse response ){
        this.response = response;
    }

    public void process( Object object ){
        try{
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

    /*
    private void configure( ServletResponse response, Map config, Map info ){
        if( config != null ){
            response.setContentType( config.containsKey(CONTENT_TYPE)? (String)config.get( CONTENT_TYPE ) : "text/html"  );

            if( config.containsKey( CONTENT_LENGTH ) )
                response.setContentLength( ((Integer)config.get(CONTENT_LENGTH)).intValue() );

            if( config.containsKey( BUFFER_SIZE ) )
                response.setBufferSize( ((Integer)config.get(BUFFER_SIZE)).intValue() );

            if( config.containsKey( CHARACTER_ENCODING ) )
                response.setCharacterEncoding( (String)config.get(CHARACTER_ENCODING) );

            if( config.containsKey( LOCALE ) )
                response.setLocale( (Locale)config.get(LOCALE) );
        }

        if( info != null && response instanceof HttpServletResponse ){
            HttpServletResponse httpResponse = (HttpServletResponse)response;
            for(Object o: info.keySet() ){
                String key = String.valueOf( o );
                String value = String.valueOf(info.get( o ));
                httpResponse.addHeader(key, value);
            }
        }
    }
    */

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
}
