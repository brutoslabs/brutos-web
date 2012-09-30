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

package org.brandao.brutos.test;

import org.brandao.brutos.Invoker;
import org.brandao.brutos.StackRequestElement;

/**
 *
 * @author Brandao
 */
public class MockInvoker extends Invoker{

    private StackRequestElement element;
    private String requestId;
    private Object request;
    private Object response;
    
    public MockInvoker(){
    }
    
    public boolean invoke( StackRequestElement element ){
        this.element = element;
        return true;
    }

    public boolean invoke( String requestId ){
        this.requestId = requestId;
        return true;
    }

    public StackRequestElement getElement() {
        return element;
    }

    public String getRequestId() {
        return requestId;
    }

    public Object getRequest() {
        return request;
    }

    public Object getResponse() {
        return response;
    }

}
