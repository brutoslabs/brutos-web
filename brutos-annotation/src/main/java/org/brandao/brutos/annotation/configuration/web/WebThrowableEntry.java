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

package org.brandao.brutos.annotation.configuration.web;

import org.brandao.brutos.DispatcherType;
import org.brandao.brutos.annotation.configuration.ThrowableEntry;
import org.brandao.brutos.annotation.web.ResponseError;
import org.brandao.brutos.annotation.web.ResponseErrors;
import org.brandao.brutos.mapping.StringUtil;
import org.brandao.brutos.web.WebDispatcherType;

public class WebThrowableEntry extends ThrowableEntry{

	private int responseError;
	
	private String reason;

	public WebThrowableEntry(ResponseErrors defaultValue, Class<? extends Throwable> target){
		
		this.responseError = 
			defaultValue != null?
				defaultValue.code() == 0? 
						defaultValue.value() : 
						defaultValue.code() :
				0;
						
		this.reason = 
				defaultValue != null?
					defaultValue.reason() :
					null;
		
		super.setDispatcher( 
				defaultValue != null?
					(StringUtil.isEmpty(defaultValue.dispatcher())?
						null :
						WebDispatcherType.valueOf(StringUtil.adjust(defaultValue.dispatcher()))) :
							
					null
		);

		super.setEnabled(
				defaultValue != null? 
					defaultValue.enabled() : 
					true
		);
		
		super.setName(
				defaultValue != null?
					StringUtil.isEmpty(defaultValue.name())? 
						null :
						StringUtil.adjust(defaultValue.name()) :
						
					null							
		);

		super.setRendered(
				defaultValue != null?
					defaultValue.rendered() :
					true 
		);
		
		super.setTarget(
				target
		);
		
		super.setView(
				defaultValue != null?
					StringUtil.adjust(defaultValue.view()) :
					null
		);
		
		super.setResolved(
				defaultValue != null?
					defaultValue.resolved() :
					false
		);
		
	}
	
	public WebThrowableEntry(ResponseError value){
		
		this.responseError = value.code();
						
		this.reason = value.reason();
		
		super.setDispatcher( 
			(StringUtil.isEmpty(value.dispatcher())? 
				null :
				DispatcherType.valueOf(StringUtil.adjust(value.dispatcher())))
		);

		super.setEnabled(
			value.enabled()
		);
		
		super.setName(
			StringUtil.isEmpty(value.name())? 
				null :
				StringUtil.adjust(value.name())
		);

		super.setRendered(
			value.rendered() 
		);
		
		super.setTarget(
			value.target()
		);
		
		super.setView(
			StringUtil.adjust(value.view())
		);
		
		super.setResolved(
			value.resolved()
		);
		
	}
	
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public int getResponseError() {
		return responseError;
	}

	public void setResponseError(int responseError) {
		this.responseError = responseError;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!getClass().isAssignableFrom(obj.getClass()))
			return false;
		ThrowableEntry other = (ThrowableEntry) obj;
		if (super.getTarget() == null) {
			if (other.getTarget() != null)
				return false;
		} else if (!super.getTarget().equals(other.getTarget()))
			return false;
		return true;
	}
	
}
