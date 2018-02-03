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
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

import org.brandao.brutos.ClassUtil;
import org.brandao.brutos.FetchType;
import org.brandao.brutos.mapping.BeanDecoder;
import org.brandao.brutos.mapping.BeanDecoderException;
import org.brandao.brutos.mapping.DependencyBean;
import org.brandao.brutos.mapping.UseBeanData;

/**
 * 
 * @author Brandao
 */
public abstract class BeanEntityProxyHandler 
	extends AbstractEntityProxyHandler {

	private Object metadata;
	
	private Object data;
	
	private volatile Object value;
	
	private BeanDecoder decoder;
	
	public BeanEntityProxyHandler(Object metadata, Object data, 
			BeanDecoder decoder) {
		this.metadata = metadata;
		this.decoder  = decoder;
		this.value    = null;
		this.data     = data;
		super.target  = null;
	}

	public Object invoke(Object self, Method thisMethod, Method proceed,
			Object[] args) throws Throwable {
		
		try{
			if(super.isGetEntityProxyHandlerMethod(thisMethod)){
				return this;
			}
			
			if(this.value == null){
				this.load();	
			}
			
			if(this.value == null){
				throw new LazyLoadException("can't load entity");
			}
			
			return thisMethod.invoke(this.value, args);
		}
		catch(LazyLoadException e){
			throw e;	
		}
		catch(Throwable e){
			throw new LazyLoadException(e);	
		}
		 
	}
	
	public Object getTarget(){
		try{
			if(this.value == null){
				this.load();	
			}
			return super.getTarget();
		}
		catch(LazyLoadException e){
			throw e;	
		}
		catch(Throwable e){
			throw new LazyLoadException(e);	
		}
	}
	
	private synchronized void load() 
			throws BeanDecoderException, InstantiationException, IllegalAccessException{
		
		if(this.value != null){
			return;
		}
		
		if(this.metadata instanceof UseBeanData){
			UseBeanData m = (UseBeanData)this.metadata;
			this.value = this.decoder.decode(m, FetchType.EAGER, data);
			
			if(this.value == null){
				this.value = this.getDefaultInstance(m.getClassType());
			}
		}
		else
		if(this.metadata instanceof DependencyBean){
			DependencyBean m = (DependencyBean)this.metadata;
			this.value       = this.decoder.decode(m, FetchType.EAGER, data);
			
			if(this.value == null){
				this.value = this.getDefaultInstance(m.getClassType());
			}
		}
		else
			throw new IllegalStateException(String.valueOf(this.metadata));
		
		super.target = this.value;
	}
	
	private Object getDefaultInstance(Class<?> type) throws InstantiationException, IllegalAccessException{
		
		if(List.class.isAssignableFrom(type)){
			return ClassUtil.getInstance(ClassUtil.getInstantiableClass(List.class));
		}
		else
		if(Set.class.isAssignableFrom(type)){
			return ClassUtil.getInstance(ClassUtil.getInstantiableClass(Set.class));
		}
		else
		if(Map.class.isAssignableFrom(type)){
			return ClassUtil.getInstance(ClassUtil.getInstantiableClass(Map.class));
		}
		else
		if(ConcurrentMap.class.isAssignableFrom(type)){
			return ClassUtil.getInstance(ClassUtil.getInstantiableClass(ConcurrentMap.class));
		}
		else{
			return null;
		}
	}
}
