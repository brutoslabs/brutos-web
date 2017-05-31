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
import org.brandao.brutos.ActionType;
import org.brandao.brutos.ConfigurableApplicationContext;
import org.brandao.brutos.ControllerBuilder;
import org.brandao.brutos.ControllerManager;
import org.brandao.brutos.DataType;
import org.brandao.brutos.DispatcherType;
import org.brandao.brutos.InterceptorManager;
import org.brandao.brutos.ValidatorFactory;
import org.brandao.brutos.mapping.Action;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.mapping.MappingException;
import org.brandao.brutos.mapping.StringUtil;
import org.brandao.brutos.mapping.ThrowableSafeData;
import org.brandao.brutos.web.mapping.WebAction;
import org.brandao.brutos.web.mapping.WebActionID;
import org.brandao.brutos.web.mapping.WebController;
import org.brandao.brutos.web.mapping.WebThrowableSafeData;
import org.brandao.brutos.web.util.WebUtil;

/**
 * 
 * @author Brandao
 */
public class WebControllerBuilder extends ControllerBuilder{
    
    public WebControllerBuilder(ControllerBuilder builder, ControllerManager.InternalUpdate internalUpdate){
        super( builder, internalUpdate );
    }
    
    public WebControllerBuilder( Controller controller, ControllerManager controllerManager,
            InterceptorManager interceptorManager, ValidatorFactory validatorFactory,
            ConfigurableApplicationContext applicationContext, ControllerManager.InternalUpdate internalUpdate ){
        super( controller, controllerManager, interceptorManager, 
                validatorFactory, applicationContext, internalUpdate );
    }
    
    @Override
    protected Action createAction(){
    	return new WebAction();
    }
    
    public ControllerBuilder addAlias(String id){
        WebUtil.checkURI(id, true);
        return super.addAlias(id);
    }

    public ActionBuilder addAction(String id, 
    		String resultId, boolean resultRendered, String view, 
            DispatcherType dispatcher, boolean resolvedView, String executor ){
    	return this.addAction(id, BrutosWebConstants.DEFAULT_REQUEST_METHOD_TYPE, 
    			resultId, resultRendered, view, dispatcher, resolvedView, executor);
    }
    
    public ActionBuilder addAction(String id, RequestMethodType requestMethodType, 
    		String resultId, boolean resultRendered, String view, 
            DispatcherType dispatcher, boolean resolvedView, String executor ){
    	
        ActionType type = this.controller.getActionType();
        
    	if(!type.isValidActionId(id))
    		throw new MappingException("invalid action id: " + id);
        
        ActionBuilder builder =
            super.addAction(id, resultId, resultRendered, view, 
            dispatcher, resolvedView, executor);
        
        WebUtil.checkURI(builder.getView(), resolvedView && view != null);
        
        WebActionBuilder webBuilder = new WebActionBuilder(builder);
        
        webBuilder.setRequestMethod(
    		requestMethodType == null? 
				BrutosWebConstants.DEFAULT_REQUEST_METHOD_TYPE :
				requestMethodType
			);
        
        return webBuilder;
    }
    
    public ControllerBuilder addThrowable( Class<?> target, String view, String id, 
            DispatcherType dispatcher, boolean resolvedView ){
    	return this.addThrowable(0, null, 
    			target, view, id, dispatcher, resolvedView);
    }

    public ControllerBuilder addThrowable(int responseError, String reason,
    		Class<?> target, String view, String id, 
            DispatcherType dispatcher, boolean resolvedView ){
    	
		ControllerBuilder builder = super.addThrowable(target, view, id, dispatcher, resolvedView);
		
        WebThrowableSafeData thr = (WebThrowableSafeData)this.controller.getThrowsSafe(target);
        
        thr.setReason(reason);
        
        thr.setResponseError(
        		responseError == 0? 
    				BrutosWebConstants.DEFAULT_RESPONSE_ERROR :
    				responseError);
        
        WebUtil.checkURI(thr.getView(), resolvedView && view != null);
        return builder;
    }
    
	protected ThrowableSafeData createThrowableSafeData(){
		return new WebThrowableSafeData();
	}
    
    public ControllerBuilder setDefaultAction(String id){
        return this.setDefaultAction(id, null);
    }
    
	public ControllerBuilder setDefaultAction(String id, RequestMethodType requestMethodType) {

		id = StringUtil.adjust(id);

        WebUtil.checkURI(id,true);
        
        requestMethodType = requestMethodType == null? 
        		BrutosWebConstants.DEFAULT_REQUEST_METHOD_TYPE :
        		requestMethodType;
        
		WebActionID actionID = new WebActionID(id, requestMethodType);
		
		if (this.controller.getActionById(actionID) == null)
			throw new MappingException("action not found: \"" + id + "\"");

		if (id != null) {
			getLogger()
					.info(String
							.format("adding default action %s on controller %s",
									new Object[] {
											id,
											controller.getClassType()
													.getSimpleName() }));

			controller.setDefaultAction(actionID);
		}
		
		return this;
	}
    
    public ControllerBuilder setId(String value){
    	
    	if(!this.controller.getActionType().isValidControllerId(value))
    		throw new MappingException("invalid controller id: " + value);
    	
        return super.setId(value);
    }
    
    public ControllerBuilder setView(String value, boolean resolvedView){
    	WebUtil.checkURI(value, resolvedView && value != null);
        return super.setView(value, resolvedView);
    }
    
    public ControllerBuilder setRequestMethod(RequestMethodType value){
    	((WebController)this.controller).setRequestMethod(value);
    	return this;
    }

    public RequestMethodType getRequestMethod(){
    	return ((WebController)this.controller).getRequestMethod();
    }
    
	public ControllerBuilder addRequestType(DataType value){
		MediaType mediaType = MediaType.valueOf(value.getName());
		super.addRequestType(mediaType);
		return this;
	}
	
	public ControllerBuilder removeRequestType(DataType value){
		MediaType mediaType = MediaType.valueOf(value.getName());
		super.removeRequestType(mediaType);
		return this;
	}

	public ControllerBuilder addResponseType(DataType value){
		MediaType mediaType = MediaType.valueOf(value.getName());
		super.addResponseType(mediaType);
		return this;
	}
	
	public ControllerBuilder removeResponseType(DataType value){
		MediaType mediaType = MediaType.valueOf(value.getName());
		super.removeResponseType(mediaType);
		return this;
	}
    
	public ControllerBuilder setResponseStatus(int value){
		((WebController)this.controller).setResponseStatus(value);
		return this;
	}
	
	public ControllerBuilder setResponseError(Class<? extends Throwable> type, int value){
		((WebController)this.controller).getResponseErrors().put(type, value);
		return this;
	}

	public int getResponseStatus(){
		return ((WebController)this.controller).getResponseStatus();
	}
	
	public int getResponseError(Class<? extends Throwable> type){
		return ((WebController)this.controller).getResponseErrors().get(type);
	}
	
}
