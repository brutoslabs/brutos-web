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
import java.io.OutputStream;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.brandao.brutos.*;

/**
 * 
 * @author Brandao
 */
public class WebMvcResponseImp
	extends DefaultMvcResponse
	implements WebMvcResponse {

    private HttpServletResponse response;

    public WebMvcResponseImp(HttpServletResponse response, MvcRequest request){
        this.response = response;
        this.request  = request;
    }

	public ServletResponse getServletResponse() {
		return this.response;
	}

	public OutputStream processStream() throws IOException {
		return this.response.getOutputStream();
	}
	
	public void setHeader(String name, Object value){
		this.response.addHeader(name, String.valueOf(value));
	}
	
}
