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

import java.util.Properties;
import org.brandao.brutos.DispatcherType;
import org.brandao.brutos.*;
import org.brandao.brutos.mapping.Action;
import org.brandao.brutos.type.Type;

/**
 * 
 * @author Brandao
 */
public class MockRenderView extends AbstractRenderView {

	private DispatcherType dispatcherType;

	private String view;

	private boolean redirect;

	private Object actionResult;

	public MockRenderView() {
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

	public void configure(Properties properties) {
	}

	public void show(RequestInstrument requestInstrument,
			StackRequestElement stackRequestElement){

		Action method = stackRequestElement.getAction() == null ? null
				: stackRequestElement.getAction().getMethodForm();

		if (method != null && method.isReturnRendered())
			this.actionResult = stackRequestElement.getResultAction();
		else
			super.show(requestInstrument, stackRequestElement);
	}

	public void show(RequestInstrument requestInstrument, String view,
			DispatcherType dispatcherType){
		this.redirect = dispatcherType == DispatcherType.REDIRECT;
		this.dispatcherType = dispatcherType;
		this.view = view;

	}

	public DispatcherType getDispatcherType() {
		return dispatcherType;
	}

	public void setDispatcherType(DispatcherType dispatcherType) {
		this.dispatcherType = dispatcherType;
	}

	public Object getActionResult() {
		return actionResult;
	}

	public void setActionResult(Type actionResult) {
		this.actionResult = actionResult;
	}

	public void destroy() {
	}

}
