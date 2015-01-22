/*
 * Brutos Web MVC http://www.brutosframework.com.br/
 * Copyright (C) 2009 Afonso Brandao. (afonso.rbn@gmail.com)
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
import org.brandao.brutos.ValidatorFactory;
import org.brandao.brutos.mapping.Action;
import org.brandao.brutos.mapping.Controller;
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
    
    public void addAlias(String value){
        
        ActionType type = this.controller.getActionType();
        
        if(!ActionType.PARAMETER.equals(type))
            WebUtil.checkURI(value, true);
        
        super.addAlias(value);
    }

    public ActionBuilder addThrowable( Class target, String view, 
            String id, DispatcherType dispatcher, boolean resolvedView ){

        ActionType type = this.controller.getActionType();
        
        if(!ActionType.PARAMETER.equals(type)){
            WebUtil.checkURI(id, true);
            
            if(resolvedView)
                WebUtil.checkURI(view, true);
        }
        return super.addThrowable(target, view, resolvedView, id, dispatcher);
    }
    
    public ActionBuilder setView(String value, boolean viewResolved){

        ActionType type = this.controller.getActionType();
        
        if(!ActionType.PARAMETER.equals(type) && this.action.isResolvedView())
            WebUtil.checkURI(value, true);
        
        return super.setView(value, viewResolved);
    }
    
}
