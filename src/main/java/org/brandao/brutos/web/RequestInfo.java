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

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 
 * @author Brandao
 */
@Deprecated
public class RequestInfo {

    private static final ThreadLocal currentRequestInfo = new ThreadLocal();

    private ServletRequest request;
    private ServletResponse response;

    public ServletRequest getRequest() {
        return request;
    }

    public void setRequest(ServletRequest request) {
        this.request = request;
    }

    public ServletResponse getResponse() {
        return response;
    }

    public void setResponse(ServletResponse response) {
        this.response = response;
    }

    public static void setCurrent( RequestInfo requestInfo ){
        currentRequestInfo.set(requestInfo);
    }

    public static RequestInfo getCurrentRequestInfo(){
        return (RequestInfo) currentRequestInfo.get();
    }

    public static void removeCurrent(){
        currentRequestInfo.remove();
    }
}
