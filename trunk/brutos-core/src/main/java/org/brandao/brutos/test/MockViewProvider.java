/*
 * Brutos Web MVC http://www.brutosframework.com.br/
 * Copyright (C) 2009-2012 Afonso Brandao. (afonso.rbn@gmail.com)
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

package org.brandao.brutos.test;

import java.util.Properties;
import org.brandao.brutos.DispatcherType;
import org.brandao.brutos.view.*;
import java.io.IOException;
import org.brandao.brutos.Configuration;
import org.brandao.brutos.RequestInstrument;

/**
 *
 * @author Afonso Brandao
 */
public class MockViewProvider extends ViewProvider{

    private DispatcherType dispatcherType;
    private String view;
    private boolean redirect;

    public MockViewProvider() {
    }

    public void configure(Configuration properties) {
    }


    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public boolean isRedirect() {
        return redirect;
    }

    public void setRedirect(boolean redirect) {
        this.redirect = redirect;
    }

    /*
    public ServletRequest getRequest() {
        return request;
    }

    public void setRequest(ServletRequest request) {
        this.request = request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    public ServletContext getContext() {
        return context;
    }

    public void setContext(ServletContext context) {
        this.context = context;
    }
    */
    
    public void configure(Properties properties) {
    }

    public void show(RequestInstrument requestInstrument,
            String view, DispatcherType dispatcherType) throws IOException {
        //this.context = null;
        this.redirect = dispatcherType == DispatcherType.REDIRECT? true : false;
        this.dispatcherType = dispatcherType;
        //this.request = null;
        //this.response = null;
        this.view = view;
    }

    public DispatcherType getDispatcherType() {
        return dispatcherType;
    }

    public void setDispatcherType(DispatcherType dispatcherType) {
        this.dispatcherType = dispatcherType;
    }

}
