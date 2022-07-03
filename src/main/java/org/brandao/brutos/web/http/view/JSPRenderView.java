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

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.brandao.brutos.DispatcherType;
import org.brandao.brutos.MvcRequest;
import org.brandao.brutos.MvcResponse;
import org.brandao.brutos.RenderViewException;
import org.brandao.brutos.RenderViewType;
import org.brandao.brutos.RequestInstrument;
import org.brandao.brutos.ScopeType;
import org.brandao.brutos.Scopes;
import org.brandao.brutos.scope.Scope;
import org.brandao.brutos.web.BrutosWebConstants;
import org.brandao.brutos.web.WebApplicationContext;
import org.brandao.brutos.web.WebDispatcherType;
import org.brandao.brutos.web.WebMvcRequest;
import org.brandao.brutos.web.WebMvcResponse;
import org.brandao.brutos.web.WebStackRequestElement;
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
			String view, String context, DispatcherType dispatcherType, WebApplicationContext webApplicationContext){

		HttpServletRequest request   = (HttpServletRequest) webRequest;
		HttpServletResponse response = (HttpServletResponse)webResponse.getServletResponse();
		
		ServletContext servletContext = webApplicationContext.getContext();
		if(context != null && !servletContext.getContextPath().equals(context)) {
			servletContext = servletContext.getContext(context);
		}
		
		if(servletContext == null) {
			throw new NullPointerException(context);
		}
		
		try{
			if(reason != null){
				if(responseStatus <= 0){
					responseStatus = BrutosWebConstants.DEFAULT_RESPONSE_ERROR;
				}
				response.sendError(responseStatus, reason);
				return;
			}
			
			if(responseStatus <= 0){
				responseStatus = BrutosWebConstants.DEFAULT_RESPONSE_STATUS;
			}
			
			response.setStatus(responseStatus);
			
			if(dispatcherType == null){
				dispatcherType = WebDispatcherType.FORWARD;
			}
			
	        if( dispatcherType == WebDispatcherType.FORWARD ){
	        	request.getRequestDispatcher(view)
	                        .forward(
	                        		request, 
	                        		response);
	        }
	        else
	        if( dispatcherType == WebDispatcherType.INCLUDE ){
	        	request.getRequestDispatcher(view)
	            .include(
	            		request, 
	            		response);
	        }
	        else
	        if( dispatcherType == WebDispatcherType.REDIRECT ){
	        	response.sendRedirect(request.getContextPath() + view);
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
			WebApplicationContext context, WebStackRequestElement stackRequestElement, 
			WebThrowableSafeData throwableSafeData, WebAction action, WebController controller, 
			Scope requestScope){

		WebMvcRequest webMvcRequest   = (WebMvcRequest)stackRequestElement.getRequest();
		WebMvcResponse webMvcResponse = (WebMvcResponse)stackRequestElement.getResponse();
		
		String reason                 = null;
		
			if(throwableSafeData != null){
				reason             = throwableSafeData.getReason();
				Object objectThrow = stackRequestElement.getObjectThrow();
				String name        = throwableSafeData.getParameterName();
				if (name != null){
					requestScope.put(name, objectThrow);
				}
			}
			else
			if(action != null){
				org.brandao.brutos.mapping.ResultAction resultAction =
						action.getResultAction();
				
				if (resultAction.getType() != null) {
					if (action.isReturnRendered() || resultAction.getType().isAlwaysRender()) {
						resultAction.getType().show(
								stackRequestElement.getResponse(), 
								stackRequestElement.getResultAction());
						return;
					}
				}
				
			}
				
			String view                   = null;
			String viewContext            = null;
			DispatcherType dispatcherType = null;
			int responseCode              = 0;
			
			if(controller != null){
				
				if(controller.getContextView() != null && !controller.getContextView().equals(viewContext)) {
					viewContext = controller.getContextView();
				}
				
				view           = controller.getView();
				responseCode   = controller.getResponseStatus();
				dispatcherType = controller.getDispatcherType();
			}

			if(action != null){
				
				if(action.getView() != null){
					
					if(action.getContextView() != null && !action.getContextView().equals(viewContext)) {
						viewContext = action.getContextView();
					}
					
					dispatcherType = action.getDispatcherType();
					view           = action.getView();
				}
				
				if(action.getResponseStatus() != 0){
					responseCode = action.getResponseStatus();
				}

			}
			
			if(throwableSafeData != null){
				
				reason = throwableSafeData.getReason();
				WebAction twAction = (WebAction) throwableSafeData.getAction();
				
				if(twAction.getView() != null){
					
					if(twAction.getContextView() != null && !twAction.getContextView().equals(viewContext)) {
						viewContext = twAction.getContextView();
					}
					
					dispatcherType = twAction.getDispatcherType();
					view           = twAction.getView();
				}
				
				if(((WebAction)throwableSafeData.getAction()).getResponseStatus() != 0){
					responseCode = ((WebAction)throwableSafeData.getAction()).getResponseStatus();
				}
				
			}
			
			if(responseCode == 0){
				responseCode = context.getResponseStatus();
			}
			
			if(dispatcherType == null){
				dispatcherType = context.getDispatcherType();
			}

			this.show(
					responseCode, 
					reason,
					webMvcRequest,
					webMvcResponse,
					view,
					viewContext,
					dispatcherType, context);
		
	}
	
	public void show(MvcRequest request, MvcResponse response){
		
		RequestInstrument requestInstrument     = request.getRequestInstrument();
		WebStackRequestElement stackRequestElement = (WebStackRequestElement) request.getStackRequestElement();

		if (requestInstrument.isHasViewProcessed()){
			return;
		}

		WebApplicationContext context = (WebApplicationContext) requestInstrument.getContext();
		Scopes scopes                 = context.getScopes();
		Scope requestScope            = scopes.get(ScopeType.REQUEST.toString());

		if (stackRequestElement.getView() != null) {
			
			this.show(
					stackRequestElement.getResponseStatus() == 0? 
							BrutosWebConstants.DEFAULT_RESPONSE_STATUS : 
							stackRequestElement.getResponseStatus(), 
					stackRequestElement.getReason(),
					(WebMvcRequest)stackRequestElement.getRequest(),
					(WebMvcResponse)stackRequestElement.getResponse(),
					stackRequestElement.getView(),
					stackRequestElement.getViewContext(),
					stackRequestElement.getDispatcherType(),
					context);
			return;
		}

		WebThrowableSafeData throwableSafeData = (WebThrowableSafeData)stackRequestElement.getThrowableSafeData();
		WebAction action                       = (WebAction)stackRequestElement.getAction().getMethodForm();
		WebController controller               = (WebController)stackRequestElement.getController();
		
		this.show(context, stackRequestElement, throwableSafeData, action, controller, requestScope);
	}
	
}
