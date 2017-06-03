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
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.brandao.brutos.DataType;
import org.brandao.brutos.Invoker;
import org.brandao.brutos.MutableMvcRequest;
import org.brandao.brutos.RequestTypeException;
import org.brandao.brutos.ResourceAction;
import org.brandao.brutos.ResponseTypeException;
import org.brandao.brutos.StackRequestElement;
import org.brandao.brutos.mapping.DataTypeMap;
import org.brandao.brutos.web.mapping.MediaTypeMap;
import org.brandao.brutos.web.mapping.WebAction;
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
    		RequestScope.setServletRequest(request);
            if(!super.invoke(webRequest, webResponse)){
                if(chain == null)
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                else
                    chain.doFilter(request, response);
            }
    		
    	}
    	catch(RequestTypeException e){
    		response.setStatus(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
    	}
    	catch(RequestMethodException e){
    		response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    	}
    	catch(ResponseTypeException e){
    		response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
    	}
    	finally{
    		SessionScope.removeServletRequest(request);
    		RequestScope.removeServletRequest(request);
    	}
    }

    public boolean invoke(StackRequestElement element){
    	
    	WebMvcRequest request           = (WebMvcRequest) element.getRequest();
		ResourceAction resourceAction   = element.getAction();
		WebAction action                = (WebAction)resourceAction.getMethodForm();
		RequestMethodType requestMethod = action.getRequestMethod();
		
		if(!request.getRequestMethodType().equals(requestMethod)){
			throw new RequestMethodException(request.getRequestMethodType().getId());
		}
		
		return super.invoke(element);
	}
    
	protected DataType selectResponseType(ResourceAction action, MutableMvcRequest request){
		
    	DataTypeMap supportedResponseTypes = action.getMethodForm().getResponseTypes();
    	List<DataType> responseTypes       = request.getAcceptResponse();
    	
    	if(supportedResponseTypes.isEmpty()){
    		supportedResponseTypes = action.getController().getRequestTypes();
    	}
    	
    	if(supportedResponseTypes.isEmpty()){
    		
    		MediaType defaultDataType = (MediaType)this.renderView.getDefaultRenderViewType();
    		
	    	for(DataType dataType: responseTypes){
	    		if(defaultDataType.match((MediaType)dataType)){
	    			return dataType;
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
