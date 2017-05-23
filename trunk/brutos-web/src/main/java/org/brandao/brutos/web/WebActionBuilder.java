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
import org.brandao.brutos.mapping.ThrowableSafeData;
import org.brandao.brutos.web.mapping.WebAction;
import org.brandao.brutos.web.util.WebUtil;

/**
 * 
 * @author Brandao
 */
public class WebActionBuilder extends ActionBuilder{
    
    public WebActionBuilder(ActionBuilder builder){
        super(builder);
    }
    
    public WebActionBuilder( Action methodForm, 
            Controller controller, ValidatorFactory validatorFactory,
            ControllerBuilder controllerBuilder,
            ConfigurableApplicationContext applicationContext) {
        super(methodForm, controller, validatorFactory, 
                controllerBuilder, applicationContext);
    }
    
    public ActionBuilder addAlias(String value){
        
        ActionType type = this.controller.getActionType();
        
        if(!ActionType.PARAMETER.equals(type))
            WebUtil.checkURI(value, true);
        
        return super.addAlias(value);
    }

    public ActionBuilder addThrowable( Class<?> target, String view, 
            String id, DispatcherType dispatcher, boolean resolvedView ){
    	
        ActionBuilder builder = super.addThrowable(target, view, id, dispatcher, resolvedView);
        ThrowableSafeData thr = this.action.getThrowsSafeOnAction(target);
        WebUtil.checkURI(thr.getView(), resolvedView && view != null);
        return builder;
    }
    
    public void setRequestMethod(RequestMethodType value){
    	((WebAction)this.action).setRequestMethod(value);
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
		((WebAction)this.action).getRequestTypeMap().add(mediaType);
		return this;
	}
	
	public ActionBuilder removeRequestType(DataType value){
		MediaType mediaType = MediaType.valueOf(value.getName());
		((WebAction)this.action).getRequestTypeMap().remove(mediaType);
		return this;
	}

	public ActionBuilder addResponseType(DataType value){
		MediaType mediaType = MediaType.valueOf(value.getName());
		((WebAction)this.action).getResponseTypeMap().add(mediaType);
		return this;
	}
	
	public ActionBuilder removeResponseType(DataType value){
		MediaType mediaType = MediaType.valueOf(value.getName());
		((WebAction)this.action).getResponseTypeMap().remove(mediaType);
		return this;
	}
	
}
