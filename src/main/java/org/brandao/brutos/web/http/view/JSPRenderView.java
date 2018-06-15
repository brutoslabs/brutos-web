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
import org.brandao.brutos.web.BrutosWebConstants;
import org.brandao.brutos.web.WebApplicationContext;
import org.brandao.brutos.web.WebDispatcherType;
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

		HttpServletRequest request   = (HttpServletRequest) webRequest;
		HttpServletResponse response = (HttpServletResponse)webResponse.getServletResponse();
		
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
			WebApplicationContext context, StackRequestElement stackRequestElement, 
			WebThrowableSafeData throwableSafeData, WebAction action, WebController controller, 
			Scope requestScope){

		WebMvcRequest webMvcRequest   = (WebMvcRequest)stackRequestElement.getRequest();
		WebMvcResponse webMvcResponse = (WebMvcResponse)stackRequestElement.getResponse();
		String reason                 = null;

		if(stackRequestElement.getView() != null){
			this.show(
					BrutosWebConstants.DEFAULT_RESPONSE_STATUS, 
					reason,
					webMvcRequest,
					webMvcResponse,
					stackRequestElement.getView(),
					stackRequestElement.getDispatcherType());
		}
		else{
			if(throwableSafeData != null){
				reason             = throwableSafeData.getReason();
				Object objectThrow = stackRequestElement.getObjectThrow();
				
				if (throwableSafeData.getParameterName() != null){
					requestScope
						.put(
							throwableSafeData.getParameterName(),
							objectThrow);
				}
			}
			
			this.show(
					this.getResponseCode(context, throwableSafeData, action, controller), 
					reason,
					webMvcRequest,
					webMvcResponse,
					this.getView(throwableSafeData, action, controller),
					this.getDispatcherType(context, throwableSafeData, action, controller));
		}
		
	}
	
	/*
	protected void show(
			WebAction action, 
			StackRequestElement stackRequestElement, 
			Scope requestScope){
		
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
	 */
	
	public void show(MvcRequest request, MvcResponse response){
		
		RequestInstrument requestInstrument     = request.getRequestInstrument();
		StackRequestElement stackRequestElement = request.getStackRequestElement();

		if (requestInstrument.isHasViewProcessed()){
			return;
		}

		WebApplicationContext context = (WebApplicationContext) requestInstrument.getContext();
		Scopes scopes                 = context.getScopes();
		Scope requestScope            = scopes.get(ScopeType.REQUEST.toString());

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

		WebThrowableSafeData throwableSafeData = (WebThrowableSafeData)stackRequestElement.getThrowableSafeData();
		WebAction action                       = (WebAction)stackRequestElement.getAction().getMethodForm();
		WebController controller               = (WebController)stackRequestElement.getController();
		
		this.show(context, stackRequestElement, throwableSafeData, action, controller, requestScope);
		
		/*
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
		*/

	}

	private String getView(WebThrowableSafeData throwableSafeData, WebAction action, 
			WebController controller){
		
		String view = null;
		
		if(throwableSafeData != null){
			view = throwableSafeData.getView();
		}
		
		if(view == null && action != null){
			view = action.getView();
		}

		if(view == null && controller != null){
			view = controller.getView();
		}
		
		return view;
	}

	private int getResponseCode(
			WebApplicationContext context, WebThrowableSafeData throwableSafeData, 
			WebAction action, WebController controller){
		
		int responseStatus = 0;
		
		if(throwableSafeData != null){
			responseStatus = throwableSafeData.getResponseError();
		}
		
		if(responseStatus <= 0 && action != null){
			responseStatus = action.getResponseStatus();
		}

		if(responseStatus <= 0 && controller != null){
			responseStatus = controller.getResponseStatus();
		}
		
		if(responseStatus <= 0){
			responseStatus = 
				throwableSafeData != null? 
					context.getResponseError() : 
					context.getResponseStatus();
		}
		
		return responseStatus;
	}
	
	private DispatcherType getDispatcherType(
			WebApplicationContext context, WebThrowableSafeData throwableSafeData, 
			WebAction action, WebController controller){
		
		DispatcherType dispatcherType = null;
		
		if(throwableSafeData != null){
			dispatcherType = throwableSafeData.getDispatcher();
		}
		
		if(dispatcherType == null && action != null){
			dispatcherType = action.getDispatcherType();
		}

		if(dispatcherType == null && controller != null){
			dispatcherType = controller.getDispatcherType();
		}
		
		if(dispatcherType == null){
			dispatcherType = context.getDispatcherType();
		}
		
		return dispatcherType;
		
	}
	
}
