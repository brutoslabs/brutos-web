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

package org.brandao.brutos.web.http.view;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.brandao.brutos.BrutosConstants;
import org.brandao.brutos.DispatcherType;
import org.brandao.brutos.MvcRequest;
import org.brandao.brutos.MvcResponse;
import org.brandao.brutos.RenderViewException;
import org.brandao.brutos.RenderViewType;
import org.brandao.brutos.RequestInstrument;
import org.brandao.brutos.ScopeType;
import org.brandao.brutos.Scopes;
import org.brandao.brutos.StackRequestElement;
import org.brandao.brutos.scope.Scope;
import org.brandao.brutos.type.Type;
import org.brandao.brutos.web.BrutosWebConstants;
import org.brandao.brutos.web.WebMvcRequest;
import org.brandao.brutos.web.WebMvcResponse;
import org.brandao.brutos.web.mapping.WebAction;
import org.brandao.brutos.web.mapping.WebController;
import org.brandao.brutos.web.mapping.WebThrowableSafeData;

/**
 * 
 * @author Brandao
 */
public class JSPRenderView implements RenderViewType{
    
	protected void show(int responseStatus, String reason,
			WebMvcRequest webRequest,
			WebMvcResponse webResponse,
			String view, DispatcherType dispatcherType){

		HttpServletRequest request = (HttpServletRequest)webRequest.getServletRequest();
		HttpServletResponse response = (HttpServletResponse)webResponse.getServletResponse();
		
		try{
			if(reason != null){
				response.sendError(responseStatus, reason);
				return;
			}
			
			response.setStatus(responseStatus);
			
	        if( dispatcherType == DispatcherType.FORWARD ){
	        	request.getRequestDispatcher(view)
	                        .forward(
	                        		request, 
	                        		response);
	        }
	        else
	        if( dispatcherType == DispatcherType.INCLUDE ){
	        	request.getRequestDispatcher(view)
	            .include(
	            		request, 
	            		response);
	        }
	        else
	        if( dispatcherType == DispatcherType.REDIRECT ){
	        	response.sendRedirect(view);
	        }
	        else
	            throw new RenderViewException( "invalid dispatcher type: " + dispatcherType );
		}
		catch(RenderViewException e){
			throw e;
		}
		catch(Throwable e){
			throw new RenderViewException(e);
		}
		
	}
    
	protected void show(
			WebThrowableSafeData throwableSafeData, 
			StackRequestElement stackRequestElement, 
			Scope requestScope){
		Object objectThrow = stackRequestElement.getObjectThrow();
		
		if (throwableSafeData.getParameterName() != null){
			requestScope
				.put(
					throwableSafeData.getParameterName(),
					objectThrow);
		}

		if (throwableSafeData.getView() != null || throwableSafeData.getReason() != null) {
			this.show(
					throwableSafeData.getResponseError(), 
					throwableSafeData.getReason(),
					(WebMvcRequest)stackRequestElement.getRequest(),
					(WebMvcResponse)stackRequestElement.getResponse(),
					throwableSafeData.getView(),
					throwableSafeData.getDispatcher());
			return;
		}
		
		WebAction action = (WebAction)stackRequestElement.getAction().getMethodForm();
		
		if(action.getView() != null){
			this.show(
					action.getResponseStatus(), 
					null,
					(WebMvcRequest)stackRequestElement.getRequest(),
					(WebMvcResponse)stackRequestElement.getResponse(),
					action.getView(),
					action.getDispatcherType());
		}
		
		WebController controller = (WebController)action.getController();
		
		if(controller.getView() != null){
			this.show(
					controller.getResponseStatus(), 
					null,
					(WebMvcRequest)stackRequestElement.getRequest(),
					(WebMvcResponse)stackRequestElement.getResponse(),
					controller.getView(),
					controller.getDispatcherType());
			return;
		}
		
	}
	
	protected void show(
			WebAction action, 
			StackRequestElement stackRequestElement, 
			Scope requestScope){
		
		org.brandao.brutos.mapping.ResultAction resultAction =
				action.getResultAction();
		
		if (resultAction.getType() != null) {
			String var = 
					resultAction.getName() == null? 
						BrutosConstants.DEFAULT_RETURN_NAME : 
						resultAction.getName();
			
			requestScope.put(var, stackRequestElement.getResultAction());

			if (action.isReturnRendered() || resultAction.getType().isAlwaysRender()) {
				resultAction.getType().show(
						stackRequestElement.getResponse(), 
						stackRequestElement.getResultAction());
				return;
			}
		}

		if (action.getView() != null){
			this.show(
					action.getResponseStatus(), 
					null,
					(WebMvcRequest)stackRequestElement.getRequest(),
					(WebMvcResponse)stackRequestElement.getResponse(),
					action.getView(),
					action.getDispatcherType());
			return;
		}
		
		WebController controller = (WebController)action.getController();
		
		if(controller.getView() != null){
			this.show(
					controller.getResponseStatus(), 
					null,
					(WebMvcRequest)stackRequestElement.getRequest(),
					(WebMvcResponse)stackRequestElement.getResponse(),
					controller.getView(),
					controller.getDispatcherType());
			return;
		}
		
		Type type = action.getResultAction().getType();
		
		if(type != null){
			type.show(
				stackRequestElement.getResponse(), 
				stackRequestElement.getResultAction());
		}
		
	}
	
	protected void show(WebController controller, 
			StackRequestElement stackRequestElement, 
			Scope requestScope){
		
		if(controller.getView() != null){
			this.show(
					controller.getResponseStatus(), 
					null,
					(WebMvcRequest)stackRequestElement.getRequest(),
					(WebMvcResponse)stackRequestElement.getResponse(),
					controller.getView(),
					controller.getDispatcherType());
		}
		
	}

	public void show(MvcRequest request, MvcResponse response){
		
		RequestInstrument requestInstrument     = request.getRequestInstrument();
		StackRequestElement stackRequestElement = request.getStackRequestElement();

		if (requestInstrument.isHasViewProcessed())
			return;

		Scopes scopes = requestInstrument.getContext().getScopes();
		Scope requestScope = scopes.get(ScopeType.REQUEST.toString());

		WebThrowableSafeData throwableSafeData = 
				(WebThrowableSafeData)stackRequestElement.getThrowableSafeData();

		if (stackRequestElement.getView() != null) {
			this.show(
					BrutosWebConstants.DEFAULT_RESPONSE_STATUS,
					null,
					(WebMvcRequest)stackRequestElement.getRequest(),
					(WebMvcResponse)stackRequestElement.getResponse(),
					stackRequestElement.getView(),
					stackRequestElement.getDispatcherType());
			return;
		}
		
		if (throwableSafeData != null) {
			this.show(throwableSafeData, stackRequestElement, requestScope);
			return;
		}
		else{
			WebAction action = 
					(WebAction)stackRequestElement.getAction().getMethodForm();
	
			if (action != null) {
				this.show(
						action, 
						stackRequestElement, 
						requestScope);
			}
			else{
				this.show(
						(WebController)stackRequestElement.getController(), 
						stackRequestElement, 
						requestScope);
			}
		}

	}

}
