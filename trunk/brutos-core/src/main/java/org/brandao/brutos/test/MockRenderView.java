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

package org.brandao.brutos.test;

import org.brandao.brutos.ConfigurableRenderViewImp;
import org.brandao.brutos.DispatcherType;
import org.brandao.brutos.MvcRequest;
import org.brandao.brutos.MvcResponse;
import org.brandao.brutos.RenderViewException;

/**
 * 
 * @author Brandao
 */
public class MockRenderView extends ConfigurableRenderViewImp {

	private DispatcherType dispatcherType;

	private String view;

	private boolean redirect;

	private Object actionResult;

	public MockRenderView(){
	}
	
	public void show(MvcRequest request, MvcResponse response) throws RenderViewException{
		throw new UnsupportedOperationException();
	}
	
	public DispatcherType getDispatcherType() {
		return dispatcherType;
	}

	public String getView() {
		return view;
	}

	public boolean isRedirect() {
		return redirect;
	}

	public Object getActionResult() {
		return actionResult;
	}

}
