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

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.brandao.brutos.BrutosConstants;
import org.brandao.brutos.DataType;
import org.brandao.brutos.Invoker;
import org.brandao.brutos.InvokerException;
import org.brandao.brutos.MutableMvcRequest;
import org.brandao.brutos.MutableMvcResponse;
import org.brandao.brutos.RequestInstrument;
import org.brandao.brutos.RequestProvider;
import org.brandao.brutos.RequestTypeException;
import org.brandao.brutos.ResourceAction;
import org.brandao.brutos.ResponseProvider;
import org.brandao.brutos.ResponseTypeException;
import org.brandao.brutos.Scopes;
import org.brandao.brutos.StackRequestElement;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.mapping.DataTypeMap;
import org.brandao.brutos.scope.Scope;
import org.brandao.brutos.web.mapping.MediaTypeMap;
import org.brandao.brutos.web.scope.HeaderScope;
import org.brandao.brutos.web.scope.ParamScope;
import org.brandao.brutos.web.scope.RequestScope;
import org.brandao.brutos.web.scope.SessionScope;

/**
 * 
 * @author Brandao
 */
public class WebInvoker extends Invoker{
    
    public void invoker(HttpServletRequest request, 
            HttpServletResponse response, FilterChain chain) throws IOException, ServletException{
    	
    	WebMvcRequestImp webRequest   = new WebMvcRequestImp((HttpServletRequest)request);
    	WebMvcResponseImp webResponse = new WebMvcResponseImp((HttpServletResponse)response, webRequest);
    	
    	try{
    		SessionScope.setServletRequest(request);
    		ParamScope.setRequest(webRequest);
    		RequestScope.setRequest(webRequest);
    		HeaderScope.setRequest(webRequest);

            if(!this.invoke(webRequest, webResponse)){
                if(chain == null)
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                else
                    chain.doFilter(request, response);
            }
    		
    	}
    	catch(InvokerException e){
    		if(e.getCause() instanceof RequestTypeException){
        		response.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
    		}
    		else
    		if(e.getCause() instanceof RequestMethodException){
        		response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    		}
    		else
    		if(e.getCause() instanceof ResponseTypeException){
        		response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE);
    		}
    		else{
    			throw e;
    		}
    	}
    	finally{
    		SessionScope.removeServletRequest(request);
    		ParamScope.removeRequest(webRequest);
    		RequestScope.removeRequest(webRequest);
    		HeaderScope.removeRequest(webRequest);
    	}
    }

	protected boolean invokeApplication(MutableMvcRequest request, MutableMvcResponse response,
			StackRequestElement element, RequestInstrument requestInstrument) throws Throwable{
		
    	this.updateRedirectVars(request);
    	return super.invokeApplication(request, response, element, requestInstrument);
    	
	}
    
	protected void updateRequest(MutableMvcRequest request, Controller controller, Object resource) 
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{	
		
		super.updateRequest(request, controller, resource);
		
		WebMvcRequestImp webRequest = (WebMvcRequestImp)request;
		webRequest.getServletRequest()
			.setAttribute(BrutosConstants.CONTROLLER_PROPERTY, request.getResource());
	}
	
	public Object invoke(Controller controller, ResourceAction action,
			Object resource, Object[] parameters) throws InvokerException{

		if (controller == null)
			throw new InvokerException("controller not found");

		if (action == null)
			throw new InvokerException("action not found");

		MutableWebMvcRequest webRequest   = (MutableWebMvcRequest) RequestProvider.getRequest();
		MutableWebMvcResponse webResponse = (MutableWebMvcResponse) ResponseProvider.getResponse();
		
    	webRequest   = new WebMvcRequestImp((HttpServletRequest)webRequest.getServletRequest());
    	webResponse = new WebMvcResponseImp((HttpServletResponse)webResponse.getServletResponse(), webRequest);
		
    	webRequest.setResource(resource);
    	webRequest.setResourceAction(action);
    	webRequest.setParameters(parameters);
		
		this.invoke(webRequest, webResponse);
		
		return webResponse.getResult();
	}
    
	@SuppressWarnings("unchecked")
	protected void updateRedirectVars(MutableMvcRequest webRequest){
		Scopes scopes 				= this.applicationContext.getScopes();
		Scope scope 				= scopes.get(WebScopeType.FLASH);
		Map<String,Object> vars     = (Map<String, Object>) scope.get(BrutosWebConstants.REDIRECT_PARAMS);
		
		if(vars != null){
			for(Entry<String, Object> e: vars.entrySet()){
				webRequest.setProperty(e.getKey(), e.getValue());
			}
		}
		
	}
	
	protected boolean resolveAction(MutableMvcRequest request, 
			MutableMvcResponse response){
		
		if(!super.resolveAction(request, response)){
			return false;
		}
		
		try{
	    	WebMvcRequest webRequest         = (WebMvcRequest)request;
			WebResourceAction resourceAction = (WebResourceAction)request.getResourceAction();
			RequestMethodType requestMethod  = resourceAction.getRequestMethod();
			
			if(!webRequest.getRequestMethodType().equals(requestMethod)){
				throw new RequestMethodException(webRequest.getRequestMethodType().getId());
			}
			
			return true;
		}
		catch(InvokerException e){
			throw e;
		}
		catch(Throwable e){
			throw new InvokerException(e);
		}
		
	}
    
	protected DataType selectResponseType(ResourceAction action, MutableMvcRequest request){
		
    	DataTypeMap supportedResponseTypes = action.getResponseTypes();
    	List<DataType> responseTypes       = request.getAcceptResponse();
    	
    	if(supportedResponseTypes.isEmpty()){
    		
    		MediaType defaultDataType = (MediaType)this.renderView.getDefaultRenderViewType();
    		
    		if(responseTypes == null || responseTypes.isEmpty()){
    			return defaultDataType;
    		}
    		
	    	for(DataType dataType: responseTypes){
	    		if(defaultDataType.match((MediaType)dataType)){
	    			return defaultDataType;
	    		}
	    	}
	    	
    	}
    	else{
    		MediaTypeMap supportedMediaType = (MediaTypeMap)supportedResponseTypes;
    		
	    	for(DataType dataType: responseTypes){
	    		MediaType selected = 
    				supportedMediaType.getMatch((MediaType)dataType);
	    			
	    		if(selected != null){
	    			return selected;
	    		}
	    	}
	    	
    	}
    	
    	return null;
	}
        
}
