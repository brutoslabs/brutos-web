/*
 * Brutos Web MVC http://www.brutosframework.com.br/
 * Copyright (C) 2009-2017 Afonso Brandao. (afonso.rbn@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
 * @author Brandao
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
