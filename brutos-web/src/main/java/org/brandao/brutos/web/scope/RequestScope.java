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

package org.brandao.brutos.web.scope;

import java.util.Arrays;

import javax.servlet.ServletRequest;

import org.brandao.brutos.scope.Scope;
import org.brandao.brutos.web.RequestInfo;
import org.brandao.brutos.web.http.ParameterList;

/**
 * 
 * @author Brandao
 */
public class RequestScope implements Scope{
    
    public RequestScope() {
    }

    public void put(String name, Object value) {
        ServletRequest request = getServletRequest();
        request.setAttribute(name, value);
    }

    public Object get(String name) {
        ServletRequest request = getServletRequest();
        return request.getAttribute(name);
    }

    public Object getCollection( String name ){
        ServletRequest request = getServletRequest();
        return new ParameterList(
                Arrays.asList(request.getParameterValues(name)));
    }

    public void remove( String name ){
        ServletRequest request = getServletRequest();
        request.removeAttribute(name);
    }

    private ServletRequest getServletRequest(){
        RequestInfo requestInfo = RequestInfo.getCurrentRequestInfo();
        return requestInfo.getRequest();
        
    }

}
