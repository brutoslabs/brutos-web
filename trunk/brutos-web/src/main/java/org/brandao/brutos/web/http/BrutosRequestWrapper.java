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

package org.brandao.brutos.web.http;

import java.io.IOException;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * 
 * @author Brandao
 */
public class BrutosRequestWrapper extends HttpServletRequestWrapper implements BrutosRequest{

    private BrutosRequest request;

    public BrutosRequestWrapper( BrutosRequest request ){
        super( (HttpServletRequest)request.getServletRequest() );
        this.request = request;
    }

    public Object getObject(String name) {
        return request.getObject(name);
    }

    public List<Object> getObjects(String name) {
        return request.getObjects(name);
    }

    public UploadListener getUploadListener() {
        return request.getUploadListener();
    }

    public void parseRequest() throws IOException {
        request.parseRequest();
    }

    public void setServletRequest(ServletRequest request0){
        request.setServletRequest(request0);
    }
    
    public ServletRequest getServletRequest() {
        return request.getServletRequest();
    }

    public void setParameter(String name, String value) {
        request.setParameter(name, value);
    }

    public void setParameters(String name, String[] values) {
        request.setParameters(name, values);
    }

    public String[] getParameterValues(String name){
        return request.getParameterValues(name);
    }

    public String getParameter(String name){
        return request.getParameter(name);
    }
    
    public void setObject(String name, Object value) {
        request.setObject(name, value);
    }

    public void setObjects(String name, Object[] value) {
        request.setObjects(name, value);
    }

    public String getRequestId() {
        return request.getRequestId();
    }

    @SuppressWarnings("rawtypes")
	public Enumeration getParametersNames(){
    	return this.request.getParameterNames();
    }
    
}
