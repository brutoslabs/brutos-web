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

package org.brandao.brutos.web.test;

import org.brandao.brutos.ActionResolver;
import org.brandao.brutos.ConfigurableApplicationContext;
import org.brandao.brutos.ControllerManager;
import org.brandao.brutos.ControllerResolver;
import org.brandao.brutos.ObjectFactory;
import org.brandao.brutos.RenderView;
import org.brandao.brutos.StackRequestElement;
import org.brandao.brutos.web.WebInvoker;

/**
 *
 * @author Brandao
 */
public class MockWebInvoker extends WebInvoker{

    private StackRequestElement element;
    
    private String requestId;
    
    private Object request;
    
    private Object response;

    public MockWebInvoker( ControllerResolver controllerResolver, ObjectFactory objectFactory, 
            ControllerManager controllerManager, ActionResolver actionResolver, 
            ConfigurableApplicationContext applicationContext, RenderView renderView){
        super(controllerResolver, objectFactory, 
                controllerManager, actionResolver, applicationContext, 
                renderView);
    }
    
    public boolean invoke( StackRequestElement element ){
        this.element = element;
        return super.invoke(element);
    }

    public boolean invoke( String requestId ){
        this.requestId = requestId;
        return super.invoke(requestId);
    }

    public StackRequestElement getElement() {
        return element;
    }

    public String getRequestId() {
        return this.requestId == null? 
                this.element.getHandler().requestId() : 
                this.requestId;
    }

    public Object getRequest() {
        return request;
    }

    public Object getResponse() {
        return response;
    }

}
