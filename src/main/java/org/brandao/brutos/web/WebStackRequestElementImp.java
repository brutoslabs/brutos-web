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

import org.brandao.brutos.StackRequestElementImp;

/**
 * 
 * @author Brandao
 *
 */
public class WebStackRequestElementImp 
	extends StackRequestElementImp
	implements WebStackRequestElement{

	private int responseStatus;
	
	private String reason;
	
	private String viewContext;
	
	public void setResponseStatus(int value) {
		this.responseStatus = value; 
	}

	public int getResponseStatus() {
		return this.responseStatus;
	}

	public void setReason(String value) {
		this.reason = value;
	}

	public String getReason() {
		return this.reason;
	}

	public String getViewContext() {
		return viewContext;
	}

	public void setViewContext(String value) {
		this.viewContext = value;
	}

}
