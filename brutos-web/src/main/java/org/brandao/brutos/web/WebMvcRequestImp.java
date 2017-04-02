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

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import javax.servlet.ServletRequest;
import org.brandao.brutos.web.http.BrutosRequest;

public class WebMvcRequestImp implements WebMvcRequest{

    private ServletRequest request;

    public WebMvcRequestImp( ServletRequest request ){
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

    public ServletRequest getServletRequest() {
        return request;
    }

}
