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
import org.brandao.brutos.BrutosConstants;
import org.brandao.brutos.ConfigurableApplicationContext;
import org.brandao.brutos.Configuration;
import org.brandao.brutos.ControllerBuilder;
import org.brandao.brutos.ControllerManager;
import org.brandao.brutos.DataType;
import org.brandao.brutos.DispatcherType;
import org.brandao.brutos.InterceptorManager;
import org.brandao.brutos.ValidatorFactory;
import org.brandao.brutos.mapping.Action;
import org.brandao.brutos.mapping.ActionID;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.mapping.MappingException;
import org.brandao.brutos.mapping.StringUtil;
import org.brandao.brutos.web.mapping.WebAction;
import org.brandao.brutos.web.mapping.WebActionID;
import org.brandao.brutos.web.mapping.WebController;
import org.brandao.brutos.web.mapping.WebControllerID;
import org.brandao.brutos.web.mapping.WebThrowableSafeData;
import org.brandao.brutos.web.util.WebUtil;

/**
 * 
 * @author Brandao
 */
public class WebControllerBuilder extends ControllerBuilder{
    
	/*
	 * O construtor somente pode possuir métodos que alteram 
	 * características mutáveis.
	 */
	private ConfigurableWebApplicationContext webApplicationContext;
	
    public WebControllerBuilder(ControllerBuilder builder, ControllerManager.InternalUpdate internalUpdate){
        super( builder, internalUpdate );
    }
    
    public WebControllerBuilder( Controller controller, ControllerManager controllerManager,
            InterceptorManager interceptorManager, ValidatorFactory validatorFactory,
            ConfigurableApplicationContext applicationContext, ControllerManager.InternalUpdate internalUpdate ){
        super( controller, controllerManager, interceptorManager, 
                validatorFactory, applicationContext, internalUpdate );
        this.webApplicationContext = (ConfigurableWebApplicationContext)applicationContext;
    }
    

	public ControllerBuilder addAlias(String id) {
		return this.addAlias(id, null);
	}
    
	public ControllerBuilder addAlias(String id, RequestMethodType requestMethodType) {

		id = StringUtil.adjust(id);
		requestMethodType = 
				requestMethodType == null? 
						this.webApplicationContext.getRequestMethod() : 
						requestMethodType;

        WebUtil.checkURI(id, true);
						
    	if(!this.controller.getActionType().isValidControllerId(id))
    		throw new MappingException("invalid controller alias: " + id);
    	
		if (StringUtil.isEmpty(id))
			throw new MappingException("invalid alias");

		WebControllerID controllerID = new WebControllerID(id, requestMethodType);
		return this.addAlias(controllerID);
	}

	public ControllerBuilder removeAlias(String id) {
		return this.removeAlias(id, null);
	}
	
	public ControllerBuilder removeAlias(String id, RequestMethodType requestMethodType) {

        WebUtil.checkURI(id, true);
		
		id = StringUtil.adjust(id);
		requestMethodType = 
				requestMethodType == null? 
						this.webApplicationContext.getRequestMethod() : 
						requestMethodType;

		if (StringUtil.isEmpty(id))
			throw new MappingException("invalid alias");

		WebControllerID controllerID = new WebControllerID(id, requestMethodType);
		return this.removeAlias(controllerID);
	}

    public ActionBuilder addAction(String id, 
    		String resultId, boolean resultRendered, String view, 
            DispatcherType dispatcher, boolean resolvedView, String executor ){
    	return this.addAction(id, null, 
    			resultId, resultRendered, view, dispatcher, resolvedView, executor);
    }
    
    public ActionBuilder addAction(String id, RequestMethodType requestMethodType, 
    		String resultId, boolean resultRendered, String view, 
            DispatcherType dispatcher, boolean resolvedView, String executor ){
    	
    	//tratamento de variáveis
        ActionType type      = this.controller.getActionType();
        
        id                   = StringUtil.adjust(id);
		
        resultId             = StringUtil.adjust(resultId);
		
		view                 = StringUtil.adjust(view);
		
		executor             = StringUtil.adjust(executor);

		if(StringUtil.isEmpty(id) && !StringUtil.isEmpty(executor)){
			//if(type.isDelegate() || type.isComposite()){
	    	//	id = type.getActionID(executor.replaceAll("Action$", ""));
			//}
			id = type.getActionID(executor);
		}
		
		requestMethodType    = 
			requestMethodType == null?
				((WebController)this.controller).getRequestMethod() :
				//this.webApplicationContext.getRequestMethod() : 
				requestMethodType;

		dispatcher = dispatcher == null?
				this.controller.getDispatcherType() :
				//this.webApplicationContext.getDispatcherType() :
				dispatcher;
				
		WebActionID actionId = new WebActionID(id, requestMethodType);
				
		//verificação das variáveis
		if (StringUtil.isEmpty(executor) && StringUtil.isEmpty(id)){
			throw new MappingException("executor cannot be empty");
		}
		
		/*
		if (StringUtil.isEmpty(id)){
			throw new MappingException("action id cannot be empty");
		}
		*/
		
    	if(!type.isValidActionId(id))
    		throw new MappingException("invalid action id: " + id);

        if(requestMethodType == null){
        	throw new MappingException("request method type is required");
        }
		
		if (StringUtil.isEmpty(view) && StringUtil.isEmpty(executor))
			throw new MappingException(
					"view must be informed in abstract actions: " + id);

		if (controller.getActionById(actionId) != null)
			throw new MappingException("duplicate action: " + id);

		//criar base da entidade
		WebAction action = new WebAction();
		action.setId(actionId);
		action.setCode(Action.getNextId());
		//action.setResponseStatus(this.webApplicationContext.getResponseStatus());
		action.setResponseStatus(0);
		action.setName(id);
		action.setController(controller);
		action.setResultValidator(validatorFactory.getValidator(new Configuration()));
		action.setParametersValidator(validatorFactory.getValidator(new Configuration()));
		action.setRequestMethod(requestMethodType);
		
		//registrar entidade
		controller.addAction(actionId, action);

		//criar construtor
		WebActionBuilder actionBuilder = 
			new WebActionBuilder(action, controller, validatorFactory, 
					this, this.webApplicationContext);

		//definir características opcionais com o construtor 
		actionBuilder
			.setDispatcherType(dispatcher)
			.setExecutor(executor)
			.setResult(resultId)
			.setResultRendered(resultRendered)
			.setView(view, resolvedView);

		getLogger()
				.info(String
						.format("adding action %s on controller %s",
								new Object[] {
										action.getId(),
										this.controller.getClassType()
												.getSimpleName() }));

		return actionBuilder;    	
    }
    
    public WebThrowSafeBuilder addThrowable( Class<?> target, String view, String id, 
            DispatcherType dispatcher, boolean resolvedView ){
    	return this.addThrowable(0, null, 
    			target, view, id, dispatcher, resolvedView);
    }

    public WebThrowSafeBuilder addThrowable(int responseError, String reason,
    		Class<?> target, String view, String id, 
            DispatcherType dispatcher, boolean resolvedView ){
    	return this.addThrowable(target, null, responseError, 
    			reason, view, dispatcher, resolvedView, null, false);
    }
    
    public WebThrowSafeBuilder addThrowable(Class<?> target, String executor, 
			int responseError, String reason, String view, DispatcherType dispatcher, 
			boolean resolvedView, String resultId, boolean resultRendered){
    	
		executor = StringUtil.adjust(executor);
		view     = StringUtil.adjust(view);
		resultId = StringUtil.isEmpty(resultId)? BrutosConstants.DEFAULT_EXCEPTION_NAME : StringUtil.adjust(resultId);
		reason   = StringUtil.adjust(reason);
		view     = resolvedView ? 
				view : 
				webApplicationContext.getViewResolver().getView(this, null, target, view);
		
		//responseError = responseError <= 0? 
		//	this.webApplicationContext.getResponseError() :
		//	responseError;
		
		//dispatcher = dispatcher == null? 
		//		this.webApplicationContext.getDispatcherType() :
		//		dispatcher;

		if (target == null){
			throw new MappingException("target is required: "
					+ controller.getClassType().getSimpleName());
		}

		if (!Throwable.class.isAssignableFrom(target)){
			throw new MappingException("target is not allowed: "
					+ target.getSimpleName());
		}

		if (this.controller.getThrowsSafe().containsKey(target)){
			throw new MappingException(
					"the exception has been added on controller: "
							+ target.getSimpleName());
		}

        WebUtil.checkURI(view, resolvedView && view != null);

		WebThrowableSafeData thr = new WebThrowableSafeData(null);
		thr.getAction().setId(new ActionID(target.getSimpleName()));
		thr.getAction().setCode(Action.getNextId());
		thr.getAction().setName(target.getSimpleName());
		thr.getAction().setController(controller);
		thr.getAction().setResultValidator(validatorFactory.getValidator(new Configuration()));
		thr.getAction().setParametersValidator(validatorFactory.getValidator(new Configuration()));
		thr.getAction().setView(view);
		thr.getAction().setResolvedView(resolvedView);
		thr.getAction().setDispatcherType(dispatcher);
		thr.getAction().setReturnRendered(resultRendered);
		thr.getAction().getResultAction().setName(resultId);
		thr.getAction().setExecutor(executor);
		((WebAction)thr.getAction()).setResponseStatus(responseError);
		thr.setTarget(target);
		thr.setRedirect(false);
        thr.setReason(reason);
		this.controller.setThrowsSafe(thr);
		
		getLogger().info(
				String.format("added exception %s on controller %s",
						new Object[] { target.getSimpleName(),
								controller.getClassType().getSimpleName() }));
		
		return new WebThrowSafeBuilder(thr, controller, null, validatorFactory, this, null, 
				webApplicationContext);    	
    }
    
    public ControllerBuilder setDefaultAction(String id){
        return this.setDefaultAction(id, null);
    }
    
	public ControllerBuilder setDefaultAction(String id, RequestMethodType requestMethodType) {

		id                   = StringUtil.adjust(id);
		
		requestMethodType    = 
				requestMethodType == null? 
					this.webApplicationContext.getRequestMethod() : 
					requestMethodType;

		WebActionID actionID = new WebActionID(id, requestMethodType);
					
        if(StringUtil.isEmpty(id)){
        	throw new MappingException("invalid id");
        }

        WebUtil.checkURI(id,true);
        
        if(requestMethodType == null){
        	throw new MappingException("invalid request method type");
        }
		
		if (this.controller.getActionById(actionID) == null)
			throw new MappingException("action not found: \"" + id + "\"");

		controller.setDefaultAction(actionID);
		getLogger()
				.info(String
						.format("adding default action %s on controller %s",
								new Object[] {
										id,
										controller.getClassType()
												.getSimpleName() }));

		
		return this;
	}
    
    public ControllerBuilder setView(String value, boolean resolvedView){
        if(resolvedView){
        	WebUtil.checkURI(value, resolvedView && value != null);
        }
        return super.setView(value, resolvedView);
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
		
		if(value <=0){
			throw new MappingException("invalid response status");
		}
		
		((WebController)this.controller).setResponseStatus(value);
		return this;
	}
	
	public int getResponseStatus(){
		return ((WebController)this.controller).getResponseStatus();
	}
	
}
