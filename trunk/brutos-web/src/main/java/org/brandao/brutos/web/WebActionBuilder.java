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
import org.brandao.brutos.DispatcherType;
import org.brandao.brutos.DataType;
import org.brandao.brutos.ValidatorFactory;
import org.brandao.brutos.mapping.Action;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.mapping.MappingException;
import org.brandao.brutos.mapping.StringUtil;
import org.brandao.brutos.mapping.ThrowableSafeData;
import org.brandao.brutos.web.mapping.WebAction;
import org.brandao.brutos.web.mapping.WebActionID;
import org.brandao.brutos.web.mapping.WebThrowableSafeData;
import org.brandao.brutos.web.util.WebUtil;

/**
 * 
 * @author Brandao
 */
public class WebActionBuilder extends ActionBuilder{
    
    public WebActionBuilder(ActionBuilder builder){
        super(builder);
    }
    
    public WebActionBuilder(
    		Action methodForm, 
            Controller controller, 
            ValidatorFactory validatorFactory,
            ControllerBuilder controllerBuilder,
            ConfigurableApplicationContext applicationContext) {
        super(methodForm, controller, validatorFactory, 
                controllerBuilder, applicationContext);
    }
    
	public ActionBuilder addAlias(String id) {

		id = StringUtil.adjust(id);

        ActionType type = this.controller.getActionType();
        
        if(type.isValidActionId(id)){
        	throw new MappingException("invalid action alias: " + id);
        }
        
		if (StringUtil.isEmpty(id)){
			throw new MappingException("action id cannot be empty");
		}

		WebActionID actionId = new WebActionID(id, ((WebAction)action).getRequestMethod());
		
		if (controller.getAction(actionId) != null)
			throw new MappingException("duplicate action: " + id);

		action.addAlias(id);
		controller.addAction(actionId, this.action);

		return this;
	}

	public ActionBuilder removeAlias(String id) {
		id = StringUtil.adjust(id);

		if (StringUtil.isEmpty(id))
			throw new MappingException("action id cannot be empty");

		WebActionID actionId = new WebActionID(id, ((WebAction)action).getRequestMethod());
		
		if (controller.getAction(actionId) != null)
			throw new MappingException("duplicate action: " + id);
		
		controller.removeAction(actionId);
		
		return this;
	}
	
    public ActionBuilder addThrowable(Class<?> target, String view, 
            String id, DispatcherType dispatcher, boolean resolvedView ){
    	return this.addThrowable(0, null, target, 
    			view, id, dispatcher, resolvedView);
    }
    
    public ActionBuilder addThrowable(int responseError, String reason,
    		Class<?> target, String view, String id, DispatcherType dispatcher, 
    		boolean resolvedView ){
    	
        ActionBuilder builder = 
        		super.addThrowable(target, view, id, dispatcher, resolvedView);
        
        WebThrowableSafeData thr = 
        		(WebThrowableSafeData)this.action.getThrowsSafeOnAction(target);
        
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
    
    public void setRequestMethod(RequestMethodType value){
    	
    	WebAction webAction = (WebAction)this.action;
    	WebActionID oldId = (WebActionID)webAction.getId();
    	WebActionID newId = new WebActionID(webAction.getId().getName(), value);
    	
    	if(this.action.getController().getAction(newId) != null){
    		throw new MappingException("duplicate action: " + newId);
    	}
    	
    	webAction.setRequestMethod(value);
    	this.controller.removeAction(oldId);
    	this.controller.addAction(newId, this.action);
    }

    public RequestMethodType getRequestMethod(){
    	return ((WebAction)this.action).getRequestMethod();
    }
    
    public ActionBuilder setView(String value, boolean viewResolved){
        WebUtil.checkURI(value, viewResolved && value != null);
        return super.setView(value, viewResolved);
    }
    
	public ActionBuilder addRequestType(DataType value){
		MediaType mediaType = MediaType.valueOf(value.getName());
		super.addRequestType(mediaType);
		return this;
	}
	
	public ActionBuilder removeRequestType(DataType value){
		MediaType mediaType = MediaType.valueOf(value.getName());
		super.removeRequestType(mediaType);
		return this;
	}

	public ActionBuilder addResponseType(DataType value){
		MediaType mediaType = MediaType.valueOf(value.getName());
		super.addResponseType(mediaType);
		return this;
	}
	
	public ActionBuilder removeResponseType(DataType value){
		MediaType mediaType = MediaType.valueOf(value.getName());
		super.removeResponseType(mediaType);
		return this;
	}
	
	public ActionBuilder setResponseStatus(int value){
		((WebAction)this.action).setResponseStatus(value);
		return this;
	}
	
	public ActionBuilder setResponseError(Class<? extends Throwable> type, int value){
		((WebAction)this.action).getResponseErrors().put(type, value);
		return this;
	}

	public int getResponseStatus(){
		return ((WebAction)this.action).getResponseStatus();
	}
	
	public int getResponseError(Class<? extends Throwable> type){
		return ((WebAction)this.action).getResponseErrors().get(type);
	}
	
}
