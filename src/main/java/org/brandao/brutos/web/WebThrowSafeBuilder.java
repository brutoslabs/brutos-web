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

import org.brandao.brutos.ActionBuilder;
import org.brandao.brutos.ConfigurableApplicationContext;
import org.brandao.brutos.ControllerBuilder;
import org.brandao.brutos.DataType;
import org.brandao.brutos.ThrowSafeBuilder;
import org.brandao.brutos.ValidatorFactory;
import org.brandao.brutos.mapping.Action;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.mapping.MappingException;
import org.brandao.brutos.mapping.ThrowableSafeData;
import org.brandao.brutos.web.MediaType;
import org.brandao.brutos.web.mapping.WebAction;

/**
 * 
 * @author Brandao
 *
 */
public class WebThrowSafeBuilder 
	extends ThrowSafeBuilder{

	public WebThrowSafeBuilder(ThrowSafeBuilder throwSafeBuilder) {
		super(throwSafeBuilder);
	}

	public WebThrowSafeBuilder(ThrowableSafeData throwSafe,
			Controller controller, Action action, ValidatorFactory validatorFactory,
			ControllerBuilder controllerBuilder, ActionBuilder actionBuilder,
			ConfigurableApplicationContext applicationContext) {
		super(throwSafe, controller, action, validatorFactory, controllerBuilder, 
				actionBuilder, applicationContext);
	}
	
	public WebThrowSafeBuilder addRequestType(DataType value){
		MediaType mediaType = MediaType.valueOf(value.getName());
		super.addRequestType(mediaType);
		return this;
	}
	
	public WebThrowSafeBuilder removeRequestType(DataType value){
		MediaType mediaType = MediaType.valueOf(value.getName());
		super.removeRequestType(mediaType);
		return this;
	}

	public WebThrowSafeBuilder addResponseType(DataType value){
		MediaType mediaType = MediaType.valueOf(value.getName());
		super.addResponseType(mediaType);
		return this;
	}
	
	public WebThrowSafeBuilder removeResponseType(DataType value){
		MediaType mediaType = MediaType.valueOf(value.getName());
		super.removeResponseType(mediaType);
		return this;
	}
	
	public WebThrowSafeBuilder setResponseError(int value){

		if(value <=0){
			throw new MappingException("invalid response status");
		}
		
		WebAction webAction = (WebAction) this.throwSafeData.getAction();
		webAction.setResponseStatus(value);
		return this;
	}
	
}
