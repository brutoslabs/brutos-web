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

package org.brandao.brutos.proxy;

import java.lang.reflect.Method;

import org.brandao.brutos.FetchType;
import org.brandao.brutos.mapping.BeanDecoder;
import org.brandao.brutos.mapping.BeanDecoderException;
import org.brandao.brutos.mapping.DependencyBean;
import org.brandao.brutos.mapping.UseBeanData;

/**
 * 
 * @author Brandao
 */
public abstract class BeanHandlerImp implements ActionHandler {

	private Object metadata;
	
	private Object data;
	
	private volatile Object value;
	
	private volatile boolean loaded;
	
	private BeanDecoder decoder;
	
	public BeanHandlerImp(Object metadata, Object data, 
			BeanDecoder decoder) {
		this.metadata = metadata;
		this.decoder  = decoder;
		this.loaded   = false;
	}

	public Object invoke(Object self, Method thisMethod, Method proceed,
			Object[] args) throws Throwable {
		
		if(this.loaded){
			this.load();	
		}
		
		return proceed.invoke(this.value, args);
	}
	
	private synchronized void load() throws BeanDecoderException{
		
		if(this.loaded){
			return;
		}
		
		if(this.metadata instanceof UseBeanData){
			this.value = this.decoder.decode((UseBeanData)this.metadata, FetchType.EAGER, data);	
		}
		else
		if(this.metadata instanceof DependencyBean){
			this.value = this.decoder.decode((DependencyBean)this.metadata, FetchType.EAGER, data);	
		}
		else
			throw new IllegalStateException(String.valueOf(this.metadata));
		
	}
	
}
