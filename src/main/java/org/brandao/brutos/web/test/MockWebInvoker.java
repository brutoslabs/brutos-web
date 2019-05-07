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

package org.brandao.brutos.web.test;

import org.brandao.brutos.MutableMvcRequest;
import org.brandao.brutos.MutableMvcResponse;
import org.brandao.brutos.RequestInstrument;
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

	protected void invokeApplication(
			MutableMvcRequest request,
			MutableMvcResponse response,
			StackRequestElement element,
			RequestInstrument requestInstrument
			) throws Throwable{
		this.element = element;
		super.invokeApplication(request, response, element, requestInstrument);
	}

    public StackRequestElement getElement() {
        return element;
    }

    public String getRequestId() {
        return this.requestId == null? 
                this.element.getRequest().getRequestId() : 
                this.requestId;
    }

    public Object getRequest() {
        return request;
    }

    public Object getResponse() {
        return response;
    }

}
