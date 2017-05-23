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

import javax.servlet.http.HttpServletRequest;

import org.brandao.brutos.DefaultMvcRequest;

/**
 * 
 * @author Brandao
 */
public class WebMvcRequestImp extends DefaultMvcRequest{

    private HttpServletRequest request;

    public WebMvcRequestImp(HttpServletRequest request){
        this.request = request;
        super.setType(MediaType.valueOf(request.getContentType()));
    }

	public InputStream getStream() throws IOException {
		return request.getInputStream();
	}
    
	public Object getHeader(String value) {
		Object r = super.getHeader(value);
		return r == null? request.getHeader(value) : r;
	}

	public Object getParameter(String name) {
		Object r = super.getParameter(name);
		return r == null? request.getParameter(name) : r;
	}

	public Object getProperty(String name) {
		Object r = super.getProperty(name);
		return r == null? request.getAttribute(name) : r;
	}
    
	public void setProperty(String name, Object value) {
		request.setAttribute(name, String.valueOf(value));
	}
    
}
