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

package org.brandao.brutos.type;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.brandao.brutos.ConfigurableApplicationContext;
import org.brandao.brutos.ConfigurableResultAction;
import org.brandao.brutos.MutableMvcRequest;
import org.brandao.brutos.MvcResponse;
import org.brandao.brutos.RenderView;
import org.brandao.brutos.ResourceAction;
import org.brandao.brutos.StackRequestElement;
import org.brandao.brutos.TypeManager;
import org.brandao.brutos.ViewResolver;
import org.brandao.brutos.mapping.Action;

/**
 * 
 * @author Brandao
 */
public class ResultActionType extends AbstractType {

	private final ConcurrentMap<Class<?>, Type> cache;

	public ResultActionType() {
		this.cache = new ConcurrentHashMap<Class<?>, Type>();
	}

	public Object convert(Object value) {
		return value;
	}

	public void show(MvcResponse response, Object value){

		MutableMvcRequest request              = (MutableMvcRequest)response.getRequest();
		ConfigurableResultAction resultAction  = (ConfigurableResultAction)value;
		Map<String, Object> header             = resultAction.getHeader();
		Map<String, Object> vars               = resultAction.getVars();
		Object content                         = resultAction.getContent();
		ConfigurableApplicationContext context = 
				(ConfigurableApplicationContext) request.getRequestInstrument().getContext();
		
		for (String key : header.keySet()) {
			response.setHeader(key, header.get(key));
		}

		for (String key : vars.keySet()) {
			request.setProperty(key, vars.get(key));
		}
		
		if (content != null) {
			Type contentType = this.getContentType(resultAction.getContentType(), context);
			contentType.show(response, content);
			return;
		}
		
		RenderView renderView                   = context.getRenderView();
		StackRequestElement stackRequestElement = request.getStackRequestElement();
		String view                             = 
				this.getView(
						resultAction, 
						request.getResourceAction(), 
						context.getViewResolver());
		
		stackRequestElement.setView(view);
		renderView.show(response.getRequest(), response);
	}

	private String getView(ConfigurableResultAction resultAction, 
			ResourceAction resourceAction, ViewResolver viewResolver){
		String view      = resultAction.getView();
		boolean resolved = resultAction.isResolvedView();
		Action action    = resourceAction.getMethodForm();

		view = 
			resolved ? 
				view : 
				viewResolver.getActionView(
						action.getController().getClassType(),
						action.getExecutor(), 
						view);
		return view;
	}
	
	private Type getContentType(Class<?> contentType,
			ConfigurableApplicationContext context) {
		Type type = (Type) this.cache.get(contentType);

		if (type != null)
			return type;
		else {
			synchronized (this) {
				type = (Type) this.cache.get(contentType);
				if (type != null)
					return type;

				TypeManager typeManager = context.getTypeManager();

				type = typeManager.getType(contentType);

				if (contentType == null){
					throw new UnknownTypeException();
				}

				this.cache.put(contentType, type);
				return type;
			}
		}
	}

	public boolean isAlwaysRender() {
		return true;
	}
	
}
