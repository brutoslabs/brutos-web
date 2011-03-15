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

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import javax.servlet.ServletRequest;
import org.brandao.brutos.*;
import org.brandao.brutos.web.http.BrutosRequest;

/**
 *
 * @author Afonso Brandao
 */
public class WebMvcRequest implements MvcRequest{

    private ServletRequest request;

    public WebMvcRequest( ServletRequest request ){
        this.request = request;
    }
    
    public Object getValue(String name) {
        return ((BrutosRequest)request).getObject(name);
    }

    public Object getProperty(String name) {
        return request.getAttribute(name);
    }

    public InputStream getStream() throws IOException{
        return request.getInputStream();
    }

    public String getType() {
        return request.getContentType();
    }

    public int getLength() {
        return request.getContentLength();
    }

    public String getCharacterEncoding() {
        return request.getCharacterEncoding();
    }

    public Locale getLocale() {
        return request.getLocale();
    }

}
